package com.feylabs.firrieflix

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.feylabs.firrieflix.data.FirrieflixRepository
import com.feylabs.firrieflix.data.source.remote.responses.MovieCreditsResponse
import com.feylabs.firrieflix.data.source.remote.responses.MovieDetailResponse
import com.feylabs.firrieflix.data.source.remote.responses.ShowDetailResponse
import com.feylabs.firrieflix.ui.detail.DetailMovieViewModel
import com.feylabs.firrieflix.util.data_seed.Seeder
import com.feylabs.firrieflix.data.ResponseHelper
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class DetailMovieViewModelTest {

    lateinit var viewModel: DetailMovieViewModel

    val movieID = Seeder.getDetailMovie().id.toString()
    val showID = Seeder.getDetailShow().id.toString()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieDetailObserver: Observer<ResponseHelper<MovieDetailResponse>>

    @Mock
    private lateinit var showDetailObserver: Observer<ResponseHelper<ShowDetailResponse>>

    @Mock
    private lateinit var castDetailObserver: Observer<ResponseHelper<List<MovieCreditsResponse.Cast>>>

    @Mock
    private lateinit var firrieflixRepository: FirrieflixRepository

    @Before
    fun setup() {
        viewModel = DetailMovieViewModel(firrieflixRepository)
    }

    @Test
    fun getMovieDetail() {
        val dummyDetail = Seeder.getDetailMovie()
        val detail: MutableLiveData<ResponseHelper<MovieDetailResponse>> = MutableLiveData()
        detail.value = ResponseHelper.Success("success", dummyDetail)

        `when`(firrieflixRepository.getDetailMovie(movieID)).thenReturn(detail)

        val detailMovie = viewModel.getDetailMovie(movieID).value?.data

        verify(firrieflixRepository).getDetailMovie(movieID)
        assertNotNull(detailMovie)

        dummyDetail.apply {
            assertEquals(title, detailMovie?.title)
            assertEquals(budget, detailMovie?.budget)
            assertEquals(adult, detailMovie?.adult)
            assertEquals(original_language, detailMovie?.original_language)
            assertEquals(overview, detailMovie?.overview)
            assertEquals(imdb_id, detailMovie?.imdb_id)
            assertEquals(homepage, detailMovie?.homepage)
            assertEquals(genres, detailMovie?.genres)
            assertEquals(poster_path, detailMovie?.poster_path)
            assertEquals(production_companies, detailMovie?.production_companies)
            assertEquals(production_countries, detailMovie?.production_countries)
            assertEquals(popularity, detailMovie?.popularity)
            assertEquals(release_date, detailMovie?.release_date)
            assertEquals(spoken_languages, detailMovie?.spoken_languages)
            assertEquals(tagline, detailMovie?.tagline)
        }

        viewModel.getDetailMovie(movieID).observeForever(movieDetailObserver)
        verify(movieDetailObserver).onChanged(detail.value)
    }

    @Test
    fun getShowDetail() {
        val dummyDetail = Seeder.getDetailShow()
        val detail: MutableLiveData<ResponseHelper<ShowDetailResponse>> = MutableLiveData()
        detail.value = ResponseHelper.Success("success", dummyDetail)

        `when`(firrieflixRepository.getDetailShow(showID)).thenReturn(detail)

        val detailMovie = viewModel.getDetailShow(showID).value?.data

        verify(firrieflixRepository).getDetailShow(showID)
        assertNotNull(detailMovie)

        dummyDetail.apply {
            assertEquals(original_language, detailMovie?.original_language)
            assertEquals(overview, detailMovie?.overview)
            assertEquals(homepage, detailMovie?.homepage)
            assertEquals(genres, detailMovie?.genres)
            assertEquals(poster_path, detailMovie?.poster_path)
            assertEquals(production_companies, detailMovie?.production_companies)
            assertEquals(production_countries, detailMovie?.production_countries)
            assertEquals(popularity, detailMovie?.popularity)
            assertEquals(spoken_languages, detailMovie?.spoken_languages)
            assertEquals(tagline, detailMovie?.tagline)
        }

        viewModel.getDetailShow(showID).observeForever(showDetailObserver)
        verify(showDetailObserver).onChanged(detail.value)
    }

    @Test
    fun `get Movie Credits`() {
        val dummyData = Seeder.getDummyCast()
        val casts = MutableLiveData<ResponseHelper<List<MovieCreditsResponse.Cast>>>()
        casts.value = ResponseHelper.Success("SUCCESS", dummyData)

        `when`(
            firrieflixRepository.getMovieCredit(
                id = movieID,
                DetailMovieViewModel.MovType.MOVIE
            )
        ).thenReturn(
            casts
        )

        viewModel.getCredits(movieID,DetailMovieViewModel.MovType.MOVIE)
        val detailCast = viewModel.movieCreditRes.value
        verify(firrieflixRepository).getMovieCredit(movieID,DetailMovieViewModel.MovType.MOVIE)
        assertNotNull(detailCast)

        viewModel.movieCreditRes.observeForever(castDetailObserver)
        verify(castDetailObserver).onChanged(detailCast)

    }


}