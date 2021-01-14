package id.train.sportaria.data.remote.mapper

import id.train.sportaria.abstaction.BaseMapper
import id.train.sportaria.data.remote.response.FootballResponse
import id.train.sportaria.domain.entity.model.Football
import javax.inject.Inject

class FootballNetworkMapper
@Inject constructor(): BaseMapper<FootballResponse.Team, Football> {
    override fun mapFromEntity(entity: FootballResponse.Team): Football {
        return Football(
            id = entity.idTeam,
            strTeam = entity.strTeam,
            intFormedYear = entity.intFormedYear,
            strStadium = entity.strStadium,
            strStadiumThumb = entity.strStadiumThumb,
            strDescription = entity.strDescription,
            strStadiumDescription = entity.strStadiumDescription,
            strTeamBadge = entity.strTeamBadge,
            strTeamBanner = entity.strTeamBanner
        )
    }

    override fun mapToEntity(domainModel: Football, isBookmarked: Boolean?): FootballResponse.Team {
        return FootballResponse.Team(
            idTeam = domainModel.id,
            strTeam = domainModel.strTeam,
            intFormedYear = domainModel.intFormedYear,
            strStadium = domainModel.strStadium,
            strStadiumThumb = domainModel.strStadiumThumb,
            strDescription = domainModel.strDescription,
            strStadiumDescription = domainModel.strStadiumDescription,
            strTeamBadge = domainModel.strTeamBadge,
            strTeamBanner = domainModel.strTeamBanner
        )
    }

    fun mapFromEntityList(entities: List<FootballResponse.Team>) : List<Football> {
        return entities.map { mapFromEntity(it) }
    }
}