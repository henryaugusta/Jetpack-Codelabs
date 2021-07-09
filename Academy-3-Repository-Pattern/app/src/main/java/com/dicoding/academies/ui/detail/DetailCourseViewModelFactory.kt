package com.dicoding.academies.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.academies.data.AcademyRepository

class DetailCourseViewModelFactory(val repository: AcademyRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailCourseViewModel(repository) as T
    }
}