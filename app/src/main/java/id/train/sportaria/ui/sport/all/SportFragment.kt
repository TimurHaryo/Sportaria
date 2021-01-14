package id.train.sportaria.ui.sport.all

import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.train.sportaria.R
import id.train.sportaria.abstaction.BaseFragment
import id.train.sportaria.abstaction.BaseItemModel
import id.train.sportaria.abstaction.BaseRecyclerViewAdapter
import id.train.sportaria.data.remote.factory.ItemTypeFactoryImpl
import id.train.sportaria.databinding.FragmentSportBinding
import id.train.sportaria.domain.entity.ItemAction
import id.train.sportaria.domain.entity.model.Sport
import id.train.sportaria.ui.sport.SportViewModel
import id.train.sportaria.ui.sport.detail.DetailSportActivity
import id.train.sportaria.util.Result
import id.train.sportaria.util.network.NetworkConnection
import id.train.sportaria.util.network.NetworkSignalHandler
import id.train.sportaria.util.toast
import id.train.sportaria.util.view.viewholder.ViewHolderType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SportFragment :
    BaseFragment<FragmentSportBinding>(), ItemAction.ItemClickListener, NetworkSignalHandler {

    @Inject
    lateinit var sportNetworkConnection: NetworkConnection

    private val sportViewModel: SportViewModel by viewModels()

    private var shouldReconnect: Boolean = false

    private val adapterSport by lazy {
        BaseRecyclerViewAdapter(
            ItemTypeFactoryImpl(),
            arrayListOf(),
            ViewHolderType.NORMAL
        ).apply {
            setHolderItemClickListener(this@SportFragment)
        }
    }

    override fun resourceLayoutId(): Int = R.layout.fragment_sport

    override fun initViewCreated() {
        binding.hasItem = true
        observeData()
        collectSportData()
        setOnClickView()

        binding.rvSports.adapter = adapterSport

        initSwipeRefresh()
    }

    private fun setOnClickView() {
        binding.noSportData.btnReconnect.setOnClickListener {
            refresh()
        }
    }

    private fun collectSportData() {
        sportViewModel.collectMainSports()
    }

    private fun observeData() {
        sportNetworkConnection.observe(this, { isConnected ->
            if(isConnected) onConnected() else onDisconnected()
        })

        sportViewModel.mainSports.observe(this, {
            when(it) {
                is Result.Loading -> {
                    binding.status = true
                }
                is Result.Success -> {
                    binding.status = false
                    if (it.data.isNullOrEmpty()) {
                        binding.hasItem = false
                        binding.noSportData.shouldNeedNetwork = true
                    } else {
                        binding.hasItem = true
                        adapterSport.refreshItem(it.data)
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
    }

    private fun initSwipeRefresh() {
        binding.swipeRefreshSport.setOnRefreshListener {
            refresh()
        }
    }

    private fun refresh() {
        if (sportNetworkConnection.value!!) {
            collectSportData()
        } else {
            onDisconnected()
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

    override fun onConnected() {
        if (shouldReconnect) {
            collectSportData()
            shouldReconnect = false
        }
    }

    override fun onDisconnected() {
        requireContext().toast(getString(R.string.no_internet), Toast.LENGTH_LONG)
        binding.status = false
        shouldReconnect = true
    }

    companion object {
        fun newInstance() = SportFragment()
    }

}
