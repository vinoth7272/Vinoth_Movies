package com.example.movierating

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.movierating.view.MovieDetailActivity
import com.example.movierating.view.MovieListActivity
import com.example.movierating.view.MoviesAdapter
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieDetailActivityInstrumentedTest {
    @Rule
    @JvmField
    var intentsRule: ActivityTestRule<MovieListActivity> =
        ActivityTestRule(MovieListActivity::class.java, true, false)

    @Before
    fun setUp() {
        Intents.init()
        val firstActivity: ActivityTestRule<MovieListActivity> =
            ActivityTestRule(MovieListActivity::class.java)
        firstActivity.launchActivity(Intent())
        onView(withId(R.id.txtSearchView)).perform(clearText())
        onView(withId(R.id.txtSearchView)).perform(typeText("Star"))
        onView(withId(R.id.btnGo)).perform(ViewActions.click())
        onView(withId(R.id.recyclerView))
            .check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<MoviesAdapter.MovieViewHolder>(
                0,
                ViewActions.click()
            )
        )
        Intents.intended(IntentMatchers.hasComponent(MovieDetailActivity::class.java.name))
        Intents.release()
    }

    @Test
    fun validateBackButton() {
        Intents.init()
        val movieDetailActivity: ActivityTestRule<MovieDetailActivity> =
            ActivityTestRule(MovieDetailActivity::class.java, true, false)
        onView(withId(R.id.imgBack)).perform(ViewActions.click())
        movieDetailActivity.finishActivity()
        Intents.release()
    }

    @Test
    fun validateUI() {
        onView(withId(R.id.txtTitle)).check(matches(withText("Star Wars")))
        onView(withId(R.id.txtLanguage)).check(matches(withText("English")))
        onView(withId(R.id.txtRating)).check(matches(withText("8.2")))
        onView(withId(R.id.txtYear)).check(matches(withText("May 77")))
        onView(withId(R.id.txtVoteCount)).check(matches(withText("15904")))
        onView(withId(R.id.txtRating)).check(matches(withText("8.2")))
        onView(withId(R.id.txtOverview)).check(
            matches(
                withText(
                    "Princess Leia is captured and held hostage by the evil Imperial " +
                        "forces in their effort to take over the galactic Empire. Venturesome Luke Skywalker and dashing captain Han Solo team " +
                        "together with the loveable robot duo R2-D2 and C-3PO to rescue the beautiful princess and restore peace and justice in " +
                        "the Empire."
                )
            )
        )
    }
}
