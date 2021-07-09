package com.feylabs.firrieflix.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.feylabs.firrieflix.R
import com.feylabs.firrieflix.util.preference.MyPreference
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WalkthroughActivityTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(WalkthroughActivity::class.java)

    @Before
    fun clearPreference(){
        MyPreference(getInstrumentation().targetContext).clearPreferences()
    }

    @Test
    fun isViewPagerDisplayed(){
        onView(withId(R.id.view_pager)).check(matches(isDisplayed()))
    }

    @Test
    fun swipeViewPagerThenSkip(){
        onView(withId(R.id.view_pager)).perform(swipeLeft())
        onView(withId(R.id.label_title_jargon)).check(matches(isDisplayed()))
        onView(withId(R.id.label_desc_jargon)).check(matches(isDisplayed()))
        onView(withId(R.id.view_pager)).perform(swipeLeft())
        onView(withId(R.id.label_title_jargon)).check(matches(isDisplayed()))
        onView(withId(R.id.label_desc_jargon)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_skip)).perform(click())
    }

    @Test
    fun skipOnboardingPagerWithoutSwipe(){
        onView(withId(R.id.btn_skip)).perform(click())
    }


}