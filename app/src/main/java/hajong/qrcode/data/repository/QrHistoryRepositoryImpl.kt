package hajong.qrcode.data.repository

import android.util.Log
import hajong.qrcode.data.QrHistoryDao
import hajong.qrcode.data.domain.QrHistory
import hajong.qrcode.data.entity.QrHistoryEntity
import hajong.qrcode.data.mapper.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class QrHistoryRepositoryImpl @Inject constructor(
    private val qrHistoryDao: QrHistoryDao,
) : QrHistoryRepository {
//    val history = qrHistoryDao.getAll()

    // flow
    override fun observeHistory(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<QrHistory>> =
        qrHistoryDao.observeHistory().map { it.toDomain() }
            .onStart { onStart() }
            .onCompletion { onComplete() }
            .flowOn(Dispatchers.IO)

    override suspend fun fetchHistory(
        page: Int,
        pageSize: Int
    ): List<QrHistory> {
        val offset = (page - 1) * pageSize

        // 디버깅을 위한 로그 추가
        Log.d("Repository", "Fetching history - page: $page, pageSize: $pageSize, offset: $offset")

        val entities = qrHistoryDao.getHistoryPage(pageSize, offset)
        Log.d("Repository", "Fetched entities: ${entities.size}")

        val domainList = entities.toDomain()
        Log.d("Repository", "Converted to domain: ${domainList.size}")

        return domainList
    }

    override suspend fun insertHistory(content: String): Long {
        return qrHistoryDao.insert(
            QrHistoryEntity(
                content = content
            )
        )
    }

    override suspend fun deleteHistory(id: Long) {
        qrHistoryDao.delete(id)
    }

    override suspend fun deleteAllHistory() {
        qrHistoryDao.deleteAllHistory()
    }
}