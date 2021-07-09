package com.dicoding.academies.ui.bookmark

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class BookmarkViewModelTest{
    lateinit var viewModel : BookmarkViewModel

    val ehem = 12
    @Before
    fun setup(){
        viewModel = BookmarkViewModel()
    }

    @Test
    fun checkBookmarkViewModel(){
        val courseEntities = viewModel.getBookmarks()
        assertNotNull(courseEntities)
        assertEquals(5, courseEntities.size)
        assertSame(12,ehem)
    }



}