package com.feylabs.firrieflix

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.feylabs.firrieflix.data.source.remote.RemoteDataSource
import com.feylabs.firrieflix.ui.detail.DetailMovieViewModel
import com.feylabs.firrieflix.util.data_seed.Seeder
import com.feylabs.firrieflix.util.preference.LiveDataTestUtil
import com.feylabs.firrieflix.data.ResponseHelper
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito


class FakeFirrieflixRepositoryTest {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remoteDataSource = Mockito.mock(RemoteDataSource::class.java)
    private val repository = FakeFirrieflixRepository(remoteDataSource)

    private val moviesSeederDummy = Seeder.getMovies()
    private val showSeederDummy = Seeder.getShow()
    private val movieDetailDummy = Seeder.getDetailMovie()
    private val showDetailDummy = Seeder.getDetailShow()
    private val castDummy = Seeder.getDummyCast()
    val movId = movieDetailDummy.id.toString()

    @Test
    fun getDetailShow() {
        val id = showDetailDummy.id.toString()
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.DetailShowCallback).detailShowCallback(
                ResponseHelper.Success("SUCCESS", showDetailDummy)
            )
            null
        }.`when`(remoteDataSource).getDetailShow(eq(id),any())

        val show = LiveDataTestUtil.getValue(repository.getDetailShow(id))
        verify(remoteDataSource).getDetailShow(eq(id),any())
        assertNotNull(show)
    }

    @Test
    fun getDetailMovie() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.DetailMovieCallback).detailMovieCallback(
                ResponseHelper.Success("SUCCESS", movieDetailDummy)
            )
            null
        }.`when`(remoteDataSource).getDetailMovie(movieID = eq(movId),callback = any())

        val movie = LiveDataTestUtil.getValue(repository.getDetailMovie(movId))
        verify(remoteDataSource).getDetailMovie(eq(movId), any())
        assertNotNull(movie)
        assertEquals(movieDetailDummy.poster_path,movie.data?.poster_path)
    }

    @Test
    fun getCreditsMovie() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.MovieCreditsCallback).movieCreditCallback(
                ResponseHelper.Success("SUCCESS",castDummy)
            )
            null
        }.`when`(remoteDataSource).getMovieCredit(movieID = eq(movId),callback = any())

        val movie = LiveDataTestUtil.getValue(repository.getMovieCredit(movId,DetailMovieViewModel.MovType.MOVIE))
        verify(remoteDataSource).getMovieCredit(eq(movId), any())
        assertNotNull(movie)
        assertEquals(castDummy[0].adult,castDummy[0].adult)
    }

    @Test
    fun getMovieList() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.MovieResponseCallback).movieListcallback(
                ResponseHelper.Success("SUCCESS", moviesSeederDummy)
            )
            null
        }.`when`(remoteDataSource).getMovieList(any())

        val movies = LiveDataTestUtil.getValue(repository.getMovie())
        verify(remoteDataSource).getMovieList(any())
        assertNotNull(movies)
        assertEquals(moviesSeederDummy.size, movies.data?.size)
        moviesSeederDummy.forEachIndexed { counter, data ->
            val mov = movies.data?.get(counter)
            Assert.assertEquals(moviesSeederDummy[counter].original_title, mov?.original_title)
            Assert.assertEquals(moviesSeederDummy[counter].adult, mov?.adult)
            Assert.assertEquals(moviesSeederDummy[counter].genre_ids, mov?.genre_ids)
            Assert.assertEquals(moviesSeederDummy[counter].original_language, mov?.original_language)
            Assert.assertEquals(moviesSeederDummy[counter].popularity, mov?.popularity)
            Assert.assertEquals(moviesSeederDummy[counter].poster_path, mov?.poster_path)
            Assert.assertEquals(moviesSeederDummy[counter].vote_average, mov?.vote_average)
        }
    }

    @Test
    fun getShowList() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.ShowResponseCallback).showListcallback(
                ResponseHelper.Success("SUCCESS", showSeederDummy)
            )
            null
        }.`when`(remoteDataSource).getShowList(any())

        val showFromRemote = LiveDataTestUtil.getValue(repository.getShow())
        verify(remoteDataSource).getShowList(any())
        assertNotNull(showFromRemote)
        assertEquals(showSeederDummy.size, showFromRemote.data?.size)
    }


}