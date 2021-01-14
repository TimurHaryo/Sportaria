package id.train.sportaria.ui.football.all

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.train.sportaria.R
import id.train.sportaria.abstaction.BaseFragment
import id.train.sportaria.abstaction.BaseItemModel
import id.train.sportaria.abstaction.BaseRecyclerViewAdapter
import id.train.sportaria.data.remote.factory.ItemTypeFactoryImpl
import id.train.sportaria.databinding.FragmentBookmarkFootballBinding
import id.train.sportaria.domain.entity.ItemAction
import id.train.sportaria.domain.entity.model.Football
import id.train.sportaria.ui.football.FootballViewModel
import id.train.sportaria.ui.football.detail.DetailFootballActivity
import id.train.sportaria.util.Result
import id.train.sportaria.util.view.dialogFragment.DeletionDialogFragment
import id.train.sportaria.util.view.viewholder.ViewHolderType
import kotlinx.coroutines.ExperimentalCoroutinesApi

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class BookmarkFootballFragment : BaseFragment<FragmentBookmarkFootballBinding>(), ItemAction.ItemClickListener, ItemAction.ItemDeleteListener {

    private val footballViewModel: FootballViewModel by viewModels()

    private val adapterBookmarkFootball by lazy {
        BaseRecyclerViewAdapter(
            ItemTypeFactoryImpl(),
            arrayListOf(),
            ViewHolderType.BOOKMARK
        ).apply {
            setHolderItemClickListener(this@BookmarkFootballFragment)
            setHolderItemDeleteListener(this@BookmarkFootballFragment)
        }
    }

    override fun resourceLayoutId(): Int = R.layout.fragment_bookmark_football

    override fun initViewCreated() {
        binding.hasItem = true
        observeBookmarkData()
        collectBookmarkFootballData()

        binding.rvBookmarkFootballs.adapter = adapterBookmarkFootball

        initSwipeRefresh()
    }

    private fun observeBookmarkData() {
        footballViewModel.bookmarkedFootballs.observe(this, {
            when(it) {
                is Result.Loading -> {
                    binding.status = true
                }
                is Result.Success -> {
                    binding.status = false
                    if (it.data.isNullOrEmpty()) {
                        binding.hasItem = false
                        binding.noBookmarkFootballData.shouldNeedNetwork = false
                    } else {
                        binding.hasItem = true
                        adapterBookmarkFootball.refreshItem(it.data)
                    }
                }
                is Result.Error -> {
                    binding.status = false
                }
                else -> {
                    binding.status = false
                }
            }
        })

        footballViewModel.updateBookmarkFootball.observe(this, {
            when(it) {
                is Result.Loading -> {
                    binding.status = true
                }
                is Result.Success -> {
                    binding.status = false
                    collectBookmarkFootballData()
                }
                is Result.Error -> {
                    binding.status = false
                }
                else -> {
                    binding.status = false
                }
            }
        })
    }

    private fun collectBookmarkFootballData() {
        footballViewModel.collectBookmarkedFootballs()
    }

    private fun initSwipeRefresh() {
        binding.swipeRefreshBookmarkFootball.setOnRefreshListener {
            collectBookmarkFootballData()
        }
    }

    override fun onResume() {
        collectBookmarkFootballData()
        super.onResume()
    }

    override fun onClick(item: BaseItemModel) {
        when(item) {
            is Football -> {
                val intent = Intent(requireContext(), DetailFootballActivity::class.java).apply {
                    putExtra(DetailFootballActivity.FOOTBALL_KEY, item)
                    putExtra(DetailFootballActivity.FOOTBALL_ID_KEY, item.id)
                }

                startActivity(intent)
            }
            else -> {  }
        }
    }

    override fun onDeleteClicked(item: BaseItemModel) {
        when(item) {
            is Football -> {
                DeletionDialogFragment.newInstance {
                    footballViewModel.updateBookmarkFootball(item.id, false)
                }.show(requireActivity().supportFragmentManager, tag)
            }
            else -> {  }
        }
    }

    companion object {
        fun newInstance() = BookmarkFootballFragment()
    }
}