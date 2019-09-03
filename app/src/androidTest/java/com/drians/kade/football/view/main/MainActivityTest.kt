package com.drians.kade.football.view.main

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.drians.kade.football.R.id.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    /**
    https://blog.dicoding.com/menerapkan-espresso-idling-resource-pada-instrumentation-testing-tutorial-kelas-kade/

    Object EspressoIdlingResource dan fungsi-fungsinya inilah yang akan digunakan dalam menandai proses
    asynchronous yang berjalan pada aplikasi. Pada kelas tersebut gunakan CountingIdlingResource
    yang berfungsi sebagai penghitung dari tugas-tugas yang dikerjakan. Jika counter-nya 0, tandanya
    aplikasi sedang tidak menjalankan tugas apa pun.

    object EspressoIdlingResource {
        private const val RESOURCE = "GLOBAL"
        private val countingIdlingResource = CountingIdlingResource(RESOURCE)

        val idlingresource: IdlingResource
            get() = countingIdlingResource

        fun increment() {
            countingIdlingResource.increment()
        }

        fun decrement() {
            countingIdlingResource.decrement()
        }
    }

    // Memberitahukan Espresso bahwa aplikasi sedang sibuk
    EspressoIdlingResource.increment()
    presenter.getEventSearch(query)

    override fun showEventList(data: List<Event>) {
        if (!EspressoIdlingResource.idlingresource.isIdleNow) {
            // Memberitahukan bahwa tugas sudah selesai dijalankan
            EspressoIdlingResource.decrement()
        }
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }

    Jangan lupa ya, hapus idling resource tersebut sesaat setelah pengujian selesai.
    Gunanya untuk menghindari memory leak.

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingresource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingresource)
    }
    **/

    @Test
    fun testAppBehaviour() {
        onView(withId(action_search)).check(matches(isDisplayed()))
        onView(withId(action_search)).perform(click())
        onView(withHint("Search Matches")).check(matches(isDisplayed()))
            .perform(clearText(), typeText("juventus"), pressImeActionButton())

        onView(withId(list_search_match)).check (matches(isDisplayed()))
        onView(withText("Juventus")).check(matches(isDisplayed()))
        onView(withText("Juventus")).perform(click())

        onView(withId(action_favorite)).check(matches(isDisplayed()))
        onView(withId(action_favorite)).perform(click())
        onView(withText("Added to favorite")).check(matches(isDisplayed()))

        pressBack()
        pressBack()
        pressBack()
        pressBack()

        onView(withId(bottom_navigation)).check(matches(isDisplayed()))
        onView(withId(navigation_favorite)).perform(click())

        onView(withText("Juventus")).check(matches(isDisplayed()))
        onView(withText("Juventus")).perform(click())

        onView(withId(action_favorite)).check(matches(isDisplayed()))
        onView(withId(action_favorite)).perform(click())
        onView(withText("Removed to favorite")).check(matches(isDisplayed()))

        pressBack()

        onView(withId(bottom_navigation)).check(matches(isDisplayed()))
        onView(withId(navigation_league)).perform(click())
    }
}