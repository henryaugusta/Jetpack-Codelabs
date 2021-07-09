package com.feylabs.firrieflix.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.feylabs.firrieflix.data.FirrieflixRepository
import com.feylabs.firrieflix.data.source.remote.responses.MovieCreditsResponse
import com.feylabs.firrieflix.data.ResponseHelper

class DetailMovieViewModel(private val repository: FirrieflixRepository) : ViewModel() {

    var movieCreditRes: LiveData<ResponseHelper<List<MovieCreditsResponse.Cast>>> =
        MutableLiveData()

    fun getDetailMovie(movieID: String) = repository.getDetailMovie(movieID)
    fun getDetailShow(movieID: String) = repository.getDetailShow(movieID)


    fun getCredits(movieID: String, type: MovType) {
        if (type == MovType.MOVIE)
            movieCreditRes = repository.getMovieCredit(movieID, MovType.MOVIE)
        if (type == MovType.SHOW)
            movieCreditRes = repository.getMovieCredit(movieID, MovType.SHOW)
    }

    enum class MovType {
        MOVIE, SHOW
    }

}