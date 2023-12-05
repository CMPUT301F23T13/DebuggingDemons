package com.example.debuggingdemonsapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.any;
import static org.junit.Assert.assertTrue;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.debuggingdemonsapp.model.Item;
import com.example.debuggingdemonsapp.ui.inventory.ItemAdapter;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import android.view.View;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class FilterByMakeUITest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    // Custom ViewAction to set up mock inventory data
    public static class ItemListCustomAdapter implements ViewAction {
        @Override
        public Matcher<View> getConstraints() {
            return any(View.class);
        }

        @Override
        public String getDescription() {
            return "Custom adapter to set list of items.";
        }

        @Override
        public void perform(UiController uiController, View view) {
            RecyclerView recyclerView = (RecyclerView) view;
            ItemAdapter adapter = (ItemAdapter) recyclerView.getAdapter();

            ArrayList<Item> mockData = new ArrayList<>();
            Item item1 = new Item();
            item1.setMake("Make1");
            Item item2 = new Item();
            item2.setMake("Make2");
            mockData.add(item1);
            mockData.add(item2);

            adapter.setItems(mockData);
            adapter.notifyDataSetChanged();
        }
    }

    // Custom ViewAction to check the filtered items
    public static class CheckFilteredByMake implements ViewAction {
        private final String expectedMake;

        public CheckFilteredByMake(String expectedMake) {
            this.expectedMake = expectedMake;
        }

        @Override
        public Matcher<View> getConstraints() {
            return any(View.class);
        }

        @Override
        public String getDescription() {
            return "Check if items are filtered by make: " + expectedMake;
        }

        @Override
        public void perform(UiController uiController, View view) {
            RecyclerView recyclerView = (RecyclerView) view;
            ItemAdapter adapter = (ItemAdapter) recyclerView.getAdapter();

            for (Item item : adapter.getItems()) {
                assertTrue("Item make does not match the expected make.",
                        item.getMake().equalsIgnoreCase(expectedMake));
            }
        }
    }

    @Test
    public void filterByMakeTest() {
        onView(withId(R.id.navigation_inventory)).perform(click());
        onView(withId(R.id.recycler_view)).perform(new ItemListCustomAdapter());

        onView(withId(R.id.filter_button)).perform(click());
        onView(withId(R.id.make_filter)).perform(typeText("Make1"), closeSoftKeyboard());
        onView(withId(R.id.filter_by_make_button)).perform(click());

        onView(withId(R.id.recycler_view)).perform(new CheckFilteredByMake("Make1"));
    }
}
