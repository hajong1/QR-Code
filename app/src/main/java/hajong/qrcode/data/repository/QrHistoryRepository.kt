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
    suspend fun insertHistory(content: String): Long
    suspend fun deleteHistory(id: Long)
    fun deleteAllHistory()
}