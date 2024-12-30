package hajong.qrcode.data.mapper

interface EntityMapper<Domain, Entity> {
    fun toDomain(entity: Entity): Domain
    fun toEntity(domain: Domain): Entity
}