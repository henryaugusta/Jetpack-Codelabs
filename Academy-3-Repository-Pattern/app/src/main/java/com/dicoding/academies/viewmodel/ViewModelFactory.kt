package com.dicoding.academies.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.academies.data.AcademyRepository
import com.dicoding.academies.di.Injection
import com.dicoding.academies.ui.academy.AcademyViewModel
import com.dicoding.academies.ui.bookmark.BookmarkViewModel
import com.dicoding.academies.ui.detail.DetailCourseViewModel
import com.dicoding.academies.ui.reader.CourseReaderViewModel

class ViewModelFactory(private val academyRepository: AcademyRepository) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: ViewModelFactory(Injection.provideRepository(context)).apply {
                        INSTANCE = this
                    }
                }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(AcademyViewModel::class.java) -> {
                return AcademyViewModel(academyRepository) as T
            }
            modelClass.isAssignableFrom(DetailCourseViewModel::class.java) -> {
                return DetailCourseViewModel(academyRepository) as T
            }
            modelClass.isAssignableFrom(BookmarkViewModel::class.java) -> {
                return BookmarkViewModel(academyRepository) as T
            }
            modelClass.isAssignableFrom(CourseReaderViewModel::class.java) -> {
                return CourseReaderViewModel(academyRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

    }



}