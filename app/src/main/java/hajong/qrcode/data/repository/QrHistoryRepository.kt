package hajong.qrcode.data.repository

import hajong.qrcode.data.entity.QrHistoryEntity
import kotlinx.coroutines.flow.Flow

interface QrHistoryRepository {
    fun fetchHistory(page: Int): Flow<List<QrHistoryEntity>>
    fun insertHistory(content: String)
    fun deleteHistory(history: QrHistoryEntity)
    fun deleteAllHistory()

}