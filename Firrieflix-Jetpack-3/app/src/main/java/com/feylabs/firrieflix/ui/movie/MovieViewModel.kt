package com.feylabs.firrieflix.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.feylabs.firrieflix.data.FirrieflixRepository
import com.feylabs.firrieflix.data.source.local.FavoriteEntity
import com.feylabs.firrieflix.data.source.local.MovieLocalEntity
import com.feylabs.firrieflix.data.source.local.ShowLocalEntity

@Suppress("DEPRECATION") // Dicoding still use PagedList and LivePagedListBuilder
class MovieViewModel(private val repository: FirrieflixRepository) : ViewModel() {

    fun getShow() = repository.getShow()
    fun getMovie() = repository.getMovie()

    fun getLocalMovies() : LiveData<PagedList<MovieLocalEntity>> = repository.getMoviesLocal()
    fun getLocalShows() : LiveData<PagedList<ShowLocalEntity>> = repository.getShowsLocal()


}