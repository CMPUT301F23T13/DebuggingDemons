package com.example.debuggingdemonsapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class InventoryDeleteTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void testIsInventoryFragmentDisplayed() {

        // Navigate to inventory
        onView(withId(R.id.navigation_inventory)).perform(click());

        onView(withId(R.id.filter_button)).check(ViewAssertions.matches(isDisplayed()));

        onView(withId(R.id.sortby_button)).check(ViewAssertions.matches(isDisplayed()));

        onView(withId(R.id.recycler_view)).check(ViewAssertions.matches(isDisplayed()));

        onView(withId(R.id.tag_button)).check(ViewAssertions.matches(isDisplayed()));

        onView(withId(R.id.delete_button)).check(ViewAssertions.matches(isDisplayed()));

        onView(withId(R.id.add_button)).check(ViewAssertions.matches(isDisplayed()));

    }

}