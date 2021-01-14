package id.train.sportaria.data.local.mapper

import id.train.sportaria.abstaction.BaseMapper
import id.train.sportaria.data.local.entity.FootballEntity
import id.train.sportaria.domain.entity.model.Football
import javax.inject.Inject

class FootballCacheMapper
@Inject constructor(): BaseMapper<FootballEntity, Football> {

    override fun mapFromEntity(entity: FootballEntity): Football {
        return Football(
            id = entity.id,
            strTeam = entity.strTeam,
            intFormedYear = entity.intFormedYear,
            strStadium = entity.strStadium,
            strStadiumThumb = entity.strStadiumThumb,
            strDescription = entity.strDescription,
            strStadiumDescription = entity.strStadiumDescription,
            strTeamBadge = entity.strTeamBadge,
            strTeamBanner = entity.strTeamBanner,
            isBookmarked = entity.isBookmarked
        )
    }

    override fun mapToEntity(domainModel: Football, isBookmarked: Boolean?): FootballEntity {
        return FootballEntity(
            id= domainModel.id,
            strTeam = domainModel.strTeam,
            intFormedYear = domainModel.intFormedYear,
            strStadium = domainModel.strStadium,
            strStadiumThumb = domainModel.strStadiumThumb,
            strDescription = domainModel.strDescription,
            strStadiumDescription = domainModel.strStadiumDescription,
            strTeamBadge = domainModel.strTeamBadge,
            strTeamBanner = domainModel.strTeamBanner,
            isBookmarked = isBookmarked ?: false
        )
    }

    fun mapFromEntityList(entities: List<FootballEntity>): List<Football> {
        return entities.map { mapFromEntity(it) }
    }
}