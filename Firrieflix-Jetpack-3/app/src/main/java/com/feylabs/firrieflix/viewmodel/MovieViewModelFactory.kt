package com.feylabs.firrieflix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.feylabs.firrieflix.data.FirrieflixRepository
import com.feylabs.firrieflix.ui.movie.MovieViewModel

class MovieViewModelFactory(
    private val repository: FirrieflixRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return  MovieViewModel(repository) as T
    }


}