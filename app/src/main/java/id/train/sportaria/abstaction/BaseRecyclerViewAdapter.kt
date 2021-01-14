package id.train.sportaria.abstaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.train.sportaria.domain.entity.ItemAction
import id.train.sportaria.domain.factory.ItemTypeFactory
import id.train.sportaria.util.view.viewholder.ViewHolderBehavior
import id.train.sportaria.util.view.viewholder.ViewHolderType

open class BaseRecyclerViewAdapter(
    private val itemTypeFactory: ItemTypeFactory,
    private val items: ArrayList<BaseItemModel> = arrayListOf(),
    private val viewHolderType: ViewHolderType
) : RecyclerView.Adapter<BaseViewHolder<BaseItemModel>>(), ViewHolderBehavior {

    private var onHolderItemClickListener: ItemAction.ItemClickListener? = null
    private var onHolderItemDeleteListener: ItemAction.ItemDeleteListener? = null

    override fun setHolderItemClickListener(listener: ItemAction.ItemClickListener?) {
        this.onHolderItemClickListener = listener
    }

    override fun setHolderItemDeleteListener(listener: ItemAction.ItemDeleteListener?) {
        this.onHolderItemDeleteListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<BaseItemModel> {
        val view = LayoutInflater.from(parent.context).inflate(
            viewType,
            parent,
            false
        )

        return itemTypeFactory.createViewHolder(view, parent, viewType) as BaseViewHolder<BaseItemModel>
    }

    override fun onBindViewHolder(holder: BaseViewHolder<BaseItemModel>, position: Int) {
        holder.setHolderItemClickListener(onHolderItemClickListener)
        holder.setHolderItemDeleteListener(onHolderItemDeleteListener)
        holder.bind(items[position], viewHolderType)
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return items[position].type(itemTypeFactory)
    }

    fun refreshItem(items: List<BaseItemModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}