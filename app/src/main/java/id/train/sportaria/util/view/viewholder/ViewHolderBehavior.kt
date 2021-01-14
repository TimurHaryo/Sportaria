package id.train.sportaria.util.view.viewholder

import id.train.sportaria.domain.entity.ItemAction

interface ViewHolderBehavior {

    fun setHolderItemClickListener(listener: ItemAction.ItemClickListener?)
    fun setHolderItemDeleteListener(listener: ItemAction.ItemDeleteListener?)

}