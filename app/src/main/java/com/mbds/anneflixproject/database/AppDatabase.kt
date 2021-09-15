package com.mbds.anneflixproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mbds.anneflixproject.models.FavoriteMoviesDatabase
import com.mbds.anneflixproject.models.FavoriteSeriesDatabase

@Database(entities = [FavoriteMoviesDatabase::class, FavoriteSeriesDatabase::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun serieDao(): SerieDao

    /*** Ref no_cours DEBUT
    // Ici, la fonction getDatabase permet de récupérer une unique instance de la base de données pour toute l'application
    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
     Ref no_cours FIN */
}