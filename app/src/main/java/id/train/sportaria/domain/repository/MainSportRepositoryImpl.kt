package id.train.sportaria.domain.repository

import android.util.Log
import id.train.sportaria.abstaction.repository.MainSportRepository
import id.train.sportaria.data.local.entity.SportEntity
import id.train.sportaria.data.local.mapper.SportCacheMapper
import id.train.sportaria.data.local.source.AppLocalDataSource
import id.train.sportaria.data.remote.mapper.SportNetworkMapper
import id.train.sportaria.data.remote.response.SportResponse
import id.train.sportaria.data.remote.source.AppRemoteDataSource
import id.train.sportaria.domain.entity.model.Sport
import id.train.sportaria.util.RequestResult
import id.train.sportaria.util.Result
import id.train.sportaria.util.dispatcher.CoroutineDispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainSportRepositoryImpl
@Inject constructor(
    private val remoteDataSource: AppRemoteDataSource,
    private val localDataSource: AppLocalDataSource,
    private val dispatcher: CoroutineDispatcherProvider,
    private val sportNetworkMapper: SportNetworkMapper,
    private val sportCacheMapper: SportCacheMapper
) : MainSportRepository  {

    override suspend fun getSports(): Flow<Result<List<Sport>>> = flow {
        emit(Result.Loading)
        try {
            var isBookmarked = false
            val networkSports = getNetworkSports()
            val bookmarkedIds = getBookmarkedIds()
            val sports = sportNetworkMapper.mapFromEntityList(networkSports)

            for (sport in sports) {
                for (id in bookmarkedIds) {
                    if (sport.id == id) isBookmarked = true
                }

                localDataSource.insertSport(
                    dispatcher.io,
                    sportCacheMapper.mapToEntity(sport, isBookmarked)
                )

                isBookmarked = false
            }

            val cachedSports = getCachedSports()
            emit(Result.Success(sportCacheMapper.mapFromEntityList(cachedSports)))
        } catch (throwable: Throwable) {
            emit(Result.Error(
                RequestResult.NOT_DEFINED,
                throwable
            ))
        }
    }

    override suspend fun getSport(id: String): Flow<Result<Sport>> = flow {
        emit(Result.Loading)
        try {
            val sportEntity = getSportById(id)!!
            val sport = sportCacheMapper.mapFromEntity(sportEntity)

            emit(Result.Success(sport))
        } catch (throwable: Throwable) {
            emit(Result.Error(
                RequestResult.NOT_DEFINED,
                throwable
            ))
        }
    }

    override suspend fun updateBookmarkSport(
            id: String,
            isBookmarked: Boolean
    ): Flow<Result<Int>> = flow {
        emit(Result.Loading)
        val sportBookmarkUpdater = localDataSource.updateBookmarkSport(dispatcher.io, id, isBookmarked)
        when(sportBookmarkUpdater) {
            is Result.Success -> {
                emit(Result.Success(sportBookmarkUpdater.data))
            }
            is Result.Error -> {
                emit(Result.Error(
                    RequestResult.NOT_DEFINED,
                    sportBookmarkUpdater.exception
                ))
            }
            else -> {
                emit(Result.Error(
                    RequestResult.NOT_DEFINED,
                    Throwable("Unknown Error From Updater")
                ))
            }
        }
    }

    override suspend fun getBookmarkedSports(): Flow<Result<List<Sport>>> = flow {
        emit(Result.Loading)
        val bookmarkedSports = localDataSource.getBookmarkedSports(dispatcher.io)
        when(bookmarkedSports) {
            is Result.Success -> {
                emit(Result.Success(sportCacheMapper.mapFromEntityList(bookmarkedSports.data)))
            }
            is Result.Error -> {
                emit(Result.Error(
                    RequestResult.NOT_DEFINED,
                    bookmarkedSports.exception
                ))
            }
            else -> {
                emit(Result.Error(
                    RequestResult.NOT_DEFINED,
                    Throwable("Unknown Error From Get Bookmarks Sport")
                ))
            }
        }
    }

    private suspend fun getNetworkSports(): List<SportResponse.DataSport> {
        val networkSport = remoteDataSource.getAllSports(dispatcher.io)
        return when(networkSport) {
            is Result.Success -> networkSport.data.dataSports ?: emptyList()
            is Result.Error -> {
                Log.e(this.javaClass.simpleName, networkSport.exception.message.toString())
                emptyList()
            }
            else -> {
                Log.e(this.javaClass.simpleName, "Unknown From Network")
                emptyList()
            }
        }
    }

    private suspend fun getBookmarkedIds(): List<String> {
        val bookmarkedId = localDataSource.getBookmarkedSportsID(dispatcher.io)
        return when(bookmarkedId) {
            is Result.Success -> bookmarkedId.data
            is Result.Error -> {
                Log.e(this.javaClass.simpleName, bookmarkedId.exception.message.toString())
                emptyList()
            }
            else -> {
                Log.e(this.javaClass.simpleName, "Unknown From Local ID")
                emptyList()
            }
        }
    }

    private suspend fun getCachedSports(): List<SportEntity> {
        val cachedSports = localDataSource.getAllSports(dispatcher.io)
        return when(cachedSports) {
            is Result.Success -> cachedSports.data
            is Result.Error -> {
                Log.e(this.javaClass.simpleName, cachedSports.exception.message.toString())
                emptyList()
            }
            else -> {
                Log.e(this.javaClass.simpleName, "Unknown From Local")
                emptyList()
            }
        }
    }

    private suspend fun getSportById(id: String): SportEntity? {
        val sport = localDataSource.getSport(dispatcher.io, id)
        return when(sport) {
            is Result.Success -> sport.data
            is Result.Error -> {
                Log.e(this.javaClass.simpleName, sport.exception.message.toString())
                null
            }
            else -> {
                Log.e(this.javaClass.simpleName, "Unknown From Local")
                null
            }
        }
    }

}