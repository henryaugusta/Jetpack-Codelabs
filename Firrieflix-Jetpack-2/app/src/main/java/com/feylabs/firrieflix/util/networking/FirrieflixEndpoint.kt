package com.feylabs.firrieflix.util.networking

import com.feylabs.firrieflix.BuildConfig
import com.feylabs.firrieflix.data.source.remote.responses.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FirrieflixEndpoint {

    @GET("trending/movie/week")

    fun getTrendingMovie(): Call<MovieResponse>

    @GET("trending/tv/week")
    fun getTrendingShow(): Call<ShowResponse>

    @GET("movie/{movie_id}")
    fun getDetailMovie(
        @Path("movie_id") movie_id : String ,
        @Query("language") language: String = "en-US"
    ): Call<MovieDetailResponse>

    @GET("tv/{show_id}")
    fun getDetailShow(
        @Path("show_id") show_id : String ,
        @Query("language") language: String = "en-US"
    ): Call<ShowDetailResponse>

    @GET("movie/{movie_id}/credits")
    fun getMovieCredits(
        @Path("movie_id") movie_id: String ,
        @Query("language") language: String = "en-US"
    ): Call<MovieCreditsResponse>

    @GET("tv/{show_id}/credits")
    fun getShowCredits(
        @Path("show_id") show_id : String ,
        @Query("language") language: String = "en-US"
    ): Call<MovieCreditsResponse>


}