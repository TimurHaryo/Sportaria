package id.train.sportaria.ui.bookmark

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import id.train.sportaria.R
import id.train.sportaria.abstaction.BaseFragment
import id.train.sportaria.databinding.FragmentBookmarkBinding
import id.train.sportaria.ui.football.all.BookmarkFootballFragment
import id.train.sportaria.ui.sport.all.BookmarkSportFragment
import id.train.sportaria.util.view.FragmentViewPagerAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class BookmarkFragment : BaseFragment<FragmentBookmarkBinding>() {

    private val fragments = listOf<Fragment>(
        BookmarkSportFragment.newInstance(),
        BookmarkFootballFragment.newInstance()
    )

    private var titles = listOf<String>()

    override fun resourceLayoutId(): Int = R.layout.fragment_bookmark

    override fun initViewCreated() {
        titles = listOf(
            requireContext().resources.getString(R.string.sports),
            requireContext().resources.getString(R.string.teams)
        )

        binding.pagerBookmark.apply {
            adapter = FragmentViewPagerAdapter(
                fragments,
                childFragmentManager,
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                titles
            )
            offscreenPageLimit = fragments.size
        }

        binding.tabLayoutBookmark.apply {
            setupWithViewPager(binding.pagerBookmark, true)
            tabGravity = TabLayout.GRAVITY_FILL
        }
    }

    companion object {
        fun newInstance() = BookmarkFragment()
    }
}