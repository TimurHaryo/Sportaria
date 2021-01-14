package id.train.sportaria.util.view.viewholder

import id.train.sportaria.R
import id.train.sportaria.abstaction.BaseViewHolder
import id.train.sportaria.databinding.ItemSportBinding
import id.train.sportaria.domain.entity.ItemAction
import id.train.sportaria.domain.entity.model.Sport
import id.train.sportaria.util.glide

class SportViewHolder(
    private val binding: ItemSportBinding
) : BaseViewHolder<Sport>(binding) {

    override fun setHolderItemClickListener(listener: ItemAction.ItemClickListener?) {
        this.itemClickListener = listener
    }

    override fun setHolderItemDeleteListener(listener: ItemAction.ItemDeleteListener?) {
        this.itemDeleteListener = listener
    }

    override fun bind(item: Sport, viewHolderType: ViewHolderType) {
        with(binding) {
            title = item.name
            isFromBookmark = (viewHolderType == ViewHolderType.BOOKMARK)
            root.glide(item.poster, ivPosterListSport)
            root.setOnClickListener { itemClickListener?.onClick(item) }

            if (viewHolderType == ViewHolderType.BOOKMARK)
                btnDeleteSport.setOnClickListener { itemDeleteListener?.onDeleteClicked(item) }

            executePendingBindings()
        }
    }

    companion object {
        const val ITEM_SPORT = R.layout.item_sport
    }
}