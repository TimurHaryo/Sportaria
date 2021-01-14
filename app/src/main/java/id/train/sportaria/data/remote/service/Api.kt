package id.train.sportaria.data.remote.service

import id.train.sportaria.data.remote.response.FootballResponse
import id.train.sportaria.data.remote.response.SportResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("all_sports.php")
    suspend fun getSports(): SportResponse

    @GET("lookup_all_teams.php")
    suspend fun getFootballs(
            @Query("id") query: String = "4328"
    ): FootballResponse

}