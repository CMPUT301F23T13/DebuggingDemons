package com.example.debuggingdemonsapp;

import androidx.camera.core.processing.SurfaceProcessorNode;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import androidx.test.filters.LargeTest;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import android.view.View;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SortByItemFragmentUITest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testSortingWithoutChoosing() throws InterruptedException{

        // Navigate to the inventory section of the app.
        // This assumes there is a navigation item identified by R.id.navigation_inventory
        onView(withId(R.id.navigation_inventory)).perform(click());

        // Pause execution to wait for potential loading processes.
        // Generally, it's better to use Espresso's Idling resources instead of Thread.sleep for synchronization.
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Open the sorting dialog.
        // Replace R.id.sortby_button with the actual ID of the button that triggers the sorting dialog.
        onView(ViewMatchers.withId(R.id.sortby_button))
                .perform(click());

        // Pause briefly to ensure the dialog has time to open.
        // As before, using Thread.sleep is not recommended for production tests.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Click the confirm button on the AlertDialog.
        // The confirm button typically has the ID android.R.id.button1.
        Espresso.onView(withId(android.R.id.button1))
                .perform(click());

        // Verify that a specific text ("Please select a sorting criterion") is displayed.
        // This checks that the appropriate error message is shown when no sorting criterion is selected.
        onView(withText("Please select a sorting criterion"))
                .check(ViewAssertions.matches(isDisplayed()));

    }



    @Test
    public void testSortingWithoutSelectingSortingOrder() throws InterruptedException{

        // Navigate to the inventory section of the app.
        // This assumes there is a navigation item identified by R.id.navigation_inventory
        onView(withId(R.id.navigation_inventory)).perform(click());

        // Pause execution to wait for potential loading processes.
        // It's better to use Espresso's Idling resources instead of Thread.sleep for synchronization in production tests.
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Open the sorting dialog.
        // Replace R.id.sortby_button with the actual ID of the button that triggers the sorting dialog.
        onView(withId(R.id.sortby_button)).perform(click());

        // Pause briefly to ensure the dialog has time to open.
        // Using Thread.sleep is not recommended for production tests as it can lead to flaky tests.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Select a sorting criterion, in this case, by description.
        // This assumes the sorting criterion button is identified by R.id.sort_by_description.
        onView(withId(R.id.sort_by_description)).perform(click());

        // Pause briefly to simulate user interaction time.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Click the confirm button on the AlertDialog.
        // The confirm button typically has the ID android.R.id.button1.
        Espresso.onView(withId(android.R.id.button1))
                .perform(click());

        // Verify that the specific text "Please select a sorting order" is displayed.
        // This checks that the appropriate error message is shown when no sorting order (ascending or descending) is selected.
        onView(withText("Please select a sorting order"))
                .check(ViewAssertions.matches(isDisplayed()));
    }



    @Test
    public void testSortingChangesItemOrderByDate() throws InterruptedException {
        // Navigate to inventory
        onView(withId(R.id.navigation_inventory)).perform(click());

        // Click on the add button
        onView(withId(R.id.add_button)).perform(click());

        // Enter a purchase date
        onView(withId(R.id.editTextDateOfPurchase)).perform(typeText("2020-10-20"), closeSoftKeyboard());

        // Enter a description
        onView(withId(R.id.editTextDescription)).perform(typeText("test1"), closeSoftKeyboard());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Click the OK button to add the item
        onView(withId(R.id.button_ok)).perform(click());

        // Click on the add button again
        onView(withId(R.id.add_button)).perform(click());

        // Enter a different purchase date
        onView(withId(R.id.editTextDateOfPurchase)).perform(typeText("2021-10-20"), closeSoftKeyboard());

        // Enter a different description
        onView(withId(R.id.editTextDescription)).perform(typeText("test2"), closeSoftKeyboard());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Click the OK button to add the second item
        onView(withId(R.id.button_ok)).perform(click());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Open the sorting dialog
        // Replace R.id.sort_button with the ID of the button that triggers the sorting dialog
        Espresso.onView(withId(R.id.sortby_button))
                .perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Select a sorting criterion, e.g., "Sort by Date"
        Espresso.onView(withId(R.id.sort_by_date))
                .perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Select a sorting order, e.g., "Ascending"
        Espresso.onView(withId(R.id.sort_ascending))
                .perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Click the confirmation button
        // AlertDialog's confirmation button usually has android.R.id.button1 as its ID
        Espresso.onView(withId(android.R.id.button1))
                .perform(click());

        // Wait for the sorting to complete (this may require additional logic to implement waiting)
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Verify the specific content of the first item in the RecyclerView
        // Replace R.id.my_recycler_view with the ID of your RecyclerView
        // Replace "Expected First Item Text" with the expected text of the first item
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText("test1"))))
                .check(matches(hasDescendant(withText("test1"))));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Click on the checkbox of the first item
        onView(withId(R.id.recycler_view))
                .perform(actionOnItem(hasDescendant(withText("test1")), clickOnViewChild(R.id.item_checkbox)));

        // Click on the checkbox of the second item
        onView(withId(R.id.recycler_view))
                .perform(actionOnItem(hasDescendant(withText("test2")), clickOnViewChild(R.id.item_checkbox)));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Click the delete button
        onView(withId(R.id.delete_button)).perform(click());
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
