package com.feylabs.firrieflix.viewmodel

import junit.framework.TestCase

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MovieViewModelTest{
    private lateinit var viewModel : MovieViewModel

    @Before
    fun setUp(){
        viewModel = MovieViewModel()
    }

    @Test
    fun getShows(){
        val showEntities = viewModel.getMovie()
        assertNotNull(showEntities)

        //check if all entities count 10
        assertEquals(10,showEntities.size)

        //Check All Item is Not Null
        showEntities.forEach {
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
}