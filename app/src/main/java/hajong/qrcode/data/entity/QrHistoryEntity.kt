package hajong.qrcode.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Entity(tableName = "qr_history")
data class QrHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val content: String,
    val timestamp: String = toLocalDateTime()
)

fun toLocalDateTime(): String {
    val currentMillis = LocalDateTime.now()
        .atZone(ZoneId.systemDefault())
        .toInstant()?.toEpochMilli() ?: 0

    val currentDateTime = Instant.ofEpochMilli(currentMillis).atZone(ZoneId.systemDefault()).toLocalDateTime()

    return DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분").format(currentDateTime)
}