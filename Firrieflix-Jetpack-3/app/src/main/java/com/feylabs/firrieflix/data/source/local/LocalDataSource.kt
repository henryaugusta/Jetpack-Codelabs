package com.feylabs.firrieflix.data.source.local

import androidx.paging.DataSource
import com.feylabs.firrieflix.data.source.local.room.FavoriteDao

class LocalDataSource(private val favoriteDao: FavoriteDao) {

    fun getFavoriteShows(): DataSource.Factory<Int, FavoriteEntity> = favoriteDao.getFavShow()
    fun getFavoriteMovie(): DataSource.Factory<Int, FavoriteEntity> = favoriteDao.getFavMovie()

    fun getMovieLocal(): DataSource.Factory<Int, MovieLocalEntity> = favoriteDao.getMovies()
    fun getShowLocal(): DataSource.Factory<Int, ShowLocalEntity> = favoriteDao.getShows()

    fun insertFavorite(body: FavoriteEntity) : Long = favoriteDao.insert(body)
    fun checkIfLiked(id: String) = favoriteDao.checkIfLiked(id)
    fun delete(id: String) = favoriteDao.delete(id)
    fun clearMovies() = favoriteDao.clearMovies()
    fun clearShows() = favoriteDao.clearShows()

    fun insertMovies(list: List<MovieLocalEntity>) = favoriteDao.insertMovies(list)
    fun insertShows(list: List<ShowLocalEntity>) = favoriteDao.insertShows(list)


}