package com.mbds.anneflixproject.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mbds.anneflixproject.models.FavoriteMoviesDatabase

@Dao
interface MovieDao {

    @Query("SELECT * FROM tbl_movies")
    fun getAll(): List<FavoriteMoviesDatabase>

    @Insert
    fun insert(movie: FavoriteMoviesDatabase)

    @Query("SELECT * FROM tbl_movies WHERE id = :id LIMIT 1")
    fun findById(id: Long): FavoriteMoviesDatabase?

    @Query("DELETE FROM tbl_movies WHERE id = :id")
    fun delete(id: Long)
}