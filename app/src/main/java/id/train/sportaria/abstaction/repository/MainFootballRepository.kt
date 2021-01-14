package id.train.sportaria.abstaction.repository

import id.train.sportaria.domain.entity.model.Football
import id.train.sportaria.util.Result
import kotlinx.coroutines.flow.Flow

interface MainFootballRepository {
    suspend fun getFootballs(): Flow<Result<List<Football>>>
    suspend fun getFootball(id: String): Flow<Result<Football>>
    suspend fun updateBookmarkFootball(id: String, isBookmarked: Boolean): Flow<Result<Int>>
    suspend fun getBookmarkedFootballs(): Flow<Result<List<Football>>>
}