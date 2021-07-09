package com.dicoding.academies.ui.academy

import androidx.lifecycle.ViewModel
import com.dicoding.academies.data.AcademyRepository
import com.dicoding.academies.data.source.local.entity.CourseEntity
import com.dicoding.academies.utils.DataDummy

class AcademyViewModel(val academyRepository: AcademyRepository)  : ViewModel() {

//    fun getCourses() : List<CourseEntity> = DataDummy.generateDummyCourses()
    fun getCourses() : List<CourseEntity> = academyRepository.getAllCourses()


}