package id.train.sportaria.data.remote.response


import com.google.gson.annotations.SerializedName

data class SportResponse(
    @SerializedName("sports")
    var dataSports: List<DataSport>?
) {
    data class DataSport(
        @SerializedName("idSport")
        var idSport: String,
        @SerializedName("strSport")
        var strSport: String,
        @SerializedName("strSportThumb")
        var strSportThumb: String,
        @SerializedName("strSportDescription")
        var strSportDescription: String
    )
}