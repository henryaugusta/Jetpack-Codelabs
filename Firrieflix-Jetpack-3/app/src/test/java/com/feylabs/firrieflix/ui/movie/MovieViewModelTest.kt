package com.feylabs.firrieflix.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.feylabs.firrieflix.data.FirrieflixRepository
import com.feylabs.firrieflix.util.data_seed.Seeder
import com.feylabs.firrieflix.data.source.local.MovieLocalEntity
import com.feylabs.firrieflix.data.source.local.ShowLocalEntity
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Assert

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

    lateinit var viewModel: MovieViewModel

    @Mock
    private lateinit var myRepository: FirrieflixRepository

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        viewModel = MovieViewModel(myRepository)
    }


    @Mock
    private lateinit var observerMovie: Observer<PagedList<MovieLocalEntity>>

    @Test
    fun `test getting movie list`() {
        val myMockPagedMovie = MutableLiveData(mockPagedList(Seeder.getLocalMovies()))

        println(myMockPagedMovie.toString())
        Mockito.`when`(myRepository.getMoviesLocal()).thenReturn(myMockPagedMovie)

        val movieEntity = viewModel.getLocalMovies().value
        Mockito.verify(myRepository).getMoviesLocal()
        Assert.assertEquals(2, movieEntity?.size)
        assertNotNull(movieEntity)

        for (i in 0 until myMockPagedMovie.value?.size!!) {
            println("check object show number ${i+1}")
            val objectFromSeeder = myMockPagedMovie.value!![i]
            val objectFromViewModel = movieEntity?.get(i)
            assertEquals(objectFromSeeder?.id, objectFromViewModel?.id)
            assertEquals(objectFromSeeder?.title, objectFromViewModel?.title)
            assertEquals(objectFromSeeder?.backdrop_path, objectFromViewModel?.backdrop_path)
            assertEquals(objectFromSeeder?.original_language, objectFromViewModel?.original_language)
            assertEquals(objectFromSeeder?.popularity, objectFromViewModel?.popularity)
            assertEquals(objectFromSeeder?.poster_path, objectFromViewModel?.poster_path)
            assertEquals(objectFromSeeder?.vote_average, objectFromViewModel?.vote_average)
        }

        viewModel.getLocalMovies().observeForever(observerMovie)
        Mockito.verify(observerMovie).onChanged(myMockPagedMovie.value)
    }




    @Mock
    private lateinit var observerShow: Observer<PagedList<ShowLocalEntity>>

    @Test
    fun `test getting show`() {
        val myMockPagedShow = MutableLiveData(mockPagedList(Seeder.getLocalShow()))

        println(myMockPagedShow.toString())
        Mockito.`when`(myRepository.getShowsLocal()).thenReturn(myMockPagedShow)

        val showEntity = viewModel.getLocalShows().value
        Mockito.verify(myRepository).getShowsLocal()
        Assert.assertEquals(2, showEntity?.size)
        assertNotNull(showEntity)

        for (i in 0 until myMockPagedShow.value?.size!!) {
            println("check object show number ${i+1}")
            val objectFromSeeder = myMockPagedShow.value!![i]
            val objectFromViewModel = showEntity?.get(i)
            assertEquals(objectFromSeeder?.id, objectFromViewModel?.id)
            assertEquals(objectFromSeeder?.name, objectFromViewModel?.name)
            assertEquals(objectFromSeeder?.id, objectFromViewModel?.id)
            assertEquals(objectFromSeeder?.backdrop_path, objectFromViewModel?.backdrop_path)
            assertEquals(objectFromSeeder?.original_language, objectFromViewModel?.original_language)
            assertEquals(objectFromSeeder?.popularity, objectFromViewModel?.popularity)
            assertEquals(objectFromSeeder?.poster_path, objectFromViewModel?.poster_path)
            assertEquals(objectFromSeeder?.vote_average, objectFromViewModel?.vote_average)
        }


        viewModel.getLocalShows().observeForever(observerShow)
        Mockito.verify(observerShow).onChanged(myMockPagedShow.value)
    }


    private fun <T> mockPagedList(list: List<T>): PagedList<T> {
        val pagedList = Mockito.mock(PagedList::class.java) as PagedList<T>
        Mockito.`when`(pagedList.get(ArgumentMatchers.anyInt())).then { invocation ->
            val index = invocation.arguments.first() as Int
            list[index]
        }
        Mockito.`when`(pagedList.size).thenReturn(list.size)
        return pagedList
    }

}