package id.train.sportaria.data.remote.mapper

import id.train.sportaria.abstaction.BaseMapper
import id.train.sportaria.data.remote.response.SportResponse
import id.train.sportaria.domain.entity.model.Sport
import javax.inject.Inject

class SportNetworkMapper
@Inject constructor(): BaseMapper<SportResponse.DataSport, Sport> {
    override fun mapFromEntity(entity: SportResponse.DataSport): Sport {
        return Sport(
            id = entity.idSport,
            name = entity.strSport,
            poster = entity.strSportThumb,
            description = entity.strSportDescription
        )
    }

    override fun mapToEntity(domainModel: Sport, isBookmarked: Boolean?): SportResponse.DataSport {
        return SportResponse.DataSport(
            idSport = domainModel.id,
            strSport = domainModel.name,
            strSportThumb = domainModel.poster,
            strSportDescription = domainModel.description
        )
    }

    fun mapFromEntityList(entities: List<SportResponse.DataSport>) : List<Sport> {
        return entities.map { mapFromEntity(it) }
    }
}