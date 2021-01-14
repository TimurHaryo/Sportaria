package id.train.sportaria.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.train.sportaria.data.local.entity.FootballEntity

@Dao
interface FootballDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFootball(footballEntity: FootballEntity): Long

    @Query("SELECT * FROM footballs")
    suspend fun getAllFootballs(): List<FootballEntity>

    @Query("SELECT * FROM footballs WHERE isBookmarked = 1")
    suspend fun getBookmarkedFootballs(): List<FootballEntity>

    @Query("SELECT id FROM footballs WHERE isBookmarked = 1")
    suspend fun getBookmarkedFootballsID(): List<String>

    @Query("SELECT * FROM footballs WHERE id = :id")
    suspend fun getFootball(id: String): FootballEntity

    @Query("UPDATE footballs SET isBookmarked = :isBookmarked WHERE id = :id")
    suspend fun updateBookmarkFootball(id: String, isBookmarked: Boolean): Int

    @Query("DELETE FROM footballs")
    suspend fun deleteAllFootballs()

}