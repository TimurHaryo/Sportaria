package id.train.sportaria.domain.factory

import android.view.View
import android.view.ViewGroup
import id.train.sportaria.abstaction.BaseViewHolder
import id.train.sportaria.domain.entity.model.Football
import id.train.sportaria.domain.entity.model.Sport

interface ItemTypeFactory {
    fun type(sport: Sport): Int
    fun type(football: Football): Int
    fun createViewHolder(parent: View, viewGroup: ViewGroup, type: Int) : BaseViewHolder<*>
}