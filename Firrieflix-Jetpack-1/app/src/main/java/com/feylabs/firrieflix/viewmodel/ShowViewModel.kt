package com.feylabs.firrieflix.viewmodel

import androidx.lifecycle.ViewModel
import com.feylabs.firrieflix.model.MovieModel
import com.feylabs.firrieflix.util.seeder.ShowSeeder

class ShowViewModel : ViewModel() {

    fun getShow() : MutableList<MovieModel> = ShowSeeder.showSeeder()

}