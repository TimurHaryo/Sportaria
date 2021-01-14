package id.train.sportaria.ui.football

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.train.sportaria.domain.entity.model.Football
import id.train.sportaria.domain.usecase.football.GetBookmarkFootballUseCase
import id.train.sportaria.domain.usecase.football.GetDetailFootballUseCase
import id.train.sportaria.domain.usecase.football.GetMainFootballsUseCase
import id.train.sportaria.domain.usecase.football.UpdateBookmarkFootballUseCase
import id.train.sportaria.util.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class FootballViewModel
@ViewModelInject constructor(
    private val getMainFootballsUseCase: GetMainFootballsUseCase,
    private val getDetailFootballUseCase: GetDetailFootballUseCase,
    private val updateBookmarkFootballUseCase: UpdateBookmarkFootballUseCase,
    private val getBookmarkFootballUseCase: GetBookmarkFootballUseCase
) : ViewModel() {

    private val _mainFootballs = MutableLiveData<Result<List<Football>>>()
    val mainFootballs: LiveData<Result<List<Football>>>
        get() = _mainFootballs

    private val _footballById = MutableLiveData<Result<Football>>()
    val footballById: LiveData<Result<Football>>
        get() = _footballById

    private val _updateBookmarkFootball = MutableLiveData<Result<Int>>()
    val updateBookmarkFootball: LiveData<Result<Int>>
        get() = _updateBookmarkFootball

    private val _bookmarkedFootballs = MutableLiveData<Result<List<Football>>>()
    val bookmarkedFootballs: LiveData<Result<List<Football>>>
        get() = _bookmarkedFootballs

/*================================================================================================*/

    fun collectMainFootballs() = viewModelScope.launch {
        getMainFootballsUseCase()
            .onEach { result ->
                _mainFootballs.value = result
            }.launchIn(viewModelScope)
    }

    fun collectFootballById(id: String) = viewModelScope.launch {
        getDetailFootballUseCase(id)
            .onEach { result ->
                _footballById.value = result
            }.launchIn(viewModelScope)
    }

    fun updateBookmarkFootball(id: String, isBookmarked: Boolean) = viewModelScope.launch {
        updateBookmarkFootballUseCase(id, isBookmarked)
            .onEach { result ->
                _updateBookmarkFootball.value = result
            }.launchIn(viewModelScope)
    }

    fun collectBookmarkedFootballs() = viewModelScope.launch {
        getBookmarkFootballUseCase()
            .onEach { result ->
                _bookmarkedFootballs.value = result
            }.launchIn(viewModelScope)
    }
}