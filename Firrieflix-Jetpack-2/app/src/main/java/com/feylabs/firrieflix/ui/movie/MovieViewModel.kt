package com.feylabs.firrieflix.ui.movie

import androidx.lifecycle.ViewModel
import com.feylabs.firrieflix.data.FirrieflixRepository

class MovieViewModel(private val repository: FirrieflixRepository) : ViewModel() {


    fun getShow() = repository.getShow()
    fun getMovie() = repository.getMovie()

}