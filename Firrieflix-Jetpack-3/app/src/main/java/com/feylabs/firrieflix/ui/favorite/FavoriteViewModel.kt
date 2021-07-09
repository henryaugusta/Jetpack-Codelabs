package com.feylabs.firrieflix.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.feylabs.firrieflix.data.FirrieflixRepository
import com.feylabs.firrieflix.data.source.local.FavoriteEntity

class FavoriteViewModel(private val repository: FirrieflixRepository) : ViewModel() {
    fun getAllFavMovies(): LiveData<PagedList<FavoriteEntity>> = repository.getFavoriteMovie()
    fun getAllFavShow(): LiveData<PagedList<FavoriteEntity>> = repository.getFavoriteShows()
    fun deleteFavorite(id: String) = repository.deleteFavorite(id)
}