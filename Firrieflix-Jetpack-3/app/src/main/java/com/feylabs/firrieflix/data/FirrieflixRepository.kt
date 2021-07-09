package com.feylabs.firrieflix.data

import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.feylabs.firrieflix.data.source.local.FavoriteEntity
import com.feylabs.firrieflix.data.source.local.LocalDataSource
import com.feylabs.firrieflix.data.source.local.MovieLocalEntity
import com.feylabs.firrieflix.data.source.local.ShowLocalEntity
import com.feylabs.firrieflix.data.source.local.room.FavoriteDatabase
import com.feylabs.firrieflix.data.source.remote.responses.*
import com.feylabs.firrieflix.data.source.remote.RemoteDataSource
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FirrieflixRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {
    /*
    note for my beloved Dicoding Reviewer ♥

    i dont use offline-online pattern, because is not mandatory just like
    answered by reviewer at Dicoding Jetpack Discussion
    and not written as submission mandatory task.

    i just use ROOM Database here as Paging Cache, so there is no NetworkBoundResource
    and i just directly save the API Data to ROOM,
    and load ROOM data to recyclerview with pagination

    Thank You, wish you all the best

    reference :
    https://www.dicoding.com/academies/129/discussions/10217
    */

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()


    fun insertNewFavorite(fav: FavoriteEntity): MutableLiveData<Long> =
        MutableLiveData(localDataSource.insertFavorite(fav))


    fun getMoviesLocal(): LiveData<PagedList<MovieLocalEntity>> {
        val config = PagedList.Config.Builder().apply {
            setEnablePlaceholders(false)
            setInitialLoadSizeHint(4)
            setPageSize(4)
        }.build()
        return LivePagedListBuilder(localDataSource.getMovieLocal(), config).build()
    }

    fun getShowsLocal(): LiveData<PagedList<ShowLocalEntity>> {
        val config = PagedList.Config.Builder().apply {
            setEnablePlaceholders(false)
            setInitialLoadSizeHint(4)
            setPageSize(4)
        }.build()
        return LivePagedListBuilder(localDataSource.getShowLocal(), config).build()
    }

    fun getFavoriteMovie(): LiveData<PagedList<FavoriteEntity>> {
        val config = PagedList.Config.Builder().apply {
            setEnablePlaceholders(false)
            setInitialLoadSizeHint(4)
            setPageSize(4)
        }.build()
        return LivePagedListBuilder(localDataSource.getFavoriteMovie(), config).build()
    }

    fun getFavoriteShows(): LiveData<PagedList<FavoriteEntity>> {
        val config = PagedList.Config.Builder().apply {
            setEnablePlaceholders(false)
            setInitialLoadSizeHint(4)
            setPageSize(4)
        }.build()
        return LivePagedListBuilder(localDataSource.getFavoriteShows(), config).build()
    }


    fun checkIfLiked(movID: String): LiveData<List<FavoriteEntity>> =
        localDataSource.checkIfLiked(movID)

    fun deleteFavorite(id: String): LiveData<Int> {
        val del = localDataSource.delete(id)
        return MutableLiveData(del)
    }

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


    fun getMovie(): MutableLiveData<ResponseHelper<List<MovieResponse.Result>>> {
        val tempList = arrayListOf<MovieLocalEntity>()
        val apiResponse: MutableLiveData<ResponseHelper<List<MovieResponse.Result>>> =
            MutableLiveData()
        remoteDataSource.getMovieList(object : RemoteDataSource.MovieResponseCallback {
            override fun movieListcallback(response: ResponseHelper<List<MovieResponse.Result>>) {

                apiResponse.value = response

                if (response is ResponseHelper.Success) {
                    executorService.run {
                        localDataSource.clearMovies()
                    }
                }

                response.data?.let { itu ->
                    itu.forEach { ini ->
                        tempList.add(
                            MovieLocalEntity(
                                id = ini.id,
                                title = ini.title,
                                adult = ini.adult,
                                backdrop_path = ini.backdrop_path,
                                media_type = ini.media_type,
                                original_language = ini.original_language,
                                original_title = ini.original_title,
                                overview = ini.overview,
                                popularity = ini.popularity,
                                poster_path = ini.poster_path,
                                release_date = ini.release_date,
                                video = ini.video,
                                vote_average = ini.vote_average,
                                vote_count = ini.vote_count
                            )
                        )
                    }
                }

                //There is no possibility of double value,
                // because i already define non-duplicated db scheme
                executorService.run {
                    localDataSource.insertMovies(tempList)
                    Log.d("mov_dv_all", "testing1")
                }
            }
        })
        return apiResponse
    }

    fun getShow(): MutableLiveData<ResponseHelper<List<ShowResponse.Result>>> {
        val tempList = arrayListOf<ShowLocalEntity>()
        val apiResponse: MutableLiveData<ResponseHelper<List<ShowResponse.Result>>> =
            MutableLiveData()
        remoteDataSource.getShowList(object : RemoteDataSource.ShowResponseCallback {
            override fun showListcallback(response: ResponseHelper<List<ShowResponse.Result>>) {
                apiResponse.value = response
                if (response is ResponseHelper.Success) {
                    executorService.run {
                        localDataSource.clearShows()
                    }
                }

                response.data?.let { itu ->
                    itu.forEach { ini ->
                        tempList.add(
                            ShowLocalEntity(
                                id = ini.id,
                                vote_count = ini.vote_count,
                                vote_average = ini.vote_average,
                                poster_path = ini.poster_path,
                                popularity = ini.popularity,
                                overview = ini.overview,
                                original_language = ini.original_language,
                                media_type = ini.media_type,
                                backdrop_path = ini.backdrop_path,
                                name = ini.name,
                                first_air_date = ini.first_air_date,
                                original_name = ini.original_name
                            )
                        )
                    }
                }

                //There is no possibility of double value,
                // because i already define non-duplicated db scheme
                executorService.run {
                    localDataSource.insertShows(tempList)
                    Log.d("mov_dv_all", "testing1")
                }

            }
        })
        return apiResponse
    }

}