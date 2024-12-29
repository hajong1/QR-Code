package hajong.qrcode.data.repository

import hajong.qrcode.data.QrHistoryDao
import hajong.qrcode.data.entity.QrHistoryEntity
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class QrHistoryRepositoryImpl @Inject constructor(
    private val qrHistoryDao: QrHistoryDao
) : QrHistoryRepository {
//    val history = qrHistoryDao.getAll()

    override fun fetchHistory(page: Int) = flow {
        val pageSize = 10
        val offset = (page - 1) * pageSize
        val history = qrHistoryDao.getHistoryPage(pageSize, offset)
        emit(history)

    }

    override fun insertHistory(content: String) {

    }

    override fun deleteHistory(history: QrHistoryEntity) {

    }

    override fun deleteAllHistory() {

    }
}