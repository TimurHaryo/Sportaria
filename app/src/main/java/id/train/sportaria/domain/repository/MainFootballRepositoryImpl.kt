package id.train.sportaria.domain.repository

import android.util.Log
import id.train.sportaria.abstaction.repository.MainFootballRepository
import id.train.sportaria.data.local.entity.FootballEntity
import id.train.sportaria.data.local.mapper.FootballCacheMapper
import id.train.sportaria.data.local.source.AppLocalDataSource
import id.train.sportaria.data.remote.mapper.FootballNetworkMapper
import id.train.sportaria.data.remote.response.FootballResponse
import id.train.sportaria.data.remote.source.AppRemoteDataSource
import id.train.sportaria.domain.entity.model.Football
import id.train.sportaria.util.RequestResult
import id.train.sportaria.util.Result
import id.train.sportaria.util.dispatcher.CoroutineDispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainFootballRepositoryImpl
@Inject constructor(
    private val remoteDataSource: AppRemoteDataSource,
    private val localDataSource: AppLocalDataSource,
    private val dispatcher: CoroutineDispatcherProvider,
    private val footballNetworkMapper: FootballNetworkMapper,
    private val footballCacheMapper: FootballCacheMapper
) : MainFootballRepository {

    override suspend fun getFootballs(): Flow<Result<List<Football>>> = flow {
        emit(Result.Loading)
        try {
            var isBookmarked = false
            val networkFootball = getNetworkFootballs()
            val bookmarkIds = getBookmarkedIds()
            val footballs = footballNetworkMapper.mapFromEntityList(networkFootball)

            for (football in footballs) {
                for (id in bookmarkIds) {
                    if (football.id == id) isBookmarked = true
                }

                localDataSource.insertFootball(
                    dispatcher.io,
                    footballCacheMapper.mapToEntity(football, isBookmarked)
                )

                isBookmarked = false
            }

            val cachedFootballs = getCachedFootballs()
            emit(Result.Success(footballCacheMapper.mapFromEntityList(cachedFootballs)))
        } catch (throwable: Throwable) {
            emit(Result.Error(
                RequestResult.NOT_DEFINED,
                throwable
            ))
        }
    }

    override suspend fun getFootball(id: String): Flow<Result<Football>> = flow {
        emit(Result.Loading)
        try {
            val footballEntity = getSportById(id)!!
            val football = footballCacheMapper.mapFromEntity(footballEntity)

            emit(Result.Success(football))
        } catch (throwable: Throwable) {
            emit(Result.Error(
                RequestResult.NOT_DEFINED,
                throwable
            ))
        }
    }

    override suspend fun updateBookmarkFootball(
        id: String,
        isBookmarked: Boolean
    ): Flow<Result<Int>> = flow {
        emit(Result.Loading)
        val footballBookmarkUpdater = localDataSource.updateBookmarkFootball(dispatcher.io, id, isBookmarked)
        when(footballBookmarkUpdater) {
            is Result.Success -> {
                emit(Result.Success(footballBookmarkUpdater.data))
            }
            is Result.Error -> {
                emit(Result.Error(
                    RequestResult.NOT_DEFINED,
                    footballBookmarkUpdater.exception
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

    override suspend fun getBookmarkedFootballs(): Flow<Result<List<Football>>> = flow {
        emit(Result.Loading)
        val bookmarkedFootballs = localDataSource.getBookmarkedFootballs(dispatcher.io)
        when(bookmarkedFootballs) {
            is Result.Success -> {
                emit(Result.Success(footballCacheMapper.mapFromEntityList(bookmarkedFootballs.data)))
            }
            is Result.Error -> {
                emit(Result.Error(
                    RequestResult.NOT_DEFINED,
                    bookmarkedFootballs.exception
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

    private suspend fun getNetworkFootballs(): List<FootballResponse.Team> {
        val networkFootball = remoteDataSource.getAllFootballs(dispatcher.io)
        return when(networkFootball) {
            is Result.Success -> networkFootball.data.teams ?: emptyList()
            is Result.Error -> {
                Log.e(this.javaClass.simpleName, networkFootball.exception.message.toString())
                emptyList()
            }
            else -> {
                Log.e(this.javaClass.simpleName, "Unknown From Network")
                emptyList()
            }
        }
    }

    private suspend fun getBookmarkedIds(): List<String> {
        val bookmarkedId = localDataSource.getBookmarkedFootballsID(dispatcher.io)
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

    private suspend fun getCachedFootballs(): List<FootballEntity> {
        val cachedFootballs = localDataSource.getAllFootballs(dispatcher.io)
        return when(cachedFootballs) {
            is Result.Success -> cachedFootballs.data
            is Result.Error -> {
                Log.e(this.javaClass.simpleName, cachedFootballs.exception.message.toString())
                emptyList()
            }
            else -> {
                Log.e(this.javaClass.simpleName, "Unknown From Local")
                emptyList()
            }
        }
    }

    private suspend fun getSportById(id: String): FootballEntity? {
        val football = localDataSource.getFootball(dispatcher.io, id)
        return when(football) {
            is Result.Success -> football.data
            is Result.Error -> {
                Log.e(this.javaClass.simpleName, football.exception.message.toString())
                null
            }
            else -> {
                Log.e(this.javaClass.simpleName, "Unknown From Local")
                null
            }
        }
    }

}