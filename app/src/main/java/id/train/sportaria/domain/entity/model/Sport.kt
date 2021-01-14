package id.train.sportaria.domain.entity.model

import id.train.sportaria.abstaction.BaseItemModel
import id.train.sportaria.domain.factory.ItemTypeFactory
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Sport(
    val id: String,
    val name: String,
    val poster: String,
    val description: String,
    var isBookmarked: Boolean = false
) : BaseItemModel() {
    override fun type(itemTypeFactory: ItemTypeFactory): Int {
        return itemTypeFactory.type(this)
    }
}