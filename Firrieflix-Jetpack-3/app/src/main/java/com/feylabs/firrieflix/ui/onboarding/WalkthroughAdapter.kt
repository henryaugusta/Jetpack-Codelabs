package com.feylabs.firrieflix.ui.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.feylabs.firrieflix.ui.onboarding.OnboardFragment


class WalkthroughAdapter(fm: FragmentManager, lf: Lifecycle) : FragmentStateAdapter(fm, lf) {

    private val fragmentList = arrayListOf<Fragment>(
        OnboardFragment(1),
        OnboardFragment(2),
        OnboardFragment(3),
        OnboardFragment(4),
    )

    override fun getItemCount() = fragmentList.size
    override fun createFragment(position: Int) = fragmentList[position]


}