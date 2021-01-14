package id.train.sportaria.abstaction

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import id.train.sportaria.domain.entity.ItemAction
import id.train.sportaria.util.view.viewholder.ViewHolderBehavior
import id.train.sportaria.util.view.viewholder.ViewHolderType

abstract class BaseViewHolder<T>(binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root), ViewHolderBehavior {

    protected var itemDeleteListener: ItemAction.ItemDeleteListener? = null
    protected var itemClickListener: ItemAction.ItemClickListener? = null

    abstract fun bind(item: T, viewHolderType: ViewHolderType)

}