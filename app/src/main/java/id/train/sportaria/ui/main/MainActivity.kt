package id.train.sportaria.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.fxn.OnBubbleClickListener
import dagger.hilt.android.AndroidEntryPoint
import id.train.sportaria.R
import id.train.sportaria.abstaction.BaseActivity
import id.train.sportaria.databinding.ActivityMainBinding
import id.train.sportaria.ui.bookmark.BookmarkFragment
import id.train.sportaria.ui.football.all.FootballFragment
import id.train.sportaria.ui.sport.all.SportFragment
import id.train.sportaria.util.view.FragmentViewPagerAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(), OnBubbleClickListener {

    override fun resourceLayoutId(): Int = R.layout.activity_main

    private val sportFragment = SportFragment.newInstance()
    private val footballFragment = FootballFragment.newInstance()
    private val bookmarkFragment = BookmarkFragment.newInstance()

    private val fragments = listOf<Fragment>(
        sportFragment,
        footballFragment,
        bookmarkFragment
    )

    private val indexToPage = mapOf(
        0 to R.id.menu_sport,
        1 to R.id.menu_team,
        2 to R.id.menu_bookmark
    )

    override fun initView() {
        binding.fragmentContainer.apply {
            adapter = FragmentViewPagerAdapter(
                fragments,
                supportFragmentManager,
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
            )
            offscreenPageLimit = fragments.size
        }
        binding.bottomNavigationView.addBubbleListener(this)
    }

    override fun onBubbleClick(id: Int) {
        with(binding) {
            val position = indexToPage.values.indexOf(id)
            if (fragmentContainer.currentItem != position) setItem(position)
        }
    }

    private fun setItem(position: Int) {
        binding.fragmentContainer.currentItem = position
    }
}