package com.example.debuggingdemonsapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.util.HumanReadables;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.debuggingdemonsapp.ui.inventory.InventoryFragment;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
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
        onView(withId(R.id.navigation_home)).perform(click());

//        // Check if inventory fragment is displayed
//        onView(withId(R.id.fragment_inventory)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void deleteItemTest() {
        // Navigate to inventory
        onView(withId(R.id.navigation_inventory)).perform(click());

        // Assume there are items in the list and the first item can be deleted
        // Click on the checkbox of the first item
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, clickChildViewWithId(R.id.item_checkbox)));

        // Click on the 'Delete' button
        onView(withId(R.id.delete_button)).perform(click());

        // Check if the Snackbar message is displayed
        onView(withText("Item deleted successfully")).check(ViewAssertions.matches(isDisplayed()));
    }


    // Helper method to perform click on child views within RecyclerView item
    // Helper method to perform click on child views within RecyclerView item
    public static ViewAction clickChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return new TypeSafeMatcher<View>() {
                    @Override
                    public boolean matchesSafely(View view) {
                        // 这里可以添加更多的检查条件，比如view是否可点击等
                        return view != null;
                    }

                    @Override
                    public void describeTo(Description description) {
                        description.appendText("View with id " + id + " is not found.");
                    }
                };
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                if (v != null) {
                    v.performClick();
                } else {
                    throw new PerformException.Builder()
                            .withActionDescription(this.getDescription())
                            .withViewDescription(HumanReadables.describe(view))
                            .withCause(new IllegalStateException("No view with ID " + id + " was found."))
                            .build();
                }
            }
        };
    }

}