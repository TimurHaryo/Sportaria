package id.train.sportaria.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sports")
data class SportEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "poster")
    var poster: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "bookmarked")
    var bookmarked: Boolean
)