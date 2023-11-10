package com.example.debuggingdemonsapp;

import android.view.View;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.not;

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

    @Test
    public void testClickDeleteButton_WithoutAnyCheckbox(){
        // Navigate to inventory
        onView(withId(R.id.navigation_inventory)).perform(click());

        onView(withId(R.id.delete_button)).perform(click());

        onView(withText("No items selected to be deleted."))
                .check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testCheckbox_CheckingAndDelete() throws InterruptedException {
        // Navigate to inventory
        onView(withId(R.id.navigation_inventory)).perform(click());
        // Assuming you have a checkbox with the ID checkbox_id in your layout
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // First, click on the checkbox to change its state
        onView(withId(R.id.recycler_view))
                .perform(actionOnItemAtPosition(0, clickOnViewChild(R.id.item_checkbox)));

        onView(withId(R.id.delete_button)).perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        onView(withText("Item deleted successfully"))
                .check(ViewAssertions.matches(isDisplayed()));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Assuming the text "item" is the first one on the Recycle view as input in the item description

        onView(withId(R.id.recycler_view))
                .check(ViewAssertions.matches(not(hasDescendant(withText("item")))));

    }

    // Helper method to perform click on a child view with specified id within the RecyclerView item
    public static ViewAction clickOnViewChild(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified ID.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                v.performClick();
            }
        };
    }
}