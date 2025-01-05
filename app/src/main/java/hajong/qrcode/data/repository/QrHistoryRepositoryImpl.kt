package hajong.qrcode.data.repository

import hajong.qrcode.data.QrHistoryDao
import hajong.qrcode.data.domain.QrHistory
import hajong.qrcode.data.entity.QrHistoryEntity
import hajong.qrcode.data.mapper.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class QrHistoryRepositoryImpl @Inject constructor(
    private val qrHistoryDao: QrHistoryDao,
) : QrHistoryRepository {
//    val history = qrHistoryDao.getAll()

    override fun fetchHistory(
        page: Int,
        pageSize: Int,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
        ) = flow {
        val offset = (page - 1) * pageSize
        val history = qrHistoryDao.getHistoryPage(pageSize, offset).toDomain()
        emit(history)
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)

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

    override fun deleteAllHistory() {

    }
}