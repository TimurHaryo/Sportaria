package id.train.sportaria.domain.usecase.football

import id.train.sportaria.abstaction.repository.MainFootballRepository
import id.train.sportaria.domain.entity.model.Football
import id.train.sportaria.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookmarkFootballUseCase
@Inject constructor(
    private val repository: MainFootballRepository
) {
    suspend operator fun invoke(): Flow<Result<List<Football>>> {
        return repository.getBookmarkedFootballs()
    }
}