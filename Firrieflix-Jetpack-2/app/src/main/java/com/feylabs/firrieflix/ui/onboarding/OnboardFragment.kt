package com.feylabs.firrieflix.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.feylabs.firrieflix.R
import com.feylabs.firrieflix.databinding.Onboard1Binding
import com.feylabs.firrieflix.databinding.OnboardFinalBinding

class OnboardFragment(val order: Int) : Fragment() {

    private var _vbind: Onboard1Binding? = null
    private val vbind get() = _vbind as Onboard1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view = inflater.inflate(R.layout.onboard1, container, false)

        if (order == 4) {
            view = inflater.inflate(R.layout.onboard_final, container, false)
            return OnboardFinalBinding.bind(view).root
        }

        _vbind = Onboard1Binding.bind(view)
        return vbind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (order) {
            1 -> {
                vbind.imageCenter.setImageResource(R.drawable.ic_onboard_1)
            }
            2 -> {
                vbind.imageCenter.setImageResource(R.drawable.ic_undraw_horror_movie)
            }
            3 -> {
                vbind.imageCenter.setImageResource(R.drawable.ic_undraw_chilling)
            }
        }
    }
}