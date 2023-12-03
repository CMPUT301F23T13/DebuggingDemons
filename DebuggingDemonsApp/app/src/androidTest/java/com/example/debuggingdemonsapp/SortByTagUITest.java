package com.example.debuggingdemonsapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.Matchers.any;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
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

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SortByTagUITest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    /*
    Set item list to custom data
     */
    public class ItemListCustomAdapter implements ViewAction {

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public Matcher<View> getConstraints() {
            return any(View.class);
        }

        @Override
        public void perform(UiController uiController, View view) {
            // get recycler view
            RecyclerView recyclerView = (RecyclerView) view;
            ItemAdapter adapter = (ItemAdapter) recyclerView.getAdapter();

            // create mock data
            ArrayList<Item> mockData = new ArrayList<>();
            Item item1 = new Item();
            item1.setDescription("first item");
            item1.addTag(new Tag("A"));
            item1.addTag(new Tag("c"));
            Item item2 = new Item();
            item2.setDescription("second item");
            item2.addTag(new Tag("a"));
            item2.addTag(new Tag("b"));
            mockData.add(item1);
            mockData.add(item2);

            // set recycler view's adapter to mock data
            adapter.setItems(mockData);
        }
    }

    /*
    Check items are sorted in ascending order
     */
    public class CheckAscending implements ViewAction {

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public Matcher<View> getConstraints() {
            return any(View.class);
        }

        @Override
        public void perform(UiController uiController, View view) {
            // get recycler view
            RecyclerView recyclerView = (RecyclerView) view;
            ItemAdapter adapter = (ItemAdapter) recyclerView.getAdapter();

            // assert second item is top of list
            assert adapter.getItems().get(0).getDescription().equals("second item");
        }
    }

    /*
    Check items are sorted in descending order
     */
    public class CheckDescending implements ViewAction {

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public Matcher<View> getConstraints() {
            return any(View.class);
        }

        @Override
        public void perform(UiController uiController, View view) {
            // get recycler view
            RecyclerView recyclerView = (RecyclerView) view;
            ItemAdapter adapter = (ItemAdapter) recyclerView.getAdapter();

            // assert second item is top of list
            assert adapter.getItems().get(0).getDescription().equals("first item");
        }
    }

    @Test
    public void SortByTagTest() {
        // navigate to inventory tab
        onView(withId(R.id.navigation_inventory)).perform(click());

        // create mock inventory data set
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.recycler_view)).perform(new SortByTagUITest.ItemListCustomAdapter());

        // go to sort fragment
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.sortby_button)).perform(click());

        // select sort by tag and ascending
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.sort_by_tag)).perform(click());
        onView(withId(R.id.sort_ascending)).perform(click());

        // confirm
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Espresso.onView(withId(android.R.id.button1)).perform(click());

        // assert items are sorted correctly
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.recycler_view)).perform(new SortByTagUITest.CheckAscending());

        // go to sort fragment
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.sortby_button)).perform(click());

        // select sort by tag and descending
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.sort_by_tag)).perform(click());
        onView(withId(R.id.sort_descending)).perform(click());

        // confirm
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Espresso.onView(withId(android.R.id.button1)).perform(click());

        // assert items are sorted correctly
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.recycler_view)).perform(new SortByTagUITest.CheckDescending());
    }
}
