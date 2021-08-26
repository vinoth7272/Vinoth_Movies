package com.example.movierating

import android.content.Intent
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.movierating.view.MovieDetailActivity
import com.example.movierating.view.MovieListActivity
import com.example.movierating.view.MoviesAdapter
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MovieListActivityInstrumentedTest {
    @Rule
    @JvmField
    var activityScenarioRule: ActivityScenarioRule<MovieListActivity> =
        ActivityScenarioRule(MovieListActivity::class.java)

    @Rule
    @JvmField
    var intentsRule: ActivityTestRule<MovieListActivity> =
        ActivityTestRule(MovieListActivity::class.java, true, false)

    @Before
    fun setUp(){
        Intents.init()
    }

    @Test
    fun validateEditTextSuccess() {
        onView(withId(R.id.btnGo)).perform(click())
        onView(isRoot()).perform(waitFor())
        onView(withId(R.id.recyclerView)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun validateRecyclerViewItem() {
        onView(withId(R.id.btnGo)).perform(click())
        onView(isRoot()).perform(waitFor())
        onView(withId(R.id.recyclerView)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun validateListItemClickTest() {
        onView(withId(R.id.btnGo)).perform(click())
        onView(isRoot()).perform(waitFor())
        onView(withId(R.id.recyclerView)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<MoviesAdapter.MovieViewHolder>(
                0,
                click()
            )
        )
        intentsRule.launchActivity(Intent())
        Intents.intended(IntentMatchers.hasComponent(MovieDetailActivity::class.java.name))
    }

    private fun waitFor(): ViewAction {
        return object : ViewAction {
            override fun getConstraints() = isRoot()
            override fun getDescription(): String = "wait for 5000 milliseconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(5000)
            }
        }
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        Intents.release()
    }
}
