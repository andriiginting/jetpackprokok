package com.andriiginting.jetpackpro

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainScreenTest {
    @get:Rule
    var rules = ActivityTestRule(MainActivity::class.java)


    @Test
    fun testMainTabLayout() {
        Thread.sleep(3000)
        onView(withId(R.id.tabs))
            .perform(click())
            .check(matches(isDisplayed()))
    }

    @Test
    fun testSwipePagerMainLayoutSwipeLeft(){
        Thread.sleep(3000)
        onView(withId(R.id.mainPager)).check(matches(isDisplayed()))

        Thread.sleep(3000)
        onView(withId(R.id.mainPager))
            .perform(swipeLeft())
    }

    @Test
    fun testSwipePagerMainLayoutSwipeRight(){
        Thread.sleep(3000)
        onView(withId(R.id.mainPager)).check(matches(isDisplayed()))

        Thread.sleep(3000)
        onView(withId(R.id.mainPager))
            .perform(swipeRight())
    }

    @Test
    fun testRecyclerDataOnMainLayoutSwipeUp(){
        Thread.sleep(3000)
        onView(withId(R.id.rvMovieFragment)).check(matches(isDisplayed()))

        Thread.sleep(3000)
        onView(withId(R.id.rvMovieFragment))
            .perform(swipeUp())
    }

    @Test
    fun testRecyclerDataOnMainLayoutSwipeDown(){
        Thread.sleep(3000)
        onView(withId(R.id.rvMovieFragment)).check(matches(isDisplayed()))

        Thread.sleep(1000)
        onView(withId(R.id.rvMovieFragment))
            .perform(swipeUp())
            .perform(swipeDown())
    }

    @Test
    fun testRecyclerDataOnMainLayoutClick(){
        Thread.sleep(3000)
        onView(withId(R.id.rvMovieFragment)).check(matches(isDisplayed()))

        Thread.sleep(1000)
        onView(withId(R.id.rvMovieFragment))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
            )
    }
}