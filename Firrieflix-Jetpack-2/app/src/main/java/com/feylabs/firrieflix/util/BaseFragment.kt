package com.feylabs.firrieflix.util

import android.R
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.feylabs.firrieflix.viewmodel.ViewModelFactory


import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


open class BaseFragment : Fragment(), KodeinAware {

    override val kodein by kodein()

    lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressBar = ProgressBar(requireContext(), null, R.attr.progressBarStyleSmall)
    }

    val firrieflixVMFactory: ViewModelFactory by instance()

    fun String.showLongToast() {
        Toast.makeText(requireContext(), this, Toast.LENGTH_LONG).show()
    }

    fun String.showShortToast() {
        Toast.makeText(requireContext(), this, Toast.LENGTH_LONG).show()
    }

    fun View.makeViewVisible() {
        this.visibility = View.VISIBLE
    }

    fun View.makeViewGone() {
        this.visibility = View.GONE
    }


}