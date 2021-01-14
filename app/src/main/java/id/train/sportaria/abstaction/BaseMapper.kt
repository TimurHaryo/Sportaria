package id.train.sportaria.abstaction

interface BaseMapper<Entity, DomainModel> {
    fun mapFromEntity(entity: Entity): DomainModel

    fun mapToEntity(domainModel: DomainModel, isBookmarked: Boolean? = null): Entity
}