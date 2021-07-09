package com.feylabs.firrieflix.viewmodel

import com.feylabs.firrieflix.R
import com.feylabs.firrieflix.model.MovieModel
import com.feylabs.firrieflix.util.seeder.MovieSeeder
import junit.framework.TestCase

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class ShowMovieViewModelTest {

    private lateinit var viewModel: DetailMovieViewModel
    private lateinit var showViewModel: ShowViewModel

    @Before
    fun setUp() {
        viewModel = DetailMovieViewModel()
        showViewModel = ShowViewModel()
    }

    @Test
    fun getShowDetail() {
        val show = showViewModel.getShow()[0]
        assertNotNull(show)
        val showEntity = viewModel.findMovieById(show.id)
        assertNotNull(showEntity)
        showEntity.let {
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
    fun checkShowContent() {
        val showEntity = showViewModel.getShow()[0]
        showEntity.let {

            //Check all output in model one by one
            assertEquals(it.id, 20)
            assertEquals(it.title, "La Case De Papel")
            assertEquals(it.type, MovieModel.MovieType.SHOW)
            assertEquals(
                it.description,
                "8 pencuri melakukan penyanderaan dan mengunci diri mereka di Badan Percetakan Uang Spanyol sementara otak utama kejahatan mengakali polisi agar mewujudkan rencananya."
            )
            assertEquals(it.releaseDate, "2017")
            assertEquals(it.director, "Álex Pina")
            assertEquals(it.imbdRating, "83")
            assertEquals(it.img_link, "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/4LjPjtfaxEn2W61ORPeytr5Qq7j.jpg")
        }
    }


    @Test
    fun checkArtist() {
        val movieArtis = showViewModel.getShow()[0].artist
        assertNotNull(movieArtis)

        movieArtis.let {
            assertEquals(3, it.size)
            movieArtis[0].let { artist ->
                assertEquals(artist.name,"Úrsula Corberó")
                assertEquals(artist.play_as,"Tokyo (Silene Oliveira)")
            }
        }
    }


}