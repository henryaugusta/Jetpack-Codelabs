package com.feylabs.firrieflix.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.feylabs.firrieflix.R
import com.feylabs.firrieflix.data.FirrieflixRepository
import com.feylabs.firrieflix.data.source.local.LocalDataSource
import com.feylabs.firrieflix.data.source.local.room.FavoriteDatabase
import com.feylabs.firrieflix.data.source.remote.RemoteDataSource
import com.feylabs.firrieflix.databinding.ActivityDashboardBinding
import com.feylabs.firrieflix.ui.movie.MovieViewModel
import com.feylabs.firrieflix.viewmodel.ViewModelFactory

import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class DashboardActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
//    private val viewModelFactory: ViewModelFactory by instance()
    private lateinit var movieViewModel: MovieViewModel
    val vbind by lazy { ActivityDashboardBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vbind.root)
        setupViewModelWithKodein()
        val navController = findNavController(R.id.nav_host_movie)
        vbind.apply {
            movieBottomMenu.apply {
                setupWithNavController(navController)
                itemIconTintList = null
            }
        }

    }

    private fun setupViewModelWithKodein() {
        val factory = ViewModelFactory(
            FirrieflixRepository(
                RemoteDataSource(),
                LocalDataSource(FavoriteDatabase(this).favDao())
            )
        )
        movieViewModel = ViewModelProvider(this, factory).get(MovieViewModel::class.java)
    }


}