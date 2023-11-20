package com.example.debuggingdemonsapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.isA;

import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.debuggingdemonsapp.model.Item;
import com.example.debuggingdemonsapp.model.Tag;
import com.example.debuggingdemonsapp.ui.inventory.EquipTagsAdapter;
import com.example.debuggingdemonsapp.ui.inventory.InventoryViewModel;
import com.example.debuggingdemonsapp.ui.inventory.ItemAdapter;
import com.example.debuggingdemonsapp.ui.tag.TagAdapter;
import com.example.debuggingdemonsapp.ui.tag.TagViewModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EquipTagsUITest {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
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
            Item item2 = new Item();
            item2.setDescription("second item");
            mockData.add(item1);
            mockData.add(item2);

            // set recycler view's adapter to mock data
            adapter.setItems(mockData);
        }
    }

    /*
    Set equip tags list to custom data
     */
    public class EquipTagsListCustomAdapter implements ViewAction {

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
            EquipTagsAdapter adapter = (EquipTagsAdapter) recyclerView.getAdapter();

            // create mock data
            ArrayList<Tag> mockData = new ArrayList<>();
            mockData.add(new Tag("first tag"));
            mockData.add(new Tag("second tag"));

            // set recycler view's adapter to mock data
            adapter.setTags(mockData);
            adapter.notifyDataSetChanged();
        }
    }

    /*
    Check box of specific item in recycler view
     */
    public class ItemCheckView implements ViewAction {

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
            CheckBox checkBox = view.findViewById(R.id.item_checkbox);
            checkBox.setChecked(true);
        }
    }

    /*
   Check box of specific tag in recycler view
    */
    public class TagCheckView implements ViewAction {

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
            CheckBox checkBox = view.findViewById(R.id.tag_checkbox);
            checkBox.setChecked(true);
        }
    }

    /*
    Check both tags are equipped on both items
     */
    public class CheckAdapterContents implements ViewAction {

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
            RecyclerView recyclerView = (RecyclerView) view;
            ItemAdapter itemAdapter = (ItemAdapter) recyclerView.getAdapter();
            for (Item item : itemAdapter.getSelectedItems()) {
                ArrayList<String> tags = item.getTagNames();
                assert tags.size() == 2;
                assert tags.contains("first tag");
                assert tags.contains("second tag");
            }
        }
    }

    @Test
    public void EquipTagsTest(){

        // navigate to inventory tab
        onView(withId(R.id.navigation_inventory)).perform(click());

        // create mock inventory data set
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.recycler_view)).perform(new ItemListCustomAdapter());

        // check boxes for both items
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, new ItemCheckView()));
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(1, new ItemCheckView()));

        // navigate to equip tags fragment
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.tag_button)).perform(click());

        // create mock tag data set
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.tag_list)).perform(new EquipTagsListCustomAdapter());

        // check boxes for both tags
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.tag_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, new TagCheckView()));
        onView(withId(R.id.tag_list)).perform(RecyclerViewActions.actionOnItemAtPosition(1, new TagCheckView()));

        // equip both new tags to both new items
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withText("OK")).perform(click());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.recycler_view)).perform(new CheckAdapterContents());
    }
}
