package hajong.qrcode.data.repository

import hajong.qrcode.data.domain.QrHistory
import kotlinx.coroutines.flow.Flow

interface QrHistoryRepository {
    fun fetchHistory(
        page: Int,
        pageSize: Int,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<QrHistory>>
    fun insertHistory(content: String)
    fun deleteHistory(history: QrHistory)
    fun deleteAllHistory()
}