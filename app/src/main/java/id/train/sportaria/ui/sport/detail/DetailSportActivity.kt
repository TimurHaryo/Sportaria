package id.train.sportaria.ui.sport.detail

import android.os.Build
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.AndroidEntryPoint
import id.train.sportaria.R
import id.train.sportaria.abstaction.BaseActivity
import id.train.sportaria.databinding.ActivityDetailSportBinding
import id.train.sportaria.domain.entity.cardModel.CardPagerModel
import id.train.sportaria.domain.entity.model.Sport
import id.train.sportaria.ui.sport.SportViewModel
import id.train.sportaria.util.NotificationUtil
import id.train.sportaria.util.Result
import id.train.sportaria.util.glide
import id.train.sportaria.util.view.CardPagerRules
import id.train.sportaria.util.view.CardViewPagerAdapter
import id.train.sportaria.util.view.dialogFragment.DeletionDialogFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailSportActivity : BaseActivity<ActivityDetailSportBinding>() {
    private val tag = this.javaClass.simpleName

    @Inject
    lateinit var sportNotificationUtil: NotificationUtil

    private lateinit var sportID: String

    private lateinit var sportData: Sport

    private val sportViewModel: SportViewModel by viewModels()

    private val pagerContent = mutableListOf<CardPagerModel>()

    private var isBookmarked: MutableLiveData<Boolean> = MutableLiveData()

    override fun resourceLayoutId(): Int =  R.layout.activity_detail_sport

    override fun initView() {
        sportID = intent.getStringExtra(SPORT_ID_KEY) as String
        sportData = intent.getParcelableExtra<Sport>(SPORT_KEY) as Sport

        observeSportData()
        collectSportData()
        setOnClickView()
        setupPagerData()

        binding.root.glide(sportData.poster, binding.ivSportPosterDetail)
        binding.title = sportData.name
        binding.pagerSportCardDetail.apply {
            adapter = CardViewPagerAdapter(pagerContent, this@DetailSportActivity)
            pageMargin = 64
            offscreenPageLimit = pagerContent.size
        }
    }

    private fun collectSportData() {
        sportViewModel.collectSportById(sportID)
    }

    private fun setOnClickView() {
        binding.btnSportBackToMain.setOnClickListener {
            intent.apply {
                removeExtra(SPORT_KEY)
                removeExtra(SPORT_ID_KEY)
            }
            onBackPressed()
        }
        binding.btnSportUpdateBookmarkStatus.setOnClickListener {
            if (isBookmarked.value!!) {
                DeletionDialogFragment.newInstance {
                    sportViewModel.updateBookmarkSport(sportID, !isBookmarked.value!!)
                }.show(supportFragmentManager, tag)
            } else {
                sportViewModel.updateBookmarkSport(sportID, !isBookmarked.value!!)
            }
        }
    }

    private fun setupPagerData() {
        pagerContent.addAll(listOf(
            CardPagerModel(
                sportData.description,
                "Description",
                CardPagerRules.TEXT
            )
        ))
    }

    private fun observeSportData() {
        isBookmarked.observe(this, {
            when(it) {
                true -> {
                    binding.root.glide(R.drawable.ic_bookmarked_gold, binding.btnSportUpdateBookmarkStatus)
                }
                false -> {
                    binding.root.glide(R.drawable.ic_notbookmarked_white, binding.btnSportUpdateBookmarkStatus)
                }
            }
        })

        sportViewModel.sportById.observe(this, {
            when(it) {
                is Result.Loading -> {
                    Log.v(tag, "LOADING GET ....")
                }
                is Result.Success -> {
                    Log.v("Sport Data", "${it.data.isBookmarked}")

                    isBookmarked.value = it.data.isBookmarked
                }
                is Result.Error -> {
                    Log.e(tag, "${it.exception.message}")
                }
                else -> {
                    Log.e(tag, "Unknown from sportById $tag")
                }
            }
        })

        sportViewModel.updateBookmarkSport.observe(this, {
            when(it) {
                is Result.Loading -> {
                    binding.isLoadingBookmark = true
                }
                is Result.Success -> {
                    Log.v("Sport Update", "before: ${isBookmarked.value}")
                    binding.isLoadingBookmark = false
                    isBookmarked.value = !isBookmarked.value!!
                    Log.v("Sport Update", "after: ${isBookmarked.value}")

                    if (isBookmarked.value!!) {
                        sportNotificationUtil(
                            this,
                            getString(R.string.notif_success_title),
                            getString(R.string.notif_success_desc),
                            true
                        )
                    }
                }
                is Result.Error -> {
                    binding.isLoadingBookmark = false
                    sportNotificationUtil(
                        this,
                        getString(R.string.notif_failed_title),
                        getString(R.string.notif_failed_desc),
                        false
                    )
                }
                else -> {
                    binding.isLoadingBookmark = false
                    sportNotificationUtil(
                        this,
                        getString(R.string.notif_failed_title),
                        getString(R.string.notif_failed_desc),
                        false
                    )
                }
            }
        })
    }

    companion object {
        const val SPORT_ID_KEY = "SPORT_ID_KEY"
        const val SPORT_KEY = "SPORT_KEY"
    }
}