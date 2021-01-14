package id.train.sportaria.ui.football.detail

import android.os.Build
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.AndroidEntryPoint
import id.train.sportaria.R
import id.train.sportaria.abstaction.BaseActivity
import id.train.sportaria.databinding.ActivityDetailFootballBinding
import id.train.sportaria.domain.entity.cardModel.CardPagerModel
import id.train.sportaria.domain.entity.model.Football
import id.train.sportaria.ui.football.FootballViewModel
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
class DetailFootballActivity : BaseActivity<ActivityDetailFootballBinding>() {
    private val tag = this.javaClass.simpleName

    @Inject
    lateinit var footballNotificationUtil: NotificationUtil

    private lateinit var footballID: String

    private lateinit var footballData: Football

    private val footballViewModel: FootballViewModel by viewModels()

    private val pagerContent = mutableListOf<CardPagerModel>()

    private var isBookmarked: MutableLiveData<Boolean> = MutableLiveData()

    override fun resourceLayoutId(): Int = R.layout.activity_detail_football

    override fun initView() {
        footballID = intent.getStringExtra(FOOTBALL_ID_KEY) as String
        footballData = intent.getParcelableExtra<Football>(FOOTBALL_KEY) as Football

        observeFootballData()
        collectFootballData()
        setOnClickView()
        setupPagerData()

        binding.root.glide(footballData.strTeamBanner, binding.ivFootballPosterDetail)
        binding.title = footballData.strTeam
        binding.year = "- ${footballData.intFormedYear} -"
        binding.pagerFootballCardDetail.apply {
            adapter = CardViewPagerAdapter(pagerContent, this@DetailFootballActivity)
            pageMargin = 64
            offscreenPageLimit = pagerContent.size
        }
    }

    private fun observeFootballData() {
        isBookmarked.observe(this, {
            when(it) {
                true -> {
                    binding.root.glide(
                        R.drawable.ic_bookmarked_gold,
                        binding.btnFootballUpdateBookmarkStatus
                    )
                }
                false -> {
                    binding.root.glide(
                        R.drawable.ic_notbookmarked_white,
                        binding.btnFootballUpdateBookmarkStatus
                    )
                }
            }
        })

        footballViewModel.footballById.observe(this, {
            when(it) {
                is Result.Loading -> {
                    Log.v(tag, "LOADING GET ....")
                }
                is Result.Success -> {
                    Log.v("Football Data", "${it.data.isBookmarked}")

                    isBookmarked.value = it.data.isBookmarked
                }
                is Result.Error -> {
                    Log.e(tag, "${it.exception.message}")
                }
                else -> {
                    Log.e(tag, "Unknown from footballById $tag")
                }
            }
        })

        footballViewModel.updateBookmarkFootball.observe(this, {
            when(it) {
                is Result.Loading -> {
                    binding.isLoadingBookmark = true
                }
                is Result.Success -> {
                    Log.v("Football Update", "before: ${isBookmarked.value}")
                    binding.isLoadingBookmark = false
                    isBookmarked.value = !isBookmarked.value!!
                    Log.v("Football Update", "after: ${isBookmarked.value}")

                    if (isBookmarked.value!!) {
                        footballNotificationUtil(
                            this,
                            getString(R.string.notif_success_title),
                            getString(R.string.notif_success_desc),
                            true
                        )
                    }
                }
                is Result.Error -> {
                    binding.isLoadingBookmark = false
                    footballNotificationUtil(
                        this,
                        getString(R.string.notif_failed_title),
                        getString(R.string.notif_failed_desc),
                        false
                    )
                }
                else -> {
                    binding.isLoadingBookmark = false
                    footballNotificationUtil(
                        this,
                        getString(R.string.notif_failed_title),
                        getString(R.string.notif_failed_desc),
                        false
                    )
                }
            }
        })
    }

    private fun collectFootballData() {
        footballViewModel.collectFootballById(footballID)
    }

    private fun setOnClickView() {
        binding.btnFootballBackToMain.setOnClickListener {
            intent.apply {
                removeExtra(FOOTBALL_KEY)
                removeExtra(FOOTBALL_ID_KEY)
            }
            onBackPressed()
        }
        binding.btnFootballUpdateBookmarkStatus.setOnClickListener {
            if (isBookmarked.value!!) {
                DeletionDialogFragment.newInstance {
                    footballViewModel.updateBookmarkFootball(footballID, !isBookmarked.value!!)
                }.show(supportFragmentManager, tag)
            } else {
                footballViewModel.updateBookmarkFootball(footballID, !isBookmarked.value!!)
            }
        }
    }

    private fun setupPagerData() {
        pagerContent.addAll(listOf(
            CardPagerModel(
                footballData.strTeamBadge,
                "Logo",
                CardPagerRules.IMAGE
            ),
            CardPagerModel(
                footballData.strDescription,
                "Description",
                CardPagerRules.TEXT
            ),
            CardPagerModel(
                footballData.strStadiumThumb,
                footballData.strStadium,
                CardPagerRules.IMAGE
            ),
            CardPagerModel(
                footballData.strStadiumDescription,
                "Stadium Description",
                CardPagerRules.TEXT
            )
        ))
    }

    companion object {
        const val FOOTBALL_ID_KEY = "FOOTBALL_ID_KEY"
        const val FOOTBALL_KEY = "FOOTBALL_KEY"
    }

}