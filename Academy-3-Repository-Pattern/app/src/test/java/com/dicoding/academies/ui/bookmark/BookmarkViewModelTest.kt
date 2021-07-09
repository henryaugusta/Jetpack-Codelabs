package com.dicoding.academies.ui.bookmark

import com.dicoding.academies.data.AcademyRepository
import com.dicoding.academies.data.source.local.entity.CourseEntity
import com.dicoding.academies.utils.DataDummy
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class BookmarkViewModelTest {
    lateinit var viewModel: BookmarkViewModel


    @Mock
    private lateinit var academyRepository: AcademyRepository

    @Before
    fun setup(){
        viewModel = BookmarkViewModel(academyRepository)
    }

    @Test
    fun getBookmark(){
        Mockito.`when`(academyRepository.getBookmarkedCourses()).thenReturn(DataDummy.generateDummyCourses() as ArrayList<CourseEntity>)
        val courseEntities = viewModel.getBookmarks()
        Mockito.verify(academyRepository.getBookmarkedCourses())
        assertNotNull(courseEntities)
        assertEquals(5,courseEntities.size)
    }



//    val ehem = 12
//
//    @Before
//    fun setup() {
//        viewModel = BookmarkViewModel()
//    }
//
//    @Test
//    fun checkBookmarkViewModel() {
//        val courseEntities = viewModel.getBookmarks()
//        assertNotNull(courseEntities)
//        assertEquals(5, courseEntities.size)
//        assertSame(12, ehem)
//    }


}