package com.feylabs.firrieflix

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.ViewPagerActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.feylabs.firrieflix.ui.DashboardActivity
import com.feylabs.firrieflix.util.EspressoIdlingResource
import org.junit.*
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.core.AllOf

class FavoriteTest {

    companion object {
        @BeforeClass
        fun clearDatabase() {
            InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand("pm clear PACKAGE_NAME").close()
        }
    }

    @get:Rule
    val scenarioRule = ActivityScenarioRule(DashboardActivity::class.java)

    @Before
    fun setup() {
        ActivityScenario.launch(DashboardActivity::class.java)
        clearDatabase()
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun likeAllMovie() {
        onView(withId(R.id.navigation_home)).perform(click())
        for (i in 0..10) {
            onView(withId(R.id.rv_film)).perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                    i
                )
            )
            onView(withId(R.id.rv_film)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(i, click())
            )
            onView(withId(R.id.like)).perform(click())
            onView(isRoot()).perform(ViewActions.pressBack())
        }

        onView(withId(R.id.navigation_favorite)).perform(click())
        onView(withId(R.id.tab_layout))
            .check(matches(isDisplayed()))
        onView(withId(R.id.view_pager))
            .perform(ViewPagerActions.scrollRight(true))
        onView(allOf(isDisplayed(), withId(R.id.rv_fav)))

        onView(withId(R.id.view_pager))
            .perform(ViewPagerActions.scrollLeft(true))
    }

    @Test
    fun likeAllShow() {
        onView(withId(R.id.navigation_home)).perform(click())
        for (i in 0..10) {
            onView(withId(R.id.rv_film)).perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                    i
                )
            )
            onView(withId(R.id.rv_film)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(i, click())
            )
            onView(withId(R.id.like)).perform(ViewActions.click())
            onView(isRoot()).perform(ViewActions.pressBack())
        }
        onView(withId(R.id.navigation_favorite)).perform(click())
        onView(withId(R.id.tab_layout))
            .check(matches(isDisplayed()))
        onView(withId(R.id.view_pager))
            .perform(ViewPagerActions.scrollRight(true))
        onView(allOf(isDisplayed(), withId(R.id.rv_fav)))
        onView(withId(R.id.view_pager))
            .perform(ViewPagerActions.scrollLeft(true))
    }

    @Test
    fun openFavoriteTab() {
        onView(withId(R.id.navigation_favorite)).perform(click())
        onView(withId(R.id.tab_layout)).check(matches(isDisplayed()))
        onView(AllOf.allOf(isDisplayed(), withId(R.id.rv_fav)))
        onView(withId(R.id.view_pager)).perform(ViewPagerActions.scrollRight(true))
        onView(withId(R.id.view_pager)).perform(ViewPagerActions.scrollLeft(true))
    }




}
