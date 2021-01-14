package id.train.sportaria.ui.sport.all

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
import id.train.sportaria.databinding.FragmentBookmarkSportBinding
import id.train.sportaria.domain.entity.ItemAction
import id.train.sportaria.domain.entity.model.Sport
import id.train.sportaria.ui.sport.SportViewModel
import id.train.sportaria.ui.sport.detail.DetailSportActivity
import id.train.sportaria.util.Result
import id.train.sportaria.util.view.dialogFragment.DeletionDialogFragment
import id.train.sportaria.util.view.viewholder.ViewHolderType
import kotlinx.coroutines.ExperimentalCoroutinesApi

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class BookmarkSportFragment : BaseFragment<FragmentBookmarkSportBinding>(), ItemAction.ItemClickListener, ItemAction.ItemDeleteListener {

    private val sportViewModel: SportViewModel by viewModels()

    private val adapterBookmarkSport by lazy {
        BaseRecyclerViewAdapter(
            ItemTypeFactoryImpl(),
            arrayListOf(),
            ViewHolderType.BOOKMARK
        ).apply {
            setHolderItemClickListener(this@BookmarkSportFragment)
            setHolderItemDeleteListener(this@BookmarkSportFragment)
        }
    }

    override fun resourceLayoutId(): Int = R.layout.fragment_bookmark_sport

    override fun initViewCreated() {
        binding.hasItem = true
        observeBookmarkData()
        collectBookmarkSportData()

        binding.rvBookmarkSports.adapter = adapterBookmarkSport

        initSwipeRefresh()
    }

    private fun observeBookmarkData() {
        sportViewModel.bookmarkedSports.observe(this, {
            when(it) {
                is Result.Loading -> {
                    binding.status = true
                }
                is Result.Success -> {
                    binding.status = false
                    if (it.data.isNullOrEmpty()) {
                        binding.hasItem = false
                        binding.noBookmarkSportData.shouldNeedNetwork = false
                    } else {
                        binding.hasItem = true
                        adapterBookmarkSport.refreshItem(it.data)

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

        sportViewModel.updateBookmarkSport.observe(this, {
            when(it) {
                is Result.Loading -> {
                    binding.status = true
                }
                is Result.Success -> {
                    binding.status = false
                    collectBookmarkSportData()
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

    override fun onResume() {
        collectBookmarkSportData()
        super.onResume()
    }

    private fun collectBookmarkSportData() {
        sportViewModel.collectBookmarkedSports()
    }

    private fun initSwipeRefresh() {
        binding.swipeRefreshBookmarkSport.setOnRefreshListener {
            collectBookmarkSportData()
        }
    }

    override fun onClick(item: BaseItemModel) {
        when(item) {
            is Sport -> {
                val intent = Intent(requireContext(), DetailSportActivity::class.java).apply {
                    putExtra(DetailSportActivity.SPORT_KEY, item)
                    putExtra(DetailSportActivity.SPORT_ID_KEY, item.id)
                }

                startActivity(intent)
            }
            else -> {  }
        }
    }

    override fun onDeleteClicked(item: BaseItemModel) {
        when(item) {
            is Sport -> {
                DeletionDialogFragment.newInstance {
                    sportViewModel.updateBookmarkSport(item.id, false)
                }.show(requireActivity().supportFragmentManager, tag)
            }
            else -> {  }
        }
    }

    companion object {
        fun newInstance() = BookmarkSportFragment()
    }
}