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
import com.example.debuggingdemonsapp.model.Tag;
import com.example.debuggingdemonsapp.ui.inventory.ItemAdapter;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import android.view.View;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class FilterByTagUITest {
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
            item1.addTag(new Tag("A"));
            Item item2 = new Item();
            item1.addTag(new Tag("c"));
            mockData.add(item1);
            mockData.add(item2);

            adapter.setItems(mockData);
            adapter.notifyDataSetChanged();
        }
    }

    // Custom ViewAction to check the filtered items
    public static class CheckFilteredByTag implements ViewAction {
        private final String expectedTag;

        public CheckFilteredByTag(String expectedTag) {
            this.expectedTag = expectedTag;
        }

        @Override
        public Matcher<View> getConstraints() {
            return any(View.class);
        }

        @Override
        public String getDescription() {
            return "Check if items are filtered by tag: " + expectedTag;
        }

        @Override
        public void perform(UiController uiController, View view) {
            RecyclerView recyclerView = (RecyclerView) view;
            ItemAdapter adapter = (ItemAdapter) recyclerView.getAdapter();

            for (Item item : adapter.getItems()) {
                assertTrue("Item does not contain the expected tag.",
                        item.getTagNames().contains(expectedTag));
            }
        }
    }

    @Test
    public void filterByTagTest() {
        onView(withId(R.id.navigation_inventory)).perform(click());
        onView(withId(R.id.recycler_view)).perform(new ItemListCustomAdapter());

        onView(withId(R.id.filter_button)).perform(click());
        onView(withId(R.id.tag_filter)).perform(typeText("Tag1"), closeSoftKeyboard());
        onView(withId(R.id.filter_by_tag_button)).perform(click());

        onView(withId(R.id.recycler_view)).perform(new CheckFilteredByTag("Tag1"));
    }
}
