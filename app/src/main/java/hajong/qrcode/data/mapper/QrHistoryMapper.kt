package hajong.qrcode.data.mapper

import hajong.qrcode.data.domain.QrHistory
import hajong.qrcode.data.entity.QrHistoryEntity

object QrHistoryMapper: EntityMapper<List<QrHistory>, List<QrHistoryEntity>> {

    override fun toDomain(entity: List<QrHistoryEntity>): List<QrHistory> {
        return entity.map {
            QrHistory(
                id = it.id,
                content = it.content,
                timestamp = it.timestamp
            )
        }
    }

    override fun toEntity(domain: List<QrHistory>): List<QrHistoryEntity> {
        return domain.map{
            QrHistoryEntity(
                id = it.id,
                content = it.content,
                timestamp = it.timestamp
            )
        }
    }
}

fun List<QrHistoryEntity>?.toDomain(): List<QrHistory> {
    return QrHistoryMapper.toDomain(this.orEmpty())
}

fun List<QrHistory>.toEntity(): List<QrHistoryEntity> {
    return QrHistoryMapper.toEntity(this)
}