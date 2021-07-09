package com.dicoding.academies.ui.bookmark

import androidx.lifecycle.ViewModel
import com.dicoding.academies.data.AcademyRepository
import com.dicoding.academies.data.source.local.entity.CourseEntity
import com.dicoding.academies.utils.DataDummy

class BookmarkViewModel(private val academyRepository: AcademyRepository) : ViewModel() {
//    fun getBookmarks(): List<CourseEntity> = DataDummy.generateDummyCourses()
    fun getBookmarks(): List<CourseEntity> = academyRepository.getAllCourses()
}