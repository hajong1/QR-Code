package hajong.qrcode.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import hajong.qrcode.data.entity.QrHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QrHistoryDao {
    @Query("SELECT * FROM qr_history ORDER BY timestamp DESC")
    fun getAll(): Flow<List<QrHistoryEntity>>

    @Query("SELECT * FROM qr_history ORDER BY timestamp DESC LIMIT :pageSize OFFSET :offset")
    suspend fun getHistoryPage(pageSize: Int, offset: Int): List<QrHistoryEntity>

    @Query("SELECT COUNT(*) FROM qr_history")
    suspend fun getTotalCount(): Int

    @Insert
    suspend fun insert(history: QrHistoryEntity)

    @Delete
    suspend fun delete(history: QrHistoryEntity)

    @Query("DELETE FROM qr_history")
    suspend fun deleteAllHistory()
}
