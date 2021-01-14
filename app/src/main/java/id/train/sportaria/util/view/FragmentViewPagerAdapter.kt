package id.train.sportaria.util.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FragmentViewPagerAdapter(
    private val fragments: List<Fragment>,
    fragmentManager: FragmentManager,
    behavior: Int,
    private val titles: List<String>? = null
) : FragmentPagerAdapter(fragmentManager, behavior) {
    override fun getCount(): Int = fragments.size

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getPageTitle(position: Int): CharSequence? {
        if (titles.isNullOrEmpty()) return super.getPageTitle(position)

        return titles[fragments.indexOf(getItem(position))]
    }
}