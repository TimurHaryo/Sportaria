package id.train.sportaria.data.local.mapper

import id.train.sportaria.abstaction.BaseMapper
import id.train.sportaria.data.local.entity.SportEntity
import id.train.sportaria.domain.entity.model.Sport
import javax.inject.Inject

class SportCacheMapper
@Inject constructor(): BaseMapper<SportEntity, Sport> {

    override fun mapFromEntity(entity: SportEntity): Sport {
        return Sport(
            id = entity.id,
            name = entity.name,
            poster = entity.poster,
            description = entity.description,
            isBookmarked = entity.bookmarked
        )
    }

    override fun mapToEntity(domainModel: Sport, isBookmarked: Boolean?): SportEntity {
        return SportEntity(
            id = domainModel.id,
            name = domainModel.name,
            poster = domainModel.poster,
            description = domainModel.description,
            bookmarked = isBookmarked ?: false
        )
    }

    fun mapFromEntityList(entities: List<SportEntity>): List<Sport> {
        return entities.map { mapFromEntity(it) }
    }
}