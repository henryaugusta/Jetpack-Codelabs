package com.feylabs.firrieflix.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.feylabs.firrieflix.data.source.local.FavoriteEntity
import com.feylabs.firrieflix.data.source.local.LocalDataSource
import com.feylabs.firrieflix.data.source.local.MovieLocalEntity
import com.feylabs.firrieflix.data.source.local.ShowLocalEntity
import com.feylabs.firrieflix.data.source.remote.RemoteDataSource
import com.feylabs.firrieflix.util.AppExecutors
import com.feylabs.firrieflix.util.LiveDataTestUtil
import com.feylabs.firrieflix.util.PagedListUtil
import com.feylabs.firrieflix.util.TestingExecutors
import com.feylabs.firrieflix.util.data_seed.Seeder
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`

@InternalCoroutinesApi
class FakeFirrieflixRepositoryTest {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testExecutors = AppExecutors(TestingExecutors, TestingExecutors, TestingExecutors)

    @Mock
    private val remoteDataSource = Mockito.mock(RemoteDataSource::class.java)
    private val localDataSource = Mockito.mock(LocalDataSource::class.java)
    private val repository = FakeFirrieflixRepository(remoteDataSource, localDataSource)
    private val appExecutors = Mockito.mock(AppExecutors::class.java)

    private val movieDetailDummy = Seeder.getDetailMovie()
    private val showDetailDummy = Seeder.getDetailShow()
    val movId = movieDetailDummy.id.toString()


    @Test
    fun getMoviesFromLocal() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieLocalEntity>
        Mockito.`when`(localDataSource.getMovieLocal()).thenReturn(dataSourceFactory)

        repository.getMovie()
        (repository.getMoviesLocal()).value

        val movieEntity = (PagedListUtil.mockPagedList(Seeder.getLocalMovies()))
        Mockito.verify(localDataSource).getMovieLocal()
        assertNotNull(movieEntity)
        assertEquals(Seeder.getMovies().size, movieEntity.size)
    }

    @Test
    fun getShow() {
        val showSeederDummy = Seeder.getShow()
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.ShowResponseCallback).showListcallback(
                ResponseHelper.Success("SUCCESS", showSeederDummy)
            )
            null
        }.`when`(remoteDataSource).getShowList(any())

        val show = LiveDataTestUtil.getValue(repository.getShow())
        verify(remoteDataSource).getShowList(any())
        assertNotNull(show)
        assertEquals(showSeederDummy.size, show.data?.size)
        showSeederDummy.forEachIndexed { counter, data ->
            val showz = show.data?.get(counter)
            assertEquals(showSeederDummy[counter].backdrop_path, showz?.backdrop_path)
            assertEquals(showSeederDummy[counter].name, showz?.name)
            assertEquals(showSeederDummy[counter].genre_ids, showz?.genre_ids)
            assertEquals(
                showSeederDummy[counter].original_language,
                showz?.original_language
            )
            assertEquals(showSeederDummy[counter].popularity, showz?.popularity)
            assertEquals(showSeederDummy[counter].poster_path, showz?.poster_path)
            assertEquals(showSeederDummy[counter].vote_average, showz?.vote_average)
        }
    }

    @Test
    fun getMovie() {
        val moviesSeederDummy = Seeder.getMovies()
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
            assertEquals(moviesSeederDummy[counter].original_title, mov?.original_title)
            assertEquals(moviesSeederDummy[counter].adult, mov?.adult)
            assertEquals(moviesSeederDummy[counter].genre_ids, mov?.genre_ids)
            assertEquals(
                moviesSeederDummy[counter].original_language,
                mov?.original_language
            )
            assertEquals(moviesSeederDummy[counter].popularity, mov?.popularity)
            assertEquals(moviesSeederDummy[counter].poster_path, mov?.poster_path)
            assertEquals(moviesSeederDummy[counter].vote_average, mov?.vote_average)
        }
    }

    @Test
    fun getShowsFromLocal() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, ShowLocalEntity>
        Mockito.`when`(localDataSource.getShowLocal()).thenReturn(dataSourceFactory)

        repository.getShowsLocal()
        val showEntity = (PagedListUtil.mockPagedList(Seeder.getLocalShow()))
        Mockito.verify(localDataSource).getShowLocal()
        assertNotNull(showEntity)
        assertEquals(Seeder.getMovies().size, showEntity.size)
    }

    @Test
    fun getFavoriteMovie() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, FavoriteEntity>
        Mockito.`when`(localDataSource.getFavoriteMovie()).thenReturn(dataSourceFactory)
        repository.getFavoriteMovie()

        val favoriteMovEntity = (PagedListUtil.mockPagedList(Seeder.getLocalShow()))
        Mockito.verify(localDataSource).getFavoriteMovie()
        assertNotNull(favoriteMovEntity)
        assertEquals(Seeder.getMovies().size, favoriteMovEntity.size)
    }

    @Test
    fun getFavoriteShow() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, FavoriteEntity>
        `when`(localDataSource.getFavoriteShows()).thenReturn(dataSourceFactory)
        repository.getFavoriteShows()

        val favoriteShowEntity = (PagedListUtil.mockPagedList(Seeder.getLocalShow()))
        Mockito.verify(localDataSource).getFavoriteShows()
        assertNotNull(favoriteShowEntity)
        assertEquals(Seeder.getMovies().size, favoriteShowEntity.size)
    }


    @Test
    fun getDetailShow() {
        val id = showDetailDummy.id.toString()
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.DetailShowCallback).detailShowCallback(
                ResponseHelper.Success("SUCCESS", showDetailDummy)
            )
            null
        }.`when`(remoteDataSource).getDetailShow(eq(id), any())

        val show = LiveDataTestUtil.getValue(repository.getDetailShow(id))
        verify(remoteDataSource).getDetailShow(eq(id), any())
        assertNotNull(show)
    }


    @Test
    fun getDetailMovie() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.DetailMovieCallback).detailMovieCallback(
                ResponseHelper.Success("SUCCESS", movieDetailDummy)
            )
            null
        }.`when`(remoteDataSource).getDetailMovie(movieID = eq(movId), callback = any())

        val movie = LiveDataTestUtil.getValue(repository.getDetailMovie(movId))
        verify(remoteDataSource).getDetailMovie(eq(movId), any())
        assertNotNull(movie)
        assertEquals(movieDetailDummy.poster_path, movie.data?.poster_path)
    }

    @Test
    fun setFavoriteMovies() {
        val dataDummy = Seeder.getFavoriteMovies()[0]
        `when`(appExecutors.diskIO()).thenReturn(testExecutors.diskIO())
        `when`(localDataSource.insertFavorite(dataDummy)).thenReturn(dataDummy.id?.toLong())
        `when`(localDataSource.insertFavorite(dataDummy)).thenReturn(dataDummy.id?.toLong())
        repository.insertNewFavorite(dataDummy)
        verify(localDataSource).insertFavorite(dataDummy)
    }

    @Test
    fun deleteFavorite() {
        val dataDummy = Seeder.getFavoriteMovies()[0].id.toString()
        `when`(appExecutors.diskIO()).thenReturn(testExecutors.diskIO())
        `when`(localDataSource.delete(dataDummy)).thenReturn(1) // simulate how 1 row deleted at ROOM
        val deleteSimul = repository.deleteFavorite(dataDummy)
        verify(localDataSource).delete(dataDummy)
        assertNotNull(deleteSimul)
        assertEquals(1, deleteSimul.value)
    }

    @Test
    fun checkIfLiked() {
        val dataDummy = Seeder.isMovieFavorited()
        val liveDataResponse = MutableLiveData(dataDummy)
        `when`(appExecutors.diskIO()).thenReturn(testExecutors.diskIO())
        `when`(localDataSource.checkIfLiked(dataDummy[0].toString())).thenReturn(
            liveDataResponse
        )
        val getListOfLiked = repository.checkIfLiked(dataDummy[0].toString())
        verify(localDataSource).checkIfLiked(dataDummy[0].toString())
        assertNotNull(getListOfLiked)
    }


}