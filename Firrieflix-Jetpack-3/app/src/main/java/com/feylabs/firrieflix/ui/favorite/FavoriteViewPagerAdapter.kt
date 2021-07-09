package com.feylabs.firrieflix.ui.favorite

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.feylabs.firrieflix.R

class FavoriteViewPagerAdapter(val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.pager_movie, R.string.pager_show)
    }


    override fun getCount() = 2

    override fun getPageTitle(position: Int): CharSequence = context.resources.getString(
        TAB_TITLES[position]
    )


    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FavoritePageFragment(1)
            1 -> FavoritePageFragment(2)
            else -> Fragment()
        }
    }
}