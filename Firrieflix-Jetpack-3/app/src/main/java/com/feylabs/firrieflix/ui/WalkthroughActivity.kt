package com.feylabs.firrieflix.ui

import android.content.Intent
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.feylabs.firrieflix.R
import com.feylabs.firrieflix.ui.onboarding.WalkthroughAdapter
import com.feylabs.firrieflix.databinding.ActivityWalkthroughBinding
import com.feylabs.firrieflix.util.BaseActivity
import com.feylabs.firrieflix.util.preference.MyPreference


class WalkthroughActivity : BaseActivity() {

    val vbind by lazy { ActivityWalkthroughBinding.inflate(layoutInflater) }

    companion object {
        const val IS_LOGIN = "IS_LOGIN"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vbind.root)

        checkIsFirstLogin()
        setUpViewPager()
        setUpLayoutBinding()
    }

    private fun checkIsFirstLogin() {
        val isLogin = MyPreference(this).getPrefBool(IS_LOGIN)
        if (isLogin == true) {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        } else {
            MyPreference(this).save(IS_LOGIN, true)
        }
    }


    private fun setUpLayoutBinding() {
        vbind.btnSkip.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }
    }

    private fun setUpViewPager() {
        val tabAdapter = WalkthroughAdapter(supportFragmentManager, lifecycle)
        vbind.viewPager.adapter = tabAdapter
        vbind.indicator.setViewPager(vbind.viewPager)
        vbind.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setUpLayoutBinding()

                val arrayTitle = arrayListOf(
                    getString(R.string.bestfie), getString(R.string.staycation), getString(
                        R.string.accessfrom
                    )
                )
                val arrayDesc = arrayListOf(
                    getString(R.string.jrg1),
                    getString(R.string.jrg2),
                    getString(R.string.jrg3)
                )
                vbind.apply {
                    if (position <= 2) {
                        labelTitleJargon.text = arrayTitle[position]
                        labelDescJargon.text = arrayDesc[position]
                        labelDescJargon.textSize = 14.0f
                        btnSkip.text = getString(R.string.skip)
                        labelDescJargon.makeViewVisible()
                        labelTitleJargon.makeViewVisible()
                    } else {
                        btnSkip.text = getString(R.string.start_app)
                        labelDescJargon.makeViewGone()
                        labelTitleJargon.makeViewGone()
                    }
                }


            }
        })
    }


}

