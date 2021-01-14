package id.train.sportaria.data.remote.source

import id.train.sportaria.util.RequestResult
import kotlinx.coroutines.CoroutineDispatcher
import id.train.sportaria.util.Result
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class RemoteDataSource {
    open suspend fun <T> safeCallApi(
        dispatcher: CoroutineDispatcher,
        apiCall: suspend () -> T
    ) : Result<T> {
        return withContext(dispatcher){
            try {
                Result.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when(throwable) {
                    is HttpException -> {
                        val result = when(throwable.code()) {
                            in 500..599 -> parseError(
                                RequestResult.SERVER_ERROR,
                                throwable
                            )
                            else -> parseError(
                                RequestResult.NOT_DEFINED,
                                throwable
                            )
                        }
                        result
                    }
                    is UnknownHostException -> parseError(
                        RequestResult.NO_CONNECTION,
                        throwable
                    )
                    is SocketTimeoutException -> parseError(
                        RequestResult.TIMEOUT,
                        throwable
                    )
                    is IOException -> parseError(
                        RequestResult.BAD_RESPONSE,
                        throwable
                    )
                    else -> parseError(
                        RequestResult.NOT_DEFINED,
                        throwable
                    )
                }
            }
        }
    }

    private fun parseError(cause: RequestResult, e: Throwable): Result.Error {
        return Result.Error(cause, e)
    }
}