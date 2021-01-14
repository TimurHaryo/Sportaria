package id.train.sportaria.domain.usecase.sport

import id.train.sportaria.abstaction.repository.MainSportRepository
import id.train.sportaria.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateBookmarkSportUseCase
@Inject constructor(
    private val repository: MainSportRepository
) {
    suspend operator fun invoke(id: String, isBookmarked: Boolean) : Flow<Result<Int>> {
        return repository.updateBookmarkSport(id, isBookmarked)
    }
}