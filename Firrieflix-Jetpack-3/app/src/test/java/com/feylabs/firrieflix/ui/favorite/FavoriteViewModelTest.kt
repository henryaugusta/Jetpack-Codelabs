package com.feylabs.firrieflix.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.feylabs.firrieflix.data.FirrieflixRepository
import com.feylabs.firrieflix.data.ResponseHelper
import com.feylabs.firrieflix.data.source.local.FavoriteEntity
import com.feylabs.firrieflix.data.source.remote.responses.MovieDetailResponse
import com.feylabs.firrieflix.data.source.remote.responses.ShowDetailResponse
import com.feylabs.firrieflix.ui.detail.DetailMovieViewModel
import com.feylabs.firrieflix.util.PagedListUtil.mockPagedList
import com.feylabs.firrieflix.util.data_seed.Seeder
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert
import junit.framework.TestCase

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest {

    lateinit var viewModel: FavoriteViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var deleteObserver: Observer<Int>

    @Mock
    private lateinit var getFavoriteMovieObserver: Observer<PagedList<FavoriteEntity>>


    @Mock
    private lateinit var firrieflixRepository: FirrieflixRepository

    @Before
    fun setup() {
        viewModel = FavoriteViewModel(firrieflixRepository)
    }


    @Test
    fun deleteFavorite() {
        val dataDetail = Seeder.getFavoriteMovies()[0].movId.toString()
        Mockito.`when`(firrieflixRepository.deleteFavorite(dataDetail))
            .thenReturn(MutableLiveData(1))
        val deletedRow = viewModel.deleteFavorite(dataDetail)
        verify(firrieflixRepository).deleteFavorite(dataDetail)
        assertNotNull(deletedRow)
        assertEquals(1, deletedRow.value)

        viewModel.deleteFavorite(dataDetail).observeForever(deleteObserver)
        verify(deleteObserver).onChanged(deletedRow.value)
    }

    @Test
    fun `test getting favorite movie `() {
        val myMockPagedMovie = MutableLiveData(mockPagedList(Seeder.getFavoriteMovies()))

        Mockito.`when`(firrieflixRepository.getFavoriteMovie()).thenReturn(myMockPagedMovie)

        val movieEntity = viewModel.getAllFavMovies().value
        Mockito.verify(firrieflixRepository).getFavoriteMovie()
        assertEquals(2, movieEntity?.size)
        assertNotNull(movieEntity)

        for (i in 0 until myMockPagedMovie.value?.size!!) {
            println("check object show number ${i + 1}")
            val objectFromSeeder = myMockPagedMovie.value!![i]
            val objectFromViewModel = movieEntity?.get(i)
            assertEquals(objectFromSeeder?.id, objectFromViewModel?.id)
            assertEquals(objectFromSeeder?.name, objectFromViewModel?.name)
            assertEquals(objectFromSeeder?.desc, objectFromViewModel?.desc)
            assertEquals(objectFromSeeder?.poster, objectFromViewModel?.poster)
            assertEquals(objectFromSeeder?.favType, objectFromViewModel?.favType)
            assertEquals(objectFromSeeder?.movId, objectFromViewModel?.movId)
        }

        viewModel.getAllFavMovies().observeForever(getFavoriteMovieObserver)
        Mockito.verify(getFavoriteMovieObserver).onChanged(myMockPagedMovie.value)
    }

    @Test
    fun `test getting favorite show`() {
        val myMockPagedShow= MutableLiveData(mockPagedList(Seeder.getFavoriteShows()))

        Mockito.`when`(firrieflixRepository.getFavoriteShows()).thenReturn(myMockPagedShow)

        val showEntity = viewModel.getAllFavShow().value
        Mockito.verify(firrieflixRepository).getFavoriteShows()
        assertEquals(2, showEntity?.size)
        assertNotNull(showEntity)

        for (i in 0 until myMockPagedShow.value?.size!!) {
            println("check object show number ${i + 1}")
            val objectFromSeeder = myMockPagedShow.value!![i]
            val objectFromViewModel = showEntity?.get(i)
            assertEquals(objectFromSeeder?.id, objectFromViewModel?.id)
            assertEquals(objectFromSeeder?.name, objectFromViewModel?.name)
            assertEquals(objectFromSeeder?.desc, objectFromViewModel?.desc)
            assertEquals(objectFromSeeder?.poster, objectFromViewModel?.poster)
            assertEquals(objectFromSeeder?.favType, objectFromViewModel?.favType)
            assertEquals(objectFromSeeder?.movId, objectFromViewModel?.movId)
        }

        viewModel.getAllFavShow().observeForever(getFavoriteMovieObserver)
        Mockito.verify(getFavoriteMovieObserver).onChanged(myMockPagedShow.value)
    }


}