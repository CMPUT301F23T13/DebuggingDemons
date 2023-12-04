package com.example.debuggingdemonsapp;

import android.view.View;
import android.widget.DatePicker;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import org.hamcrest.Matcher;
import org.jetbrains.annotations.NotNull;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class InventoryDeleteUITest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void test1_IsInventoryFragmentDisplayed() {
        // Navigate to the inventory fragment
        onView(withId(R.id.navigation_inventory)).perform(click());

        // Verify visibility of key components in the inventory screen
        onView(withId(R.id.filter_button)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.sortby_button)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.recycler_view)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.tag_button)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.delete_button)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.add_button)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void test2_ClickDeleteButton_WithoutAnyCheckbox() {
        // Navigate to inventory and click delete without selecting any item
        onView(withId(R.id.navigation_inventory)).perform(click());
        onView(withId(R.id.delete_button)).perform(click());
        // Verify that a message is displayed for no items being selected
        onView(withText("No items selected to be deleted.")).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void test3_Checkbox_CheckingAndDelete() throws InterruptedException {
        // Navigate to inventory and add a new item
        onView(withId(R.id.navigation_inventory)).perform(click());
        onView(withId(R.id.add_button)).perform(click());

        // Set a purchase date
        setDate(R.id.editTextDateOfPurchase, 2023, 1, 1);
        onView(withId(R.id.editTextDateOfPurchase)).check(matches(withText("2023-1-1")));

        // Fill in item details
        fillItemDetails("test", "abc", "model1", "100", "first item");

        // Select the added item and delete it
        selectAndDeleteItem("test");
    }

    @Test
    public void test4_Checkbox_MultipleCheckingAndDelete() throws InterruptedException {
        // Navigate to inventory and add two new items
        onView(withId(R.id.navigation_inventory)).perform(click());
        addItem("test1", "abc", "model1", "100", "first item", 2023, 1, 1);
        addItem("test2", "def", "model2", "10", "second item", 2021, 10, 20);

        // Select and delete both items
        selectAndDeleteItems(new String[]{"test1", "test2"});
    }

    // Helper methods for setting date, filling item details, and item selection/deletion
    /**
     * Sets a date on a DatePicker widget.
     *
     * @param editTextId The ID of the EditText that triggers the DatePicker.
     * @param year       The year to be set on the DatePicker.
     * @param month      The month to be set on the DatePicker (Note: Month is zero-based).
     * @param day        The day to be set on the DatePicker.
     */
    private void setDate(int editTextId, int year, int month, int day) {
        // Click on the EditText to trigger the DatePicker dialog.
        onView(withId(editTextId)).perform(click());

        // Set the desired date on the DatePicker.
        onView(withClassName(equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(year, month, day));

        // Click the confirm button to close the DatePicker and set the date.
        onView(withId(android.R.id.button1)).perform(click());
    }


    /**
     * Fills in the item details in the corresponding fields and submits the form.
     *
     * @param description The description of the item.
     * @param make        The make of the item.
     * @param model       The model of the item.
     * @param value       The estimated value of the item.
     * @param comment     Any additional comments about the item.
     */
    private void fillItemDetails(String description, String make, String model, String value, String comment) {
        // Fill in the text fields with the provided details.
        onView(withId(R.id.editTextDescription)).perform(typeText(description), closeSoftKeyboard());
        onView(withId(R.id.editTextMake)).perform(typeText(make), closeSoftKeyboard());
        onView(withId(R.id.editTextModel)).perform(typeText(model), closeSoftKeyboard());
        onView(withId(R.id.editTextEstimatedValue)).perform(typeText(value), closeSoftKeyboard());
        onView(withId(R.id.editTextComment)).perform(typeText(comment), closeSoftKeyboard());

        // Submit the form by clicking the OK button.
        onView(withId(R.id.button_ok)).perform(click());
    }


    /**
     * Adds a new item to the inventory using the specified details.
     *
     * @param description Description of the item.
     * @param make        Make of the item.
     * @param model       Model of the item.
     * @param value       Estimated value of the item.
     * @param comment     Comment for the item.
     * @param year        Year of the purchase date.
     * @param month       Month of the purchase date.
     * @param day         Day of the purchase date.
     * @throws InterruptedException If interrupted during sleep.
     */
    private void addItem(String description, String make, String model, String value, String comment, int year, int month, int day) throws InterruptedException {
        // Navigate to the add item screen.
        onView(withId(R.id.add_button)).perform(click());

        // Set the purchase date.
        setDate(R.id.editTextDateOfPurchase, year, month, day);

        // Fill in the item details and submit.
        fillItemDetails(description, make, model, value, comment);

        // Wait for the item to be added (consider replacing with more robust synchronization).
        Thread.sleep(1000);
    }

    /**
     * Selects and deletes an item from the inventory based on its description.
     *
     * @param description The description of the item to be deleted.
     * @throws InterruptedException If interrupted during the thread sleep.
     */
    private void selectAndDeleteItem(String description) throws InterruptedException {
        // Scroll to the item in the RecyclerView with the given description.
        onView(withId(R.id.recycler_view))
                .perform(scrollTo(hasDescendant(withText(description))));

        // Perform a click action on the checkbox of the item to select it.
        // Assumes each item in the RecyclerView has a checkbox identified by R.id.item_checkbox.
        onView(withId(R.id.recycler_view))
                .perform(actionOnItem(hasDescendant(withText(description)), clickOnViewChild(R.id.item_checkbox)));

        // Wait briefly to ensure the UI updates to reflect the item selection.
        Thread.sleep(1000);

        // Click the delete button to remove the selected item.
        onView(withId(R.id.delete_button)).perform(click());

        // Wait for the deletion process to complete.
        Thread.sleep(1000);

        // Verify that the deletion was successful by checking for the success message.
        onView(withText("Item deleted successfully")).check(ViewAssertions.matches(isDisplayed()));

        // Wait briefly to ensure the RecyclerView updates after the item deletion.
        Thread.sleep(1000);

        // Verify that the item is no longer present in the RecyclerView.
        onView(withId(R.id.recycler_view)).check(ViewAssertions.matches(not(hasDescendant(withText(description)))));
    }


    /**
     * Selects and deletes items from the inventory based on their descriptions.
     *
     * @param descriptions Array of item descriptions to be deleted.
     * @throws InterruptedException If interrupted during sleep.
     */
    private void selectAndDeleteItems(String @NotNull [] descriptions) throws InterruptedException {
        for (String description : descriptions) {
            // Scroll to the item and click its checkbox to select it.
            onView(withId(R.id.recycler_view))
                    .perform(scrollTo(hasDescendant(withText(description))))
                    .perform(actionOnItem(hasDescendant(withText(description)), clickOnViewChild(R.id.item_checkbox)));
        }

        // Wait briefly for selections to register.
        Thread.sleep(1000);

        // Click the delete button to remove selected items.
        onView(withId(R.id.delete_button)).perform(click());

        // Wait for deletion process to complete and verify successful deletion message.
        Thread.sleep(1000);
        onView(withText("Item deleted successfully")).check(ViewAssertions.matches(isDisplayed()));

        // Verify that the items are no longer present in the list.
        Thread.sleep(1000);
        for (String description : descriptions) {
            onView(withId(R.id.recycler_view)).check(ViewAssertions.matches(not(hasDescendant(withText(description)))));
        }
    }

    // Helper method to perform click on a child view within the RecyclerView item
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
