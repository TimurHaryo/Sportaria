package id.train.sportaria.ui.football.all

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
import id.train.sportaria.databinding.FragmentFootballBinding
import id.train.sportaria.domain.entity.ItemAction
import id.train.sportaria.domain.entity.model.Football
import id.train.sportaria.ui.football.FootballViewModel
import id.train.sportaria.ui.football.detail.DetailFootballActivity
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
class FootballFragment :
    BaseFragment<FragmentFootballBinding>(), ItemAction.ItemClickListener, NetworkSignalHandler {

    @Inject
    lateinit var footballNetworkConnection: NetworkConnection

    private val footballViewModel: FootballViewModel by viewModels()

    private var shouldReconnect: Boolean = false

    private val adapterFootball by lazy {
        BaseRecyclerViewAdapter(
            ItemTypeFactoryImpl(),
            arrayListOf(),
            ViewHolderType.NORMAL
        ).apply {
            setHolderItemClickListener(this@FootballFragment)
        }
    }

    override fun resourceLayoutId(): Int = R.layout.fragment_football

    override fun initViewCreated() {
        binding.hasItem = true
        observeData()
        collectFootballData()
        setOnClickView()

        binding.rvFootballs.adapter = adapterFootball

        initSwipeRefresh()
    }

    private fun setOnClickView() {
        binding.noFootballData.btnReconnect.setOnClickListener {
            refresh()
        }
    }

    private fun initSwipeRefresh() {
        binding.swipeRefreshFootball.setOnRefreshListener {
            refresh()
        }
    }

    private fun refresh() {
        if (footballNetworkConnection.value!!) {
            collectFootballData()
        } else {
            onDisconnected()
        }
    }

    private fun collectFootballData() {
        footballViewModel.collectMainFootballs()
    }

    private fun observeData() {
        footballNetworkConnection.observe(this, { isConnected ->
            if (isConnected) onConnected() else onDisconnected()
        })

        footballViewModel.mainFootballs.observe(this, {
            when(it) {
                is Result.Loading -> {
                    binding.status = true
                }
                is Result.Success -> {
                    binding.status = false
                    if (it.data.isNullOrEmpty()) {
                        binding.hasItem = false
                        binding.noFootballData.shouldNeedNetwork = true
                    } else {
                        binding.hasItem = true
                        adapterFootball.refreshItem(it.data)
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

    override fun onConnected() {
        if (shouldReconnect) {
            collectFootballData()
            shouldReconnect = false
        }
    }

    override fun onDisconnected() {
        requireContext().toast(getString(R.string.no_internet), Toast.LENGTH_LONG)
        binding.status = false
        shouldReconnect = true
    }

    companion object {
        fun newInstance() = FootballFragment()
    }

}