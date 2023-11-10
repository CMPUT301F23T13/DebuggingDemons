package com.example.debuggingdemonsapp;

import android.view.View;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import org.hamcrest.Matcher;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InventoryDeleteTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void test1_IsInventoryFragmentDisplayed() {

        // Navigate to inventory
        onView(withId(R.id.navigation_inventory)).perform(click());

        // Checking if all the component are show on the screen
        onView(withId(R.id.filter_button)).check(ViewAssertions.matches(isDisplayed()));

        onView(withId(R.id.sortby_button)).check(ViewAssertions.matches(isDisplayed()));

        onView(withId(R.id.recycler_view)).check(ViewAssertions.matches(isDisplayed()));

        onView(withId(R.id.tag_button)).check(ViewAssertions.matches(isDisplayed()));

        onView(withId(R.id.delete_button)).check(ViewAssertions.matches(isDisplayed()));

        onView(withId(R.id.add_button)).check(ViewAssertions.matches(isDisplayed()));

    }

    @Test
    public void test2_ClickDeleteButton_WithoutAnyCheckbox(){
        // Navigate to inventory
        onView(withId(R.id.navigation_inventory)).perform(click());

        // Clicking the delete button
        onView(withId(R.id.delete_button)).perform(click());

        // Checking whether the correct snackbar message show up
        onView(withText("No items selected to be deleted."))
                .check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void test3_Checkbox_CheckingAndDelete() throws InterruptedException {
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

        // Clicking the delete button
        onView(withId(R.id.delete_button)).perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Checking whether the correct snackbar message show up
        onView(withText("Item deleted successfully"))
                .check(ViewAssertions.matches(isDisplayed()));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Assuming the text "test" is the first one on the Recycle view as input in the item description
        onView(withId(R.id.recycler_view))
                .check(ViewAssertions.matches(not(hasDescendant(withText("test")))));

    }


    @Test
    public void test4_Checkbox_MultipleCheckingAndDelete() throws InterruptedException {
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

        onView(withId(R.id.recycler_view))
                .perform(actionOnItemAtPosition(1, clickOnViewChild(R.id.item_checkbox)));

        // Clicking the delete button
        onView(withId(R.id.delete_button)).perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Checking whether the correct snackbar message show up
        onView(withText("Item deleted successfully"))
                .check(ViewAssertions.matches(isDisplayed()));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Assuming the text "Multiple" is the first one on the Recycle view as input in the item description
        // The text "Delete" is the second one
        onView(withId(R.id.recycler_view))
                .check(ViewAssertions.matches(not(hasDescendant(withText("Multiple")))));
        onView(withId(R.id.recycler_view))
                .check(ViewAssertions.matches(not(hasDescendant(withText("Delete")))));


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