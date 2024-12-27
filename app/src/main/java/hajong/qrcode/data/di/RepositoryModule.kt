package hajong.qrcode.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hajong.qrcode.data.QrHistoryDao
import hajong.qrcode.data.repository.QrHistoryRepository
import hajong.qrcode.data.repository.QrHistoryRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {
    @Binds
    fun bindsQrHistoryRepository(qrHistoryRepositoryImpl: QrHistoryRepositoryImpl): QrHistoryRepository

    // 무슨 차이일까
//    @Provides
//    @Singleton
//    fun provideQrHistoryRepository(
//        dao: QrHistoryDao
//    ): QrHistoryRepository {
//
//    }
}