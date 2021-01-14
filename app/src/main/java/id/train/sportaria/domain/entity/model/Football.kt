package id.train.sportaria.domain.entity.model

import id.train.sportaria.abstaction.BaseItemModel
import id.train.sportaria.domain.factory.ItemTypeFactory
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Football(
    val id: String,
    val strTeam: String,
    val intFormedYear: String,
    val strStadium: String,
    val strStadiumThumb: String,
    val strStadiumDescription: String,
    val strDescription: String,
    val strTeamBadge: String,
    val strTeamBanner: String,
    var isBookmarked: Boolean = false
) : BaseItemModel() {
    override fun type(itemTypeFactory: ItemTypeFactory): Int {
        return itemTypeFactory.type(this)
    }
}