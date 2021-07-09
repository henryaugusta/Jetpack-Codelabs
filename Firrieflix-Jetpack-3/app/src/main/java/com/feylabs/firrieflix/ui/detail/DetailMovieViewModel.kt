package com.feylabs.firrieflix.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.feylabs.firrieflix.data.FirrieflixRepository
import com.feylabs.firrieflix.data.source.remote.responses.MovieCreditsResponse
import com.feylabs.firrieflix.data.ResponseHelper
import com.feylabs.firrieflix.data.source.local.FavoriteEntity

class DetailMovieViewModel(private val repository: FirrieflixRepository) : ViewModel() {

    var movieCreditRes: LiveData<ResponseHelper<List<MovieCreditsResponse.Cast>>> =
        MutableLiveData()

    fun getDetailMovie(movieID: String) = repository.getDetailMovie(movieID)
    fun getDetailShow(movieID: String) = repository.getDetailShow(movieID)

    fun addToFav(model: FavoriteEntity): MutableLiveData<Long> = repository.insertNewFavorite(model)

    fun checkIfLiked(id: String) = repository.checkIfLiked(id)
    fun deleteFavorite(id: String) = repository.deleteFavorite(id)


    enum class MovType {
        MOVIE, SHOW
    }

}