package id.train.sportaria.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.train.sportaria.data.local.entity.SportEntity

@Dao
interface SportDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSport(sportEntity: SportEntity): Long

    @Query("SELECT * FROM sports")
    suspend fun getAllSports(): List<SportEntity>

    @Query("SELECT * FROM sports WHERE bookmarked = 1")
    suspend fun getBookmarkedSports(): List<SportEntity>

    @Query("SELECT id FROM sports WHERE bookmarked = 1")
    suspend fun getBookmarkedSportsID(): List<String>

    @Query("SELECT * FROM sports WHERE id = :id")
    suspend fun getSport(id: String): SportEntity

    @Query("UPDATE sports SET bookmarked = :isBookmarked WHERE id = :id")
    suspend fun updateBookmarkSport(id: String, isBookmarked: Boolean): Int

    @Query("DELETE FROM sports")
    suspend fun deleteAllSports()

}