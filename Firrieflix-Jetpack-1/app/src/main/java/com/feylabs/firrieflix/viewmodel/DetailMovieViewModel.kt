package com.feylabs.firrieflix.viewmodel

import androidx.lifecycle.ViewModel
import com.feylabs.firrieflix.model.MovieModel
import com.feylabs.firrieflix.util.seeder.MovieSeeder
import com.feylabs.firrieflix.util.seeder.ShowSeeder

class DetailMovieViewModel : ViewModel()  {

    lateinit var mySearchedMovie : MovieModel

    fun findMovieById(searchedID:Int) : MovieModel {
        val listMovie = MovieSeeder.movieSeeder()
        listMovie.addAll(ShowSeeder.showSeeder())

        for (index in 0 until listMovie.size){
            if (listMovie[index].id==searchedID){
                mySearchedMovie = listMovie[index]
            }
        }

        return mySearchedMovie
    }


}