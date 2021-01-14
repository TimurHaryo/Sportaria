package id.train.sportaria.abstaction.repository

import id.train.sportaria.domain.entity.model.Sport
import id.train.sportaria.util.Result
import kotlinx.coroutines.flow.Flow

interface MainSportRepository {
    suspend fun getSports(): Flow<Result<List<Sport>>>
    suspend fun getSport(id: String): Flow<Result<Sport>>
    suspend fun updateBookmarkSport(id: String, isBookmarked: Boolean): Flow<Result<Int>>
    suspend fun getBookmarkedSports(): Flow<Result<List<Sport>>>
}