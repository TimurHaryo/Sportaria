package id.train.sportaria.data.remote.factory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.train.sportaria.abstaction.BaseViewHolder
import id.train.sportaria.databinding.ItemFootballBinding
import id.train.sportaria.databinding.ItemSportBinding
import id.train.sportaria.domain.entity.model.Football
import id.train.sportaria.domain.entity.model.Sport
import id.train.sportaria.domain.factory.ItemTypeFactory
import id.train.sportaria.util.view.viewholder.FootballViewHolder
import id.train.sportaria.util.view.viewholder.SportViewHolder

class ItemTypeFactoryImpl: ItemTypeFactory {
    override fun type(sport: Sport): Int {
        return SportViewHolder.ITEM_SPORT
    }

    override fun type(football: Football): Int {
        return FootballViewHolder.ITEM_FOOTBALL
    }

    override fun createViewHolder(
        parent: View,
        viewGroup: ViewGroup,
        type: Int
    ): BaseViewHolder<*> {
        return when(type) {
            SportViewHolder.ITEM_SPORT -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemSportBinding.inflate(layoutInflater, viewGroup, false)
                SportViewHolder(binding)
            }
            FootballViewHolder.ITEM_FOOTBALL -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemFootballBinding.inflate(layoutInflater, viewGroup, false)
                FootballViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Unknown Type")
        }
    }
}