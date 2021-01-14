package id.train.sportaria.domain.entity

import id.train.sportaria.abstaction.BaseItemModel

object ItemAction {

    interface ItemClickListener {
        fun onClick(item: BaseItemModel)
    }

    interface ItemDeleteListener {
        fun onDeleteClicked(item: BaseItemModel)
    }

}