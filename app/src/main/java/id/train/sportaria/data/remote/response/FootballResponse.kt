package id.train.sportaria.data.remote.response


import com.google.gson.annotations.SerializedName

data class FootballResponse(
    @SerializedName("teams")
    var teams: List<Team>?
) {
    data class Team(
        @SerializedName("idTeam")
        var idTeam: String,
        @SerializedName("strTeam")
        var strTeam: String,
        @SerializedName("intFormedYear")
        var intFormedYear: String,
        @SerializedName("strStadium")
        var strStadium: String,
        @SerializedName("strStadiumThumb")
        var strStadiumThumb: String,
        @SerializedName("strStadiumDescription")
        var strStadiumDescription: String,
        @SerializedName("strDescriptionEN")
        var strDescription: String,
        @SerializedName("strTeamBadge")
        var strTeamBadge: String,
        @SerializedName("strTeamBanner")
        var strTeamBanner: String
    )
}