package com.feylabs.firrieflix

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.feylabs.firrieflix.ui.DashboardActivity
import com.feylabs.firrieflix.util.EspressoIdlingResource
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DashboardActivityTest {


    @Before
    fun setup() {
        ActivityScenario.launch(DashboardActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
    }


    @Test
    fun loadFilm() {
        onView(withId(R.id.rv_film)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_film)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
               10
            )
        )
    }

    @Test
    fun clickOnAllFilm() {
        for (i in 0..10) {
            onView(withId(R.id.rv_film)).perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                    i
                )
            )
            onView(withId(R.id.rv_film)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(i, click())
            )

            onView(withId(R.id.img_cover)).check(matches(isDisplayed()))
            onView(withId(R.id.img_backdrop)).check(matches(isDisplayed()))
            onView(withId(R.id.label_movie_title)).check(matches(isDisplayed()))
            onView(withId(R.id.label_movie_date)).check(matches(isDisplayed()))
            onView(withId(R.id.label_movie_genre)).check(matches(isDisplayed()))
            onView(withId(R.id.label_movie_rate)).check(matches(isDisplayed()))
            onView(withId(R.id.label_storyboard)).check(matches(isDisplayed()))
            onView(isRoot()).perform(ViewActions.pressBack())

        }
    }


    @Test
    fun clickOnAllShow(){
        //Move to show fragment first
        onView(withId(R.id.navigation_show)).perform(click())
        for (i in 0..10) {
            onView(withId(R.id.rv_film)).perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                    i
                )
            )
            onView(withId(R.id.rv_film)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(i, click())
            )
            onView(withId(R.id.img_cover)).check(matches(isDisplayed()))
            onView(withId(R.id.img_backdrop)).check(matches(isDisplayed()))
            onView(withId(R.id.label_movie_title)).check(matches(isDisplayed()))
            onView(withId(R.id.label_movie_date)).check(matches(isDisplayed()))
            onView(withId(R.id.label_movie_rate)).check(matches(isDisplayed()))
            onView(withId(R.id.label_storyboard)).check(matches(isDisplayed()))
            onView(isRoot()).perform(ViewActions.pressBack())
        }
    }



    @Test
    fun navigateBetween2Fragment4Times() {
        for (i in 0 until 4) {
            onView(withId(R.id.navigation_show)).perform(click())
            onView(withId(R.id.navigation_home)).perform(click())
        }
    }



}
