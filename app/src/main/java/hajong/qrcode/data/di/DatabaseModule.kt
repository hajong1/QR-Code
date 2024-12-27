package hajong.qrcode.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hajong.qrcode.data.QrDatabase
import hajong.qrcode.data.QrHistoryDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideQrDatabase(
        @ApplicationContext context: Context
    ): QrDatabase {
        return Room.databaseBuilder(
            context,
            QrDatabase::class.java,
            "qr_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideQrHistoryDao(
        database: QrDatabase
    ): QrHistoryDao {
        return database.historyDao()
    }
}