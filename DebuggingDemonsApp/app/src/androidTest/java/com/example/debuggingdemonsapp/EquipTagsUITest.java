package com.example.debuggingdemonsapp;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.isA;

import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.debuggingdemonsapp.ui.inventory.InventoryViewModel;
import com.example.debuggingdemonsapp.ui.tag.TagViewModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
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

    public class CheckView implements ViewAction {

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public Matcher<View> getConstraints() {
            return null;
        }

        @Override
        public void perform(UiController uiController, View view) {
            CheckBox checkBox = view.findViewById(R.id.item_checkbox);
            checkBox.setChecked(true);
        }
    }

    private String mockTagName() {
        return "fae390ba";
    }

    private String anotherMockTagName() {
        return "gae390ba";
    }

    private ArrayList<String> mockItemData() {
        ArrayList<String> itemData = new ArrayList<>();
        itemData.add("2023");
        itemData.add("fae");
        itemData.add("fae");
        itemData.add("fae");
        itemData.add("100");
        itemData.add("fae");
        return itemData;
    }

    private ArrayList<String> moreMockItemData() {
        ArrayList<String> itemData = new ArrayList<>();
        itemData.add("2023");
        itemData.add("gae");
        itemData.add("gae");
        itemData.add("gae");
        itemData.add("100");
        itemData.add("gae");
        return itemData;
    }

    @Test
    public void EquipTagsTest(){
        // TODO: fix this

        // navigate to tag tab
        onView(withId(R.id.navigation_tag)).perform(click());

        // define new tag
        onView(withId(R.id.add_tag_button)).perform(click());
        onView(withId(R.id.tag_name_editText)).perform(ViewActions.typeText(mockTagName()));
        onView(withText("OK")).perform(click());

        // define another new tag
        onView(withId(R.id.add_tag_button)).perform(click());
        onView(withId(R.id.tag_name_editText)).perform(ViewActions.typeText(anotherMockTagName()));
        onView(withText("OK")).perform(click());

        // navigate to inventory tab
        onView(withId(R.id.navigation_inventory)).perform(click());

        // create a new item
        onView(withId(R.id.add_button)).perform(click());
        ArrayList<String> itemData = mockItemData();
        onView(withId(R.id.editTextDateOfPurchase)).perform(ViewActions.typeText(itemData.get(0)));
        onView(withId(R.id.editTextDescription)).perform(ViewActions.typeText(itemData.get(1)));
        onView(withId(R.id.editTextMake)).perform(ViewActions.typeText(itemData.get(2)));
        onView(withId(R.id.editTextModel)).perform(ViewActions.typeText(itemData.get(3)));
        onView(withId(R.id.editTextEstimatedValue)).perform(ViewActions.typeText(itemData.get(4)));
        onView(withId(R.id.editTextComment)).perform(ViewActions.typeText(itemData.get(5)));
        onView(withId(R.id.button_ok)).perform(click());
        onView(withText(itemData.get(1))).check(matches(isDisplayed()));  // ensure item was added

        // create another new item
        onView(withId(R.id.add_button)).perform(click());
        ArrayList<String> moreItemData = moreMockItemData();
        onView(withId(R.id.editTextDateOfPurchase)).perform(ViewActions.typeText(moreItemData.get(0)));
        onView(withId(R.id.editTextDescription)).perform(ViewActions.typeText(moreItemData.get(1)));
        onView(withId(R.id.editTextMake)).perform(ViewActions.typeText(moreItemData.get(2)));
        onView(withId(R.id.editTextModel)).perform(ViewActions.typeText(moreItemData.get(3)));
        onView(withId(R.id.editTextEstimatedValue)).perform(ViewActions.typeText(moreItemData.get(4)));
        onView(withId(R.id.editTextComment)).perform(ViewActions.typeText(moreItemData.get(5)));
        onView(withId(R.id.button_ok)).perform(click());
        onView(withText(moreItemData.get(1))).check(matches(isDisplayed()));  // ensure item was added

        // get number of items and tags
        InventoryViewModel inventoryViewModel = new InventoryViewModel();
        TagViewModel tagViewModel = new TagViewModel();
        while (inventoryViewModel.getItems().getValue().size() == 0 || tagViewModel.getTags().getValue().size() == 0);
        int itemCount = inventoryViewModel.getItems().getValue().size();
        int tagCount = tagViewModel.getTags().getValue().size();

        // check boxes for both items
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(itemCount-1, new CheckView()));
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(itemCount-2, new CheckView()));

        // equip both new tags to both new items
        onView(withId(R.id.tag_button)).perform(click());
        onView(withId(R.id.tag_list)).perform(RecyclerViewActions.actionOnItemAtPosition(tagCount-1, new CheckView()));
        onView(withId(R.id.tag_list)).perform(RecyclerViewActions.actionOnItemAtPosition(tagCount-2, new CheckView()));
        onView(withText("OK")).perform(click());

        // ensure both tags are equipped on first item
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(itemCount-1, click()));
        onView(withText(mockTagName())).check(matches(isDisplayed()));
        onView(withText(anotherMockTagName())).check(matches(isDisplayed()));
        onView(withText("OK")).perform(click());

        // ensure both new tags are equipped on second item
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(itemCount-2, click()));
        onView(withText(mockTagName())).check(matches(isDisplayed()));
        onView(withText(anotherMockTagName())).check(matches(isDisplayed()));
        onView(withText("OK")).perform(click());

        // revert changes by deleting items
        onView(withId(R.id.delete_button)).perform(click());

        // revert changes by deleting tags
        Query query = db.collection("tags").whereEqualTo("name", mockTagName());
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    document.getReference().delete();
                }
            }
        });
        Query anotherQuery = db.collection("tags").whereEqualTo("name", anotherMockTagName());
        anotherQuery.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    document.getReference().delete();
                }
            }
        });
    }
}
