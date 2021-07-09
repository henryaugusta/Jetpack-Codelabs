package com.feylabs.firrieflix.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.feylabs.firrieflix.data.source.local.FavoriteEntity
import com.feylabs.firrieflix.data.source.local.MovieLocalEntity
import com.feylabs.firrieflix.data.source.local.ShowLocalEntity


@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(fav: FavoriteEntity) : Long

    @Query("DELETE from tb_favorite WHERE favId=:idMov")
    fun delete(idMov: String): Int

    @Query("SELECT * from tb_favorite WHERE type = 1")
    fun getFavMovie(): DataSource.Factory<Int, FavoriteEntity>

    @Query("SELECT * from tb_favorite WHERE type =2")
    fun getFavShow(): DataSource.Factory<Int, FavoriteEntity>

    @Query("SELECT * from tb_favorite where favId=:idFav")
    fun checkIfLiked(idFav: String): LiveData<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = MovieLocalEntity::class)
    fun insertMovies(movies: List<MovieLocalEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = ShowLocalEntity::class)
    fun insertShows(shows: List<ShowLocalEntity>)

    @Query("SELECT * from tb_movies")
    fun getMovies(): DataSource.Factory<Int, MovieLocalEntity>

    @Query("SELECT * from tb_shows")
    fun getShows(): DataSource.Factory<Int, ShowLocalEntity>

    @Query("DELETE from tb_movies where id <>0")
    fun clearMovies(): Int

    @Query("DELETE from tb_shows where id <> 0")
    fun clearShows(): Int


}