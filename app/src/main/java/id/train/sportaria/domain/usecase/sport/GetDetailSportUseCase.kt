package id.train.sportaria.domain.usecase.sport

import id.train.sportaria.abstaction.repository.MainSportRepository
import id.train.sportaria.domain.entity.model.Sport
import id.train.sportaria.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDetailSportUseCase
@Inject constructor(
    private val repository: MainSportRepository
) {
    suspend operator fun invoke(id: String): Flow<Result<Sport>> {
        return repository.getSport(id)
    }
}