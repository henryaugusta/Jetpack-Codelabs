package com.feylabs.firrieflix

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.feylabs.firrieflix.data.FirrieflixRepository
import com.feylabs.firrieflix.data.source.remote.responses.MovieResponse
import com.feylabs.firrieflix.data.source.remote.responses.ShowResponse
import com.feylabs.firrieflix.util.data_seed.Seeder
import com.feylabs.firrieflix.ui.movie.MovieViewModel
import com.feylabs.firrieflix.data.ResponseHelper
import com.nhaarman.mockitokotlin2.verify

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

    lateinit var viewModel: MovieViewModel

    @Mock
    private lateinit var myRepository: FirrieflixRepository

    @Mock
    private lateinit var observerMovie: Observer<ResponseHelper<List<MovieResponse.Result>>>

    @Mock
    private lateinit var observerShow: Observer<ResponseHelper<List<ShowResponse.Result>>>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        viewModel = MovieViewModel(myRepository)
    }



    @Test
    fun `test getting movie`() {
        val dummyDatas = Seeder.getMovies()
        val movies = MutableLiveData<List<MovieResponse.Result>>()
        movies.value = dummyDatas

        `when`(myRepository.getMovie()).thenReturn(
            MutableLiveData(
                ResponseHelper.Success(
                    "Success",
                    movies.value
                )
            )
        )
        val movie = viewModel.getMovie().value
        verify(myRepository).getMovie()
        assertNotNull(movie)
        assertEquals(2, movie?.data?.size)
        dummyDatas.forEachIndexed { counter, data ->
            val mov = movie?.data?.get(counter)
            assertEquals(dummyDatas[counter].original_title, mov?.original_title)
            assertEquals(dummyDatas[counter].adult, mov?.adult)
            assertEquals(dummyDatas[counter].genre_ids, mov?.genre_ids)
            assertEquals(dummyDatas[counter].original_language, mov?.original_language)
            assertEquals(dummyDatas[counter].popularity, mov?.popularity)
            assertEquals(dummyDatas[counter].poster_path, mov?.poster_path)
            assertEquals(dummyDatas[counter].vote_average, mov?.vote_average)
        }

        viewModel.getMovie().observeForever(observerMovie)
        verify(observerMovie).onChanged(
            movie
        )


    }

    @Test
    fun `test getting show`() {
        val dummyDatas = Seeder.getShow()
        val movies = MutableLiveData<List<ShowResponse.Result>>()
        movies.value = dummyDatas

        `when`(myRepository.getShow()).thenReturn(
            MutableLiveData(
                ResponseHelper.Success(
                    "Success",
                    movies.value
                )
            )
        )

        val movie = viewModel.getShow().value
        verify(myRepository).getShow()
        assertNotNull(movie)
        assertEquals(2, movie?.data?.size)
        dummyDatas.forEachIndexed { counter, data ->
            val mov = movie?.data?.get(counter)
            assertEquals(dummyDatas[counter].name, mov?.name)
            assertEquals(dummyDatas[counter].id, mov?.id)
            assertEquals(dummyDatas[counter].genre_ids, mov?.genre_ids)
            assertEquals(dummyDatas[counter].original_language, mov?.original_language)
            assertEquals(dummyDatas[counter].popularity, mov?.popularity)
            assertEquals(dummyDatas[counter].poster_path, mov?.poster_path)
            assertEquals(dummyDatas[counter].vote_average, mov?.vote_average)
        }

        viewModel.getShow().observeForever(observerShow)
        verify(observerShow).onChanged(
            movie
        )
    }

}