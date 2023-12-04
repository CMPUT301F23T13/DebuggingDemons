package com.example.debuggingdemonsapp;

import android.widget.DatePicker;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import androidx.test.filters.LargeTest;

import org.hamcrest.Matcher;
import org.jetbrains.annotations.NotNull;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

import android.view.View;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SortByItemFragmentUITest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testSortingWithoutChoosing() throws InterruptedException {
        // The purpose of this test is to verify the application's response when the sorting dialog
        // is confirmed without selecting any sorting criteria.

        // Step 1: Navigate to the Inventory section of the app.
        // This step assumes that there is a navigation component (like a bottom navigation bar or a menu)
        // where one of the items is identified by R.id.navigation_inventory.
        onView(withId(R.id.navigation_inventory)).perform(click());

        // Step 2: Wait for the Inventory screen to load.
        // This delay is to account for any loading processes. However, in a robust testing environment,
        // it's recommended to use Espresso's Idling Resources for synchronizing with background processes.
        Thread.sleep(1000);

        // Step 3: Open the sorting dialog.
        // This assumes that there is a button in the Inventory screen identified by R.id.sortby_button,
        // which when clicked, opens a dialog for sorting options.
        onView(withId(R.id.sortby_button)).perform(click());

        // Step 4: Wait briefly to ensure the dialog is fully visible.
        // As noted earlier, using Thread.sleep is not ideal for production tests due to potential flakiness.
        Thread.sleep(1000);

        // Step 5: Click the confirm button on the AlertDialog.
        // This step simulates the user clicking the confirm button without selecting any sorting option.
        // Typically, the confirm button in Android dialogs has the ID android.R.id.button1.
        onView(withId(android.R.id.button1)).perform(click());

        // Step 6: Verify that the correct error message is displayed.
        // The test checks whether the app shows a specific message ("Please select a sorting criterion")
        // to the user, indicating that they haven't selected any sorting criteria before confirming.
        onView(withText("Please select a sorting criterion")).check(matches(isDisplayed()));
    }


    @Test
    public void testSortingWithoutSelectingSortingOrder() throws InterruptedException {
        // Test to ensure the app prompts for sorting order when a criterion is selected but the order isn't.

        // Step 1: Navigate to the Inventory section of the app.
        // This action simulates the user clicking on the inventory section, identified by R.id.navigation_inventory.
        onView(withId(R.id.navigation_inventory)).perform(click());

        // Step 2: Wait for potential loading processes.
        // This delay accounts for any asynchronous operations. For better testing practice, use Espresso's Idling Resources instead of Thread.sleep.
        Thread.sleep(1000);

        // Step 3: Open the sorting dialog.
        // Simulate a click on the button that opens the sorting options dialog, identified by R.id.sortby_button.
        onView(withId(R.id.sortby_button)).perform(click());

        // Step 4: Allow time for the sorting dialog to open.
        // Thread.sleep is used here for simplicity, but it's not recommended due to potential flakiness in tests.
        Thread.sleep(1000);

        // Step 5: Select a sorting criterion.
        // Choose a sorting criterion, here exemplified as 'sort by description', with its respective button identified by R.id.sort_by_description.
        onView(withId(R.id.sort_by_description)).perform(click());

        // Step 6: Simulate a brief user interaction delay.
        Thread.sleep(1000);

        // Step 7: Confirm the selection in the AlertDialog.
        // This action simulates clicking the dialog's confirm button, typically identified by android.R.id.button1.
        onView(withId(android.R.id.button1)).perform(click());

        // Step 8: Verify the error message for not selecting a sorting order.
        // The test checks if the appropriate error message "Please select a sorting order" is displayed when no order is chosen.
        onView(withText("Please select a sorting order")).check(matches(isDisplayed()));
    }




    @Test
    public void testSortingChangesItemOrderByDate() throws InterruptedException {
        // Navigate to inventory
        onView(withId(R.id.navigation_inventory)).perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        addItem("test1", "abc", "model1", "100", "first item", 2023, 1, 1);
        addItem("test2", "def", "model2", "10", "second item", 2021, 10, 20);

        // Sort items by date in ascending order and verify the first item.
        sortItems(R.id.sort_by_date, R.id.sort_ascending);
        verifyFirstItemInList(R.id.recycler_view, "test2");

        // Sort items by date in descending order and verify the first item.
        sortItems(R.id.sort_by_date, R.id.sort_descending);
        verifyFirstItemInList(R.id.recycler_view, "test1");

        // Select and delete both items
        selectAndDeleteItems(new String[]{"test1", "test2"});
    }


    @Test
    public void testSortingChangesItemOrderByDescription() throws InterruptedException {
        // This test checks if the sorting by description works correctly in both ascending and descending orders.

        // Navigate to the inventory section of the app.
        onView(withId(R.id.navigation_inventory)).perform(click());
        Thread.sleep(1000); // Wait for any potential loading.

        // Add two items with different descriptions, dates, and other details for sorting.
        addItem("test1", "abc", "model1", "100", "first item", 2023, 1, 1);
        addItem("test2", "def", "model2", "10", "second item", 2021, 10, 20);

        // Perform sorting by description in ascending order and verify the first item.
        sortItems(R.id.sort_by_description, R.id.sort_ascending);
        verifyFirstItemInList(R.id.recycler_view, "test1");

        // Perform sorting by description in descending order and verify the first item.
        sortItems(R.id.sort_by_description, R.id.sort_descending);
        verifyFirstItemInList(R.id.recycler_view, "test2");

        // Clean up: delete added items.
        selectAndDeleteItems(new String[]{"test1", "test2"});
    }

    @Test
    public void testSortingChangesItemOrderByMake() throws InterruptedException {
        // This test verifies that the sorting by item make works correctly in both ascending and descending orders.

        // Navigate to the inventory section of the app.
        onView(withId(R.id.navigation_inventory)).perform(click());
        Thread.sleep(1000); // Wait for any potential loading.

        // Add two items with different 'make' values and other details for sorting.
        addItem("test1", "abc", "model1", "100", "first item", 2023, 1, 1);
        addItem("test2", "def", "model2", "10", "second item", 2021, 10, 20);

        // Perform sorting by 'make' in ascending order and verify the first item.
        sortItems(R.id.sort_by_make, R.id.sort_ascending);
        verifyFirstItemInList(R.id.recycler_view, "test1");

        // Perform sorting by 'make' in descending order and verify the first item.
        sortItems(R.id.sort_by_make, R.id.sort_descending);
        verifyFirstItemInList(R.id.recycler_view, "test2");

        // Clean up: delete added items.
        selectAndDeleteItems(new String[]{"test1", "test2"});
    }

    @Test
    public void testSortingChangesItemOrderByEstimatedValue() throws InterruptedException {
        // This test checks if the sorting by estimated value works correctly in both ascending and descending orders.

        // Navigate to the inventory section of the app.
        onView(withId(R.id.navigation_inventory)).perform(click());
        Thread.sleep(1000); // Wait for any potential loading.

        // Add two items with different estimated values and other details for sorting.
        addItem("test1", "abc", "model1", "100", "first item", 2023, 1, 1);
        addItem("test2", "def", "model2", "10", "second item", 2021, 10, 20);

        // Perform sorting by estimated value in ascending order and verify the first item.
        sortItems(R.id.sort_by_value, R.id.sort_ascending);
        verifyFirstItemInList(R.id.recycler_view, "test2");

        // Perform sorting by estimated value in descending order and verify the first item.
        sortItems(R.id.sort_by_value, R.id.sort_descending);
        verifyFirstItemInList(R.id.recycler_view, "test1");

        // Clean up: delete added items.
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


    /**
     * Opens the sorting dialog, selects a sorting criterion and order, and confirms the choice.
     *
     * @param sortCriterionId The ID of the sorting criterion button.
     * @param sortOrder The ID of the sorting order button.
     * @throws InterruptedException If interrupted during sleep.
     */
    private void sortItems(int sortCriterionId, int sortOrder) throws InterruptedException {
        // Open the sorting dialog.
        onView(withId(R.id.sortby_button)).perform(click());

        // Wait for the dialog to open.
        Thread.sleep(1000);

        // Select the sorting criterion.
        onView(withId(sortCriterionId)).perform(click());

        // Select the sorting order.
        onView(withId(sortOrder)).perform(click());

        // Confirm the sorting choices.
        onView(withId(android.R.id.button1)).perform(click());

        // Wait for the sorting to complete.
        Thread.sleep(1000);
    }


    /**
     * Verifies that the first item in the RecyclerView matches the expected description.
     *
     * @param recyclerViewId The ID of the RecyclerView.
     * @param expectedFirstItemText The expected text of the first item.
     * @throws InterruptedException If interrupted during sleep.
     */
    private void verifyFirstItemInList(int recyclerViewId, String expectedFirstItemText) throws InterruptedException {
        // Scroll to the item that is expected to be first in the list and verify it.
        onView(withId(recyclerViewId))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText(expectedFirstItemText))))
                .check(matches(hasDescendant(withText(expectedFirstItemText))));
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
