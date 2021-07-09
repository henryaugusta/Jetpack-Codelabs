package com.feylabs.firrieflix.viewmodel

import junit.framework.TestCase

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


//Test for getting all show movie from seeder
class ShowViewModelTest {

    private lateinit var viewModel : ShowViewModel

    @Before
    fun setUp(){
        viewModel = ShowViewModel()
    }

    @Test
    fun getShows(){
        val showEntities = viewModel.getShow()
        assertNotNull(showEntities)
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