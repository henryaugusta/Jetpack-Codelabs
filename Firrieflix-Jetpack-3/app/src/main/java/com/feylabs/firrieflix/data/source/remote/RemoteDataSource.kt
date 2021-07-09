package com.feylabs.firrieflix.data.source.remote

import com.feylabs.firrieflix.data.source.remote.responses.*
import com.feylabs.firrieflix.util.networking.ApiConfig
import com.feylabs.firrieflix.util.EspressoIdlingResource
import com.feylabs.firrieflix.data.ResponseHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {

    fun getDetailMovie(movieID: String, callback: DetailMovieCallback) {
        callback.detailMovieCallback(ResponseHelper.Loading())
        EspressoIdlingResource.increment()
        ApiConfig.getClient().getDetailMovie(movieID)
            .enqueue(object : Callback<MovieDetailResponse> {
                override fun onResponse(
                    call: Call<MovieDetailResponse>,
                    response: Response<MovieDetailResponse>
                ) {
                    EspressoIdlingResource.decrement()
                    response.body()?.let {
                        callback.detailMovieCallback(
                            ResponseHelper.Success(
                                response.message(),
                                it
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                    EspressoIdlingResource.decrement()
                    callback.detailMovieCallback(ResponseHelper.Error(t.message.toString()))
                }

            })
    }

    fun getDetailShow(show_id: String, callback: DetailShowCallback) {
        EspressoIdlingResource.increment()
        ApiConfig.getClient().getDetailShow(show_id).enqueue(object : Callback<ShowDetailResponse> {
            override fun onResponse(
                call: Call<ShowDetailResponse>,
                response: Response<ShowDetailResponse>
            ) {
                EspressoIdlingResource.decrement()
                callback.detailShowCallback(
                    ResponseHelper.Success(
                        response.message(),
                        response.body()
                    )
                )
            }

            override fun onFailure(call: Call<ShowDetailResponse>, t: Throwable) {
                EspressoIdlingResource.decrement()
                callback.detailShowCallback(ResponseHelper.Error(t.message.toString()))
            }

        })

    }


    fun getMovieList(callback: MovieResponseCallback) {
        EspressoIdlingResource.increment()

        callback.movieListcallback(ResponseHelper.Loading())
        ApiConfig.getClient().getTrendingMovie().enqueue(object : Callback<MovieResponse> {

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                EspressoIdlingResource.decrement()
                callback.movieListcallback(ResponseHelper.Error(t.message.toString()))
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                EspressoIdlingResource.decrement()
                callback.movieListcallback(
                    ResponseHelper.Success(response.message(), response.body()?.results)
                )
            }


        })
    }


    fun getShowList(callback: ShowResponseCallback) {
        EspressoIdlingResource.increment()

        callback.showListcallback(ResponseHelper.Loading())
        ApiConfig.getClient().getTrendingShow().enqueue(object : Callback<ShowResponse> {

            override fun onFailure(call: Call<ShowResponse>, t: Throwable) {
                EspressoIdlingResource.decrement()
                callback.showListcallback(ResponseHelper.Error(t.message.toString()))
            }

            override fun onResponse(call: Call<ShowResponse>, response: Response<ShowResponse>) {
                EspressoIdlingResource.decrement()
                callback.showListcallback(
                    ResponseHelper.Success(
                        response.message(),
                        response.body()?.results
                    )
                )
            }

        })
    }

    interface DetailMovieCallback {
        fun detailMovieCallback(response: ResponseHelper<MovieDetailResponse>)
    }

    interface DetailShowCallback {
        fun detailShowCallback(response: ResponseHelper<ShowDetailResponse>)
    }

    interface MovieCreditsCallback {
        fun movieCreditCallback(response: ResponseHelper<List<MovieCreditsResponse.Cast>>)
    }

    interface MovieResponseCallback {
        fun movieListcallback(response: ResponseHelper<List<MovieResponse.Result>>)
    }

    interface ShowResponseCallback {
        fun showListcallback(response: ResponseHelper<List<ShowResponse.Result>>)
    }


}

