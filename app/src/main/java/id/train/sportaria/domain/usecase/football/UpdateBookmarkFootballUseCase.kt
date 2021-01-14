package id.train.sportaria.domain.usecase.football

import id.train.sportaria.abstaction.repository.MainFootballRepository
import id.train.sportaria.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateBookmarkFootballUseCase
@Inject constructor(
    private val repository: MainFootballRepository
) {
    suspend operator fun invoke(id: String, isBookmarked: Boolean) : Flow<Result<Int>> {
        return repository.updateBookmarkFootball(id, isBookmarked)
    }
}