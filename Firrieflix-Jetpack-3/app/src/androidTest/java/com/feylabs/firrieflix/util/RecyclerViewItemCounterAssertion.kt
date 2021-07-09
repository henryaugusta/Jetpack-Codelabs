package com.feylabs.firrieflix.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView

import androidx.test.espresso.NoMatchingViewException

import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import org.hamcrest.Matcher
import org.hamcrest.core.Is.`is`


class RecyclerViewItemCounterAssertion private constructor(matcher: Matcher<Int>) :
    ViewAssertion {
    private val matcher: Matcher<Int>
    override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }
        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter
        assertThat(adapter!!.itemCount, matcher)
    }

    companion object {
        fun withItemCount(expectedCount: Int): RecyclerViewItemCounterAssertion {
            return withItemCount(`is`(expectedCount))
        }

        fun withItemCount(matcher: Matcher<Int>): RecyclerViewItemCounterAssertion {
            return RecyclerViewItemCounterAssertion(matcher)
        }
    }

    init {
        this.matcher = matcher
    }
}