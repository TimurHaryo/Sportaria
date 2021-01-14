package id.train.sportaria.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "footballs")
data class FootballEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: String,
    @ColumnInfo(name = "strTeam")
    var strTeam: String,
    @ColumnInfo(name = "intFormedYear")
    var intFormedYear: String,
    @ColumnInfo(name = "strStadium")
    var strStadium: String,
    @ColumnInfo(name = "strStadiumThumb")
    var strStadiumThumb: String,
    @ColumnInfo(name = "strStadiumDescription")
    var strStadiumDescription: String,
    @ColumnInfo(name = "strDescription")
    var strDescription: String,
    @ColumnInfo(name = "strTeamBadge")
    var strTeamBadge: String,
    @ColumnInfo(name = "strTeamBanner")
    var strTeamBanner: String,
    @ColumnInfo(name = "isBookmarked")
    var isBookmarked: Boolean
)