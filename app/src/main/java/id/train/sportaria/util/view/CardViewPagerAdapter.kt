package id.train.sportaria.util.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import id.train.sportaria.R
import id.train.sportaria.databinding.ItemDetailBinding
import id.train.sportaria.domain.entity.cardModel.CardPagerModel
import id.train.sportaria.util.glide

class CardViewPagerAdapter(
    private val contents: List<CardPagerModel>,
    private val context: Context
) : PagerAdapter() {

    private lateinit var binding: ItemDetailBinding

    override fun getCount(): Int = contents.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean = (view == `object`)

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.item_detail, container, false)
        val content = contents[position]

        Log.v("Card Adapter", "$content || ${content.title} || ${content.rule}")

        binding.title = content.title
        if (content.rule == CardPagerRules.IMAGE) {
            binding.isImage = true
            binding.root.glide(content.data, binding.ivDetailContent)
        } else {
            binding.isImage = false
            binding.contentText = content.data
        }

        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}