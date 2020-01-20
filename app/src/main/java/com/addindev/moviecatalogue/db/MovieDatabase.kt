package com.addindev.moviecatalogue.db

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room
import com.addindev.moviecatalogue.ui.movie.MovieDao
import com.addindev.moviecatalogue.ui.movie.pojo.ResultsItem
import com.addindev.moviecatalogue.ui.tvshow.TvShowDao


@Database(entities = [ResultsItem::class, com.addindev.moviecatalogue.ui.tvshow.pojo.ResultsItem::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun tvShowDao(): TvShowDao

    companion object{
        const val DB_NAME = "movie_catalogue_database"

        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            if (INSTANCE == null) {

                synchronized(MovieDatabase::class.java) {

                    if (INSTANCE == null) {

                        INSTANCE = Room.databaseBuilder(context,
                                MovieDatabase::class.java, DB_NAME)
                                .fallbackToDestructiveMigration()
                                .build()

                    }
                }
            }
            return INSTANCE!!
        }
    }
}