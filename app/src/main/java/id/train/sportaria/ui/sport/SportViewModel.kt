package id.train.sportaria.ui.sport

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.train.sportaria.domain.entity.model.Sport
import id.train.sportaria.domain.usecase.sport.GetBookmarkSportsUseCase
import id.train.sportaria.domain.usecase.sport.GetDetailSportUseCase
import id.train.sportaria.domain.usecase.sport.GetMainSportsUseCase
import id.train.sportaria.domain.usecase.sport.UpdateBookmarkSportUseCase
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import id.train.sportaria.util.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn

@ExperimentalCoroutinesApi
class SportViewModel
@ViewModelInject constructor(
    private val getMainSportsUseCase: GetMainSportsUseCase,
    private val getDetailSportUseCase: GetDetailSportUseCase,
    private val updateBookmarkSportUseCase: UpdateBookmarkSportUseCase,
    private val getBookmarkSportsUseCase: GetBookmarkSportsUseCase
) : ViewModel() {

    private val _mainSports = MutableLiveData<Result<List<Sport>>>()
    val mainSports: LiveData<Result<List<Sport>>>
        get() = _mainSports

    private val _sportById = MutableLiveData<Result<Sport>>()
    val sportById: LiveData<Result<Sport>>
        get() = _sportById

    private val _updateBookmarkSport = MutableLiveData<Result<Int>>()
    val updateBookmarkSport: LiveData<Result<Int>>
        get() = _updateBookmarkSport

    private val _bookmarkedSports = MutableLiveData<Result<List<Sport>>>()
    val bookmarkedSports: LiveData<Result<List<Sport>>>
        get() = _bookmarkedSports

/*================================================================================================*/

    fun collectMainSports() = viewModelScope.launch {
        getMainSportsUseCase()
            .onEach { result ->
                _mainSports.value = result
            }.launchIn(viewModelScope)
    }

    fun collectSportById(id: String) = viewModelScope.launch {
        getDetailSportUseCase(id)
            .onEach { result ->
                _sportById.value = result
            }.launchIn(viewModelScope)
    }

    fun updateBookmarkSport(id: String, isBookmarked: Boolean) = viewModelScope.launch {
        updateBookmarkSportUseCase(id, isBookmarked)
            .onEach { result ->
                _updateBookmarkSport.value = result
            }.launchIn(viewModelScope)
    }

    fun collectBookmarkedSports() = viewModelScope.launch {
        getBookmarkSportsUseCase()
            .onEach { result ->
                _bookmarkedSports.value = result
            }.launchIn(viewModelScope)
    }
}