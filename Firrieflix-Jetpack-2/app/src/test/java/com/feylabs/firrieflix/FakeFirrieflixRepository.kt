package com.feylabs.firrieflix

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.feylabs.firrieflix.data.source.remote.RemoteDataSource
import com.feylabs.firrieflix.data.source.remote.responses.*
import com.feylabs.firrieflix.ui.detail.DetailMovieViewModel
import com.feylabs.firrieflix.data.ResponseHelper

class FakeFirrieflixRepository(val remoteDataSource: RemoteDataSource) {

    fun getDetailMovie(id: String): LiveData<ResponseHelper<MovieDetailResponse>> {
        val apiResponse: MutableLiveData<ResponseHelper<MovieDetailResponse>> = MutableLiveData()
        remoteDataSource.getDetailMovie(id, object : RemoteDataSource.DetailMovieCallback {
            override fun detailMovieCallback(response: ResponseHelper<MovieDetailResponse>) {
                apiResponse.postValue(response)
            }
        })
        return apiResponse
    }

    fun getDetailShow(id: String): LiveData<ResponseHelper<ShowDetailResponse>> {
        val apiResponse: MutableLiveData<ResponseHelper<ShowDetailResponse>> = MutableLiveData()
        remoteDataSource.getDetailShow(id, object : RemoteDataSource.DetailShowCallback {
            override fun detailShowCallback(response: ResponseHelper<ShowDetailResponse>) {
                apiResponse.postValue(response)
            }
        })
        return apiResponse
    }

    fun getMovieCredit(
        id: String,
        type: DetailMovieViewModel.MovType
    ): LiveData<ResponseHelper<List<MovieCreditsResponse.Cast>>> {
        val apiResponse: MutableLiveData<ResponseHelper<List<MovieCreditsResponse.Cast>>> =
            MutableLiveData()

        if (type == DetailMovieViewModel.MovType.SHOW) {
            remoteDataSource.getShowCredit(id, object : RemoteDataSource.MovieCreditsCallback {
                override fun movieCreditCallback(response: ResponseHelper<List<MovieCreditsResponse.Cast>>) {
                    apiResponse.postValue(response)
                }
            })
        }

        if (type == DetailMovieViewModel.MovType.MOVIE) {
            remoteDataSource.getMovieCredit(id, object : RemoteDataSource.MovieCreditsCallback {
                override fun movieCreditCallback(response: ResponseHelper<List<MovieCreditsResponse.Cast>>) {
                    apiResponse.postValue(response)
                }
            })
        }

        return apiResponse
    }

    fun getMovie(): LiveData<ResponseHelper<List<MovieResponse.Result>>> {
        val apiResponse: MutableLiveData<ResponseHelper<List<MovieResponse.Result>>> =
            MutableLiveData()
        remoteDataSource.getMovieList(object : RemoteDataSource.MovieResponseCallback {

            override fun movieListcallback(response: ResponseHelper<List<MovieResponse.Result>>) {
                apiResponse.postValue(response)
            }

        })
        return apiResponse
    }

    fun getShow(): LiveData<ResponseHelper<List<ShowResponse.Result>>> {
        val apiResponse: MutableLiveData<ResponseHelper<List<ShowResponse.Result>>> =
            MutableLiveData()
        remoteDataSource.getShowList(object : RemoteDataSource.ShowResponseCallback {
            override fun showListcallback(response: ResponseHelper<List<ShowResponse.Result>>) {
                apiResponse.postValue(response)
            }
        })
        return apiResponse
    }
}