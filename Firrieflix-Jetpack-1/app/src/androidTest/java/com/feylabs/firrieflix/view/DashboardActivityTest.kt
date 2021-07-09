package com.feylabs.firrieflix.view

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.feylabs.firrieflix.R
import com.feylabs.firrieflix.util.seeder.MovieSeeder
import com.feylabs.firrieflix.util.seeder.ShowSeeder
import org.hamcrest.CoreMatchers.containsString
import org.junit.Rule
import org.junit.Test

class DashboardActivityTest {

    private val dummyFilm = MovieSeeder.movieSeeder()
    private val dummyShow = ShowSeeder.showSeeder()

    @get:Rule
    var activityRule = ActivityScenarioRule(DashboardActivity::class.java)

    @Test
    fun loadFilm() {
        onView(withId(R.id.rv_film)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_film)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                dummyFilm.size
            )
        )
    }

    @Test
    fun clickOnAllFilm() {
        for (i in 0 until dummyFilm.size) {
            onView(withId(R.id.rv_film)).perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                    i
                )
            )
            onView(withId(R.id.rv_film)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(i, click())
            )

            onView(withId(R.id.img_cover)).check(matches(isDisplayed()))
            onView(withId(R.id.label_movie_title)).check(matches(isDisplayed()))

            onView(withId(R.id.label_movie_title)).check(matches(withText(containsString(dummyFilm[i].title))))
            onView(withId(R.id.label_movie_title)).check(matches(withText(containsString(dummyFilm[i].title))))

            onView(withId(R.id.label_movie_date)).check(matches(isDisplayed()))
            onView(withId(R.id.label_movie_date)).check(matches(withText(containsString(dummyFilm[i].releaseDate))))

            onView(withId(R.id.label_movie_director)).check(matches(isDisplayed()))
            onView(withId(R.id.label_movie_director)).check(matches(withText(containsString(dummyFilm[i].director))))

            onView(withId(R.id.label_storyboard)).check(matches(isDisplayed()))
            onView(withId(R.id.label_storyboard)).check(matches(withText(containsString(dummyFilm[i].description))))


            onView(withId(R.id.label_back)).perform(click())

        }
    }

    @Test
    fun navigateBetween2Fragment4Times() {
        for (i in 0 until 10) {
            onView(withId(R.id.navigation_show)).perform(click())
            onView(withId(R.id.navigation_home)).perform(click())
        }
    }

    @Test
    fun clickOnAllShow(){
        //Move to show fragment first
        onView(withId(R.id.navigation_show)).perform(click())

        for (i in 0 until dummyShow.size) {
            onView(withId(R.id.rv_film)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(i))
            onView(withId(R.id.rv_film)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(i, click()))

            onView(withId(R.id.img_cover)).check(matches(isDisplayed()))
            onView(withId(R.id.label_movie_title)).check(matches(isDisplayed()))

            onView(withId(R.id.label_movie_title)).check(matches(withText(containsString(dummyShow[i].title))))
            onView(withId(R.id.label_movie_title)).check(matches(withText(containsString(dummyShow[i].title))))

            onView(withId(R.id.label_movie_date)).check(matches(isDisplayed()))
            onView(withId(R.id.label_movie_date)).check(matches(withText(containsString(dummyShow[i].releaseDate))))

            onView(withId(R.id.label_movie_director)).check(matches(isDisplayed()))
            onView(withId(R.id.label_movie_director)).check(
                matches(
                    withText(
                        containsString(
                            dummyShow[i].director
                        )
                    )
                )
            )

            onView(withId(R.id.label_storyboard)).check(matches(isDisplayed()))
            onView(withId(R.id.label_storyboard)).check(matches(withText(containsString(dummyShow[i].description))))


            onView(withId(R.id.label_back)).perform(click())

        }
    }


}