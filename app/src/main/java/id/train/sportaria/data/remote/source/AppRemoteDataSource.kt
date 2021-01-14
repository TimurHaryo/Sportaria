package id.train.sportaria.data.remote.source

import id.train.sportaria.data.remote.response.FootballResponse
import id.train.sportaria.data.remote.response.SportResponse
import id.train.sportaria.data.remote.service.Api
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import id.train.sportaria.util.Result

class AppRemoteDataSource
@Inject constructor(private val apiService: Api) : RemoteDataSource() {

    suspend fun getAllSports(dispatcher: CoroutineDispatcher) : Result<SportResponse> {
        return safeCallApi(dispatcher) {apiService.getSports()}
    }

    suspend fun getAllFootballs(dispatcher: CoroutineDispatcher) : Result<FootballResponse> {
        return safeCallApi(dispatcher) {apiService.getFootballs()}
    }
}