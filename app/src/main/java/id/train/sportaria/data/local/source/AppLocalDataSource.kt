package id.train.sportaria.data.local.source

import id.train.sportaria.data.local.dao.FootballDao
import id.train.sportaria.data.local.dao.SportDao
import id.train.sportaria.data.local.entity.FootballEntity
import id.train.sportaria.data.local.entity.SportEntity
import id.train.sportaria.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class AppLocalDataSource
@Inject constructor(
    private val sportDao: SportDao,
    private val footballDao: FootballDao
) : LocalDataSource() {

    // SPORT
    suspend fun getAllSports(dispatcher: CoroutineDispatcher): Result<List<SportEntity>> {
        return safeDaoCall(dispatcher) {sportDao.getAllSports()}
    }

    suspend fun insertSport(
        dispatcher: CoroutineDispatcher,
        item: SportEntity
    ): Result<Long> {
        return safeDaoCall(dispatcher) {sportDao.insertSport(item)}
    }

    suspend fun getBookmarkedSports(dispatcher: CoroutineDispatcher): Result<List<SportEntity>> {
        return safeDaoCall(dispatcher) {sportDao.getBookmarkedSports()}
    }

    suspend fun getBookmarkedSportsID(dispatcher: CoroutineDispatcher): Result<List<String>> {
        return safeDaoCall(dispatcher) {sportDao.getBookmarkedSportsID()}
    }

    suspend fun getSport(
        dispatcher: CoroutineDispatcher,
        id: String
    ): Result<SportEntity> {
        return safeDaoCall(dispatcher) {sportDao.getSport(id)}
    }

    suspend fun updateBookmarkSport(
        dispatcher: CoroutineDispatcher,
        id: String,
        isBookmarked: Boolean
    ): Result<Int> {
        return safeDaoCall(dispatcher) {sportDao.updateBookmarkSport(id, isBookmarked)}
    }

    suspend fun deleteAllSports(dispatcher: CoroutineDispatcher): Result<Unit> {
        return safeDaoCall(dispatcher) {sportDao.deleteAllSports()}
    }

    // FOOTBALL
    suspend fun getAllFootballs(dispatcher: CoroutineDispatcher): Result<List<FootballEntity>> {
        return safeDaoCall(dispatcher) {footballDao.getAllFootballs()}
    }

    suspend fun insertFootball(
        dispatcher: CoroutineDispatcher,
        item: FootballEntity
    ): Result<Long> {
        return safeDaoCall(dispatcher) {footballDao.insertFootball(item)}
    }

    suspend fun getBookmarkedFootballs(dispatcher: CoroutineDispatcher): Result<List<FootballEntity>> {
        return safeDaoCall(dispatcher) {footballDao.getBookmarkedFootballs()}
    }

    suspend fun getBookmarkedFootballsID(dispatcher: CoroutineDispatcher): Result<List<String>> {
        return safeDaoCall(dispatcher) {footballDao.getBookmarkedFootballsID()}
    }

    suspend fun getFootball(
        dispatcher: CoroutineDispatcher,
        id: String
    ): Result<FootballEntity> {
        return safeDaoCall(dispatcher) {footballDao.getFootball(id)}
    }

    suspend fun updateBookmarkFootball(
        dispatcher: CoroutineDispatcher,
        id: String,
        isBookmarked: Boolean
    ): Result<Int> {
        return safeDaoCall(dispatcher) {footballDao.updateBookmarkFootball(id, isBookmarked)}
    }

    suspend fun deleteAllFootballs(dispatcher: CoroutineDispatcher): Result<Unit> {
        return safeDaoCall(dispatcher) {footballDao.deleteAllFootballs()}
    }

}