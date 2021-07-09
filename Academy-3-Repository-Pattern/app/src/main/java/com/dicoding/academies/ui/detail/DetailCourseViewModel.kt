package com.dicoding.academies.ui.detail

import androidx.lifecycle.ViewModel
import com.dicoding.academies.data.AcademyRepository
import com.dicoding.academies.data.source.local.entity.CourseEntity
import com.dicoding.academies.data.source.local.entity.ModuleEntity
import com.dicoding.academies.utils.DataDummy

class DetailCourseViewModel(private val academyRepository: AcademyRepository) : ViewModel() {
    private lateinit var courseId: String

    fun getCourse(): CourseEntity = academyRepository.getCourseWithModules(courseId)

    fun getModules(): List<ModuleEntity> = academyRepository.getAllModulesByCourse(courseId)

    fun setSelectedCourse(courseId: String) {
        this.courseId = courseId
    }
//
//    fun getCourse(): CourseEntity {
//        lateinit var course: CourseEntity
//        val coursesEntities = DataDummy.generateDummyCourses()
//        for (courseEntity in coursesEntities) {
//            if (courseEntity.courseId == courseId) {
//                course = courseEntity
//            }
//        }
//        return course
//    }
//
//    fun getModules(): List<ModuleEntity> = DataDummy.generateDummyModules(courseId)
}