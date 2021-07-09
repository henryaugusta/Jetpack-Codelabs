package com.feylabs.firrieflix.viewmodel

import com.feylabs.firrieflix.R
import com.feylabs.firrieflix.model.MovieModel
import com.feylabs.firrieflix.util.seeder.MovieSeeder
import junit.framework.TestCase

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class DetailMovieViewModelTest {
    private lateinit var viewModel: DetailMovieViewModel

    @Before
    fun setUp() {
        viewModel = DetailMovieViewModel()
    }

    @Test
    fun getMovieDetail() {
        val movieEntity = viewModel.findMovieById(1)
        assertNotNull(movieEntity)
        movieEntity.let {
            assertNotNull(it.id)
            assertNotNull(it.description)
            assertNotNull(it.theme)
            assertNotNull(it.releaseDate)
            assertNotNull(it.director)
            assertNotNull(it.imbdRating)
            assertNotNull(it.artist)
            assertNotNull(it.type)
        }

    }

    @Test
    fun checkFilmContent() {
        val movieEntity = viewModel.findMovieById(1)
        movieEntity.let {

            //Check all output in model one by one
            assertEquals(it.id, 1)
            assertEquals(it.title, "A Start is Born")
            assertEquals(it.type, MovieModel.MovieType.MOVIE)
            assertEquals(
                it.description,
                "Seorang bintang musik country yang karirnya mulai memudar, Jackson Maine (Bradley Cooper) menemukan sebuah talenta yang sangat berbakat di dalam diri dari seorang musisi muda bernama Ally (Lady Gaga). Maine berhasil mengorbitkan Ally menjadi seorang bintang muda yang menjanjikan. Namun keduanya terlibat hubungan yang lebih jauh dari sekedar mentor dan anak didik. Seiring dengan meroketnya karir dari Ally dan dirinya, Maine mengalami dilema mengenai masalah ini."
            )
            assertEquals(it.releaseDate, "05/10/2018")
            assertEquals(it.director, "Bradley Cooper")
            assertEquals(it.imbdRating, "75")
            assertEquals(it.img_link.toInt(), R.drawable.poster_a_start_is_born)
        }
    }

    @Test
    fun checkArtist() {
        val movieArtis = viewModel.findMovieById(1).artist

        movieArtis.let {
            assertEquals(3, it.size)
            movieArtis[0].let { artist ->
                assertEquals(artist.name,"Bradley Cooper")
                assertEquals(artist.play_as,"Jackson Maine")
            }
        }
    }


}