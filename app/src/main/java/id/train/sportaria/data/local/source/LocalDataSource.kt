package id.train.sportaria.data.local.source

import id.train.sportaria.util.RequestResult
import id.train.sportaria.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

open class LocalDataSource {
    open suspend fun <T> safeDaoCall(
        dispatcher: CoroutineDispatcher,
        daoCall: suspend () -> T
    ) : Result<T> {
        return withContext(dispatcher) {
            try {
                Result.Success(daoCall.invoke())
            } catch (throwable: Throwable) {
                Result.Error(
                    RequestResult.NOT_DEFINED,
                    throwable
                )
            }
        }
    }
}