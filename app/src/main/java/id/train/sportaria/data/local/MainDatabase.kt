package id.train.sportaria.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import id.train.sportaria.data.local.dao.FootballDao
import id.train.sportaria.data.local.dao.SportDao
import id.train.sportaria.data.local.entity.FootballEntity
import id.train.sportaria.data.local.entity.SportEntity

@Database(
    entities = [SportEntity::class, FootballEntity::class],
    version = 1
)
abstract class MainDatabase : RoomDatabase() {

    abstract fun getSportDao(): SportDao
    abstract fun getFootballDao(): FootballDao

    companion object {
        const val DB_NAME: String = "fooball_db"
    }
}