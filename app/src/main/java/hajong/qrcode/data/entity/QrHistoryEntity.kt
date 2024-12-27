package hajong.qrcode.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "qr_history")
data class QrHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)