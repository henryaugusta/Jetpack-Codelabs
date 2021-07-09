package com.feylabs.firrieflix.util

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.feylabs.firrieflix.data.FirrieflixRepository
import com.feylabs.firrieflix.viewmodel.ViewModelFactory
import com.tapadoo.alerter.Alerter
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

open class BaseActivity : AppCompatActivity(){


    fun String.showLongToast() {
        Toast.makeText(applicationContext, this, Toast.LENGTH_LONG).show()
    }

    fun String.showShortToast() {
        Toast.makeText(applicationContext, this, Toast.LENGTH_LONG).show()
    }

    fun showSweetAlert(title: String, desc: String, color: Int) {
        Alerter.create(this)
            .setTitle(title)
            .setText(desc)
            .setBackgroundColorRes(color) // or setBackgroundColorInt(Color.CYAN)
            .show()
    }

    fun View.makeViewVisible() {
        this.visibility = View.VISIBLE
    }

    fun View.makeViewGone() {
        this.visibility = View.GONE
    }


}