package com.example.debuggingdemonsapp;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import com.example.debuggingdemonsapp.model.Item;
import com.example.debuggingdemonsapp.ui.inventory.ItemAdapter;
import org.hamcrest.Matcher;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.not;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem;

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

        onView(withId(R.id.add_button)).perform(click());


        onView(withId(R.id.editTextDescription)).perform(typeText("test"), closeSoftKeyboard());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        onView(withId(R.id.button_ok)).perform(click());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        onView(withId(R.id.recycler_view))
                .perform(scrollTo(hasDescendant(withText("test"))));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        onView(withId(R.id.recycler_view))
                .perform(actionOnItem(hasDescendant(withText("test")), clickOnViewChild(R.id.item_checkbox)));


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


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

        onView(withId(R.id.add_button)).perform(click());


        onView(withId(R.id.editTextDescription)).perform(typeText("test1"), closeSoftKeyboard());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        onView(withId(R.id.button_ok)).perform(click());


        onView(withId(R.id.add_button)).perform(click());


        onView(withId(R.id.editTextDescription)).perform(typeText("test2"), closeSoftKeyboard());


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        onView(withId(R.id.button_ok)).perform(click());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        onView(withId(R.id.recycler_view))
                .perform(scrollTo(hasDescendant(withText("test1"))));

        onView(withId(R.id.recycler_view))
                .perform(scrollTo(hasDescendant(withText("test2"))));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        onView(withId(R.id.recycler_view))
                .perform(actionOnItem(hasDescendant(withText("test1")), clickOnViewChild(R.id.item_checkbox)));

        onView(withId(R.id.recycler_view))
                .perform(actionOnItem(hasDescendant(withText("test2")), clickOnViewChild(R.id.item_checkbox)));


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

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
                .check(ViewAssertions.matches(not(hasDescendant(withText("test1")))));
        onView(withId(R.id.recycler_view))
                .check(ViewAssertions.matches(not(hasDescendant(withText("test2")))));


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