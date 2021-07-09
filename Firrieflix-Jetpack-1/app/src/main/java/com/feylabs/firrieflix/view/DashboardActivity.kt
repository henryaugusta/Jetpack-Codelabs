package com.feylabs.firrieflix.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.feylabs.firrieflix.R
import com.feylabs.firrieflix.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {

    val vbind by lazy { ActivityDashboardBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vbind.root)

        val navController = findNavController(R.id.nav_host_movie)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_home,R.id.navigation_show,R.id.navigation_profile))

        vbind.movieBottomMenu.setupWithNavController(navController)

        vbind.movieBottomMenu.itemIconTintList = null;

    }
}