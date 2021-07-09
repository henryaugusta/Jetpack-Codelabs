package com.feylabs.firrieflix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.feylabs.firrieflix.data.FirrieflixRepository
import com.feylabs.firrieflix.ui.detail.DetailMovieViewModel
import com.feylabs.firrieflix.ui.movie.MovieViewModel

class ViewModelFactory constructor(val repository: FirrieflixRepository) :

    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailMovieViewModel::class.java) -> {
                DetailMovieViewModel(repository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

    }
}