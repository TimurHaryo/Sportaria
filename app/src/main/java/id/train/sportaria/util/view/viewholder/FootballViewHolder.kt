package id.train.sportaria.util.view.viewholder

import id.train.sportaria.R
import id.train.sportaria.abstaction.BaseViewHolder
import id.train.sportaria.databinding.ItemFootballBinding
import id.train.sportaria.domain.entity.ItemAction
import id.train.sportaria.domain.entity.model.Football
import id.train.sportaria.util.glide

class FootballViewHolder(
    private val binding: ItemFootballBinding
) : BaseViewHolder<Football>(binding) {

    override fun setHolderItemClickListener(listener: ItemAction.ItemClickListener?) {
        this.itemClickListener = listener
    }

    override fun setHolderItemDeleteListener(listener: ItemAction.ItemDeleteListener?) {
        this.itemDeleteListener = listener
    }

    override fun bind(item: Football, viewHolderType: ViewHolderType) {
        with(binding) {
            title = item.strTeam
            year = item.intFormedYear
            stadium = item.strStadium
            isFromBookmark = (viewHolderType == ViewHolderType.BOOKMARK)
            root.glide(item.strTeamBadge, ivPosterListFootball)
            root.setOnClickListener { itemClickListener?.onClick(item) }

            if (viewHolderType == ViewHolderType.BOOKMARK)
                btnDeleteFootball.setOnClickListener { itemDeleteListener?.onDeleteClicked(item) }

            executePendingBindings()
        }
    }

    companion object {
        const val ITEM_FOOTBALL = R.layout.item_football
    }
}