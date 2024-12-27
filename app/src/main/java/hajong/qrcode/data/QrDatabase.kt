package hajong.qrcode.data

import androidx.room.Database
import androidx.room.RoomDatabase
import hajong.qrcode.data.entity.QrHistoryEntity

@Database(
    entities = [QrHistoryEntity::class],
    version = 1
)
abstract class QrDatabase : RoomDatabase() {
    abstract fun historyDao(): QrHistoryDao
}