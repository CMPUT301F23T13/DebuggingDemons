package com.example.debuggingdemonsapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.any;

import android.view.View;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.debuggingdemonsapp.model.Item;
import com.example.debuggingdemonsapp.model.Tag;
import com.example.debuggingdemonsapp.ui.inventory.EquipTagsAdapter;
import com.example.debuggingdemonsapp.ui.inventory.ItemAdapter;
import com.google.firebase.firestore.FirebaseFirestore;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EditItemsTagsUITest {
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
            mockData.add(item1);

            // set recycler view's adapter to mock data
            adapter.setItems(mockData);
        }
    }

    /*
    Set update tags list to custom data
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
   Check or uncheck box of specific tag in recycler view
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
            checkBox.setChecked(!checkBox.isChecked());  // flip state of checkbox
        }
    }

    /*
    Check both tags are equipped
     */
    public class CheckForTags implements ViewAction {

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

    /*
    Check no tags are equipped
     */
    public class CheckForNoTags implements ViewAction {

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
                assert tags.size() == 0;
            }
        }
    }

    @Test
    public void EditItemsTagsTest() {
        // navigate to inventory tab
        onView(withId(R.id.navigation_inventory)).perform(click());

        // create mock inventory data set
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.recycler_view)).perform(new EditItemsTagsUITest.ItemListCustomAdapter());

        // click on item
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // navigate to update tags fragment
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(android.R.id.button3)).perform((click()));  // edit button
        onView(withId(R.id.update_tags_button)).perform(click());

        // create mock tag data set
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.tag_list)).perform(new EditItemsTagsUITest.EquipTagsListCustomAdapter());

        // check boxes for both tags
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.tag_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, new EditItemsTagsUITest.TagCheckView()));
        onView(withId(R.id.tag_list)).perform(RecyclerViewActions.actionOnItemAtPosition(1, new EditItemsTagsUITest.TagCheckView()));

        // click ok
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withText("OK")).perform(click());

        // confirm changes
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.confirm_button)).perform(click());

        // assert item has both tags equipped
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.recycler_view)).perform(new EditItemsTagsUITest.CheckForTags());

        // click on item
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // navigate to update tags fragment
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(android.R.id.button3)).perform((click()));  // edit button
        onView(withId(R.id.update_tags_button)).perform(click());

        // re-create mock tag data set (and check boxes for both tags)
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.tag_list)).perform(new EditItemsTagsUITest.EquipTagsListCustomAdapter());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.tag_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, new EditItemsTagsUITest.TagCheckView()));
        onView(withId(R.id.tag_list)).perform(RecyclerViewActions.actionOnItemAtPosition(1, new EditItemsTagsUITest.TagCheckView()));

        // uncheck boxes for both tags
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.tag_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, new EditItemsTagsUITest.TagCheckView()));
        onView(withId(R.id.tag_list)).perform(RecyclerViewActions.actionOnItemAtPosition(1, new EditItemsTagsUITest.TagCheckView()));

        // click ok
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withText("OK")).perform(click());

        // confirm changes
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.confirm_button)).perform(click());

        // assert item has no tags equipped
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.recycler_view)).perform(new EditItemsTagsUITest.CheckForNoTags());
    }
}
