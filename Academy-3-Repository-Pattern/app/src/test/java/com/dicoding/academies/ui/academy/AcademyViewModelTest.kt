package com.dicoding.academies.ui.academy

import com.dicoding.academies.data.AcademyRepository
import com.dicoding.academies.utils.DataDummy
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AcademyViewModelTest {

    private lateinit var viewModel: AcademyViewModel

    @Mock
    private lateinit var academyRepository: AcademyRepository

    @Before
    fun setUp() {
        viewModel = AcademyViewModel(academyRepository)
    }



    @Test
    fun getCourses() {
        `when`(academyRepository.getAllCourses()).thenReturn(DataDummy.generateDummyCourses())
        val courseEntities = viewModel.getCourses()
        assertNotNull(courseEntities)
        assertEquals(5, courseEntities.size)
    }
}