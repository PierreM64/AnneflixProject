package com.mbds.anneflixproject.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mbds.anneflixproject.models.FavoriteSeriesDatabase

@Dao
interface SerieDao {

    @Query("SELECT * FROM tbl_series")
    fun getAll(): List<FavoriteSeriesDatabase>

    @Insert
    fun insert(serie: FavoriteSeriesDatabase)

    @Query("SELECT * FROM tbl_series WHERE id = :id LIMIT 1")
    fun findById(id: Long): FavoriteSeriesDatabase?

    @Query("DELETE FROM tbl_series WHERE id = :id")
    fun delete(id: Long)
}