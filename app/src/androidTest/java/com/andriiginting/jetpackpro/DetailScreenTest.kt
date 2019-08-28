package com.andriiginting.jetpackpro

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.andriiginting.jetpackpro.presentation.TheaterDetailScreen.DetailScreenActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class DetailScreenTest {

    @get:Rule
    val intentsTestRule = IntentsTestRule(DetailScreenActivity::class.java)

    @Test
    fun testBackdropPosterIsAttach() {
        Espresso.onView(ViewMatchers.withId(R.id.ivPosterBackdrop))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testBackButtonIsAttach() {
        Espresso.onView(ViewMatchers.withId(R.id.ivBackNavigation))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testSimilarContent() {
        Espresso.onView(ViewMatchers.withId(R.id.rvSimilarMovie))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testTabFavoriteIcon() {
        Espresso.onView(ViewMatchers.withId(R.id.ivTheaterFavorite))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}