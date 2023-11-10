package com.example.debuggingdemonsapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.espresso.contrib.RecyclerViewActions;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import org.junit.Rule;
import org.junit.Test;

/**
 * Espresso UI tests for the edit functionality in the MainActivity.
 */
public class EditItemTest {
    // Rule for launching the MainActivity in an ActivityScenario
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);
    /**
     * Test case to verify the edit button functionality.
     * It navigates to the inventory, selects the first item, clicks the edit button in the dialog,
     * updates the item details, and confirms the changes.
     */
    @Test
    public void testEditButton(){
        // Navigate to the inventory
        onView(withId(R.id.navigation_inventory)).perform(click());
        // Wait for 1 second
        try {
            Thread.sleep(1000); // 等待1秒钟
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Click on the first item in the RecyclerView
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // Wait for 1 second
        try {
            Thread.sleep(1000); // 等待1秒钟
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Click on the neutral button on the dialog
        onView(withId(android.R.id.button3)).perform((click()));
        // Wait for 1 second
        try {
            Thread.sleep(1000); // 等待1秒钟
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Check if the "EDIT" and "test" views are displayed
        // Id displayed, means it navigate to edit page successfully
        onView(withText("EDIT")).check(matches(isDisplayed()));
        onView(withText("test")).check(matches(isDisplayed()));
        // Replace text in various EditText fields
        onView(withId(R.id.dateOfPurchase)).perform(replaceText("2023-11-9"), closeSoftKeyboard());
        onView(withId(R.id.description)).perform(replaceText("item2"), closeSoftKeyboard());
        onView(withId(R.id.make)).perform(replaceText("paper"), closeSoftKeyboard());
        onView(withId(R.id.model)).perform(replaceText("train"), closeSoftKeyboard());
        onView(withId(R.id.serial_number)).perform(replaceText("123"), closeSoftKeyboard());
        onView(withId(R.id.estimated_value)).perform(replaceText("777"), closeSoftKeyboard());
        onView(withId(R.id.comment)).perform(replaceText("a train"), closeSoftKeyboard());

        // Click the confirm button
        onView(withId(R.id.confirm_button)).perform(click());


    }

    /**
     * Test case to verify the back button functionality.
     * It navigates to the inventory, selects the first item, clicks the back button,
     * and checks if the navigation to the inventory screen is successful.
     */

    @Test
    public void testBackButton() {
        // Navigate to the inventory
        onView(withId(R.id.navigation_inventory)).perform(click());

        // Wait for 1 second
        try {
            Thread.sleep(1000); // 等待1秒钟
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Click on the first item in the RecyclerView

        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Wait for 1 second
        try {
            Thread.sleep(1000); // 等待1秒钟
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Click on the neutral button on the dialog
        onView(withId(android.R.id.button3)).perform((click()));
        // Wait for 1 second
        try {
            Thread.sleep(1000); // 等待1秒钟
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Click the back button
        onView(withId(R.id.back_button)).perform(click());
        // Check if the navigation to the inventory screen is successful
        onView(withId(R.id.navigation_inventory)).check(matches(isDisplayed()));

    }

}
