package com.feylabs.firrieflix.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.feylabs.firrieflix.data.source.local.FavoriteEntity
import com.feylabs.firrieflix.data.source.local.MovieLocalEntity
import com.feylabs.firrieflix.data.source.local.ShowLocalEntity
import com.feylabs.firrieflix.data.source.remote.responses.MovieResponse


@Database(
    entities = [FavoriteEntity::class, MovieLocalEntity::class, ShowLocalEntity::class],
    version = 2
)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun favDao(): FavoriteDao

    companion object {

        const val DB_NAME = "favorite.db"

        @Volatile
        private var instance: FavoriteDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                FavoriteDatabase::class.java, "favorite.db"
            ).allowMainThreadQueries()
                .build()
    }
}