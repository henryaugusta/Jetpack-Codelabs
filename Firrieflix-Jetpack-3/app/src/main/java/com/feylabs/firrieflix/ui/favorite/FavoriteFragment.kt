package com.feylabs.firrieflix.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.feylabs.firrieflix.R
import com.feylabs.firrieflix.databinding.FragmentFavoriteBinding
import com.feylabs.firrieflix.util.BaseFragment

class FavoriteFragment : BaseFragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val vbind get() = _binding as FragmentFavoriteBinding

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {
        val pagerAdapter = FavoriteViewPagerAdapter(requireContext(),childFragmentManager)
        vbind.viewPager.adapter = pagerAdapter
        vbind.tabLayout.setupWithViewPager(vbind.viewPager)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        _binding = FragmentFavoriteBinding.bind(view)
        return vbind.root
    }



}