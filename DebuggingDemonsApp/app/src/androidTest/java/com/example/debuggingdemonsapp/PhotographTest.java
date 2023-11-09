package com.example.debuggingdemonsapp;


import android.Manifest;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class PhotographTest {
    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new
            ActivityScenarioRule<MainActivity>(MainActivity.class);

    // The following rule is from https://stackoverflow.com/questions/63837978/how-do-i-grant-permission-in-espresso
    @Rule
    public GrantPermissionRule permissionCamera = GrantPermissionRule.grant(Manifest.permission.CAMERA);

    @Test
    public void TakePhotoTest(){
        // Method tests the photo taking functionality of the application

        // Navigate to the camera
        onView(withId(R.id.navigation_camera)).perform(click());
       // Perform click on camera button
        onView(withId(R.id.camera_button)).perform(click());
        // Checks whether the fragment has changed to the 'photo preview' page
        //  Does this by checking if a button that is on the 'photo preview' page is displayed
        onView(withId(R.id.retake_button)).check(matches(isDisplayed()));

    }

    @Test
    public void RetakePhotoTest(){
        // Method tests the photo retaking functionality of the application

        // Navigate to the camera
        onView(withId(R.id.navigation_camera)).perform(click());
        // Perform click on camera button
        onView(withId(R.id.camera_button)).perform(click());
        // Checks whether the fragment has changed to the 'photo preview' page
        //  Does this by checking if a button that is on the 'photo preview' page is displayed
        onView(withId(R.id.retake_button)).check(matches(isDisplayed()));

        // Makes sure that the appPhotos list is empty as no photo has been saved
        scenario.getScenario().onActivity(activity -> {
            assertTrue(activity.appPhotos.isEmpty());

        });

        // Clicks on the retake button
        onView(withId(R.id.retake_button)).perform(click());

        // Checks whether the view has changed back to the camera
        onView(withId(R.id.navigation_camera)).check(matches(isDisplayed()));


    }

    @Test
    public void SavePhotoTest(){
        // Method tests the photo saving functionality of the app

        // Makes sure that the appPhotos list is empty as no photos have been saved yet
        scenario.getScenario().onActivity(activity -> {
            assertTrue(activity.appPhotos.isEmpty());
        });

        // Navigate to the camera
        onView(withId(R.id.navigation_camera)).perform(click());
        // Perform click on camera button
        onView(withId(R.id.camera_button)).perform(click());
        // Checks whether the fragment has changed to the 'photo preview' page
        //  Does this by checking if a button that is on the 'photo preview' page is displayed
        onView(withId(R.id.retake_button)).check(matches(isDisplayed()));

        // Clicks the save button
        onView(withId(R.id.save_button)).perform(click());

        // Once the image is saved the app should return to the camera page
        // Checks whether the page is now the camera page
        onView(withId(R.id.navigation_camera)).check(matches(isDisplayed()));

       // Checks whether photo has been added to the appPhotos list
        scenario.getScenario().onActivity(activity -> {
            assertFalse(activity.appPhotos.isEmpty());
        });

    }

    @Test
    public void DeletePhotoTest(){
        // Method to test photo deletion
    }

    @Test
    public void AddPhoto(){
        // Method to add photo to an item

        onView(withId(R.id.navigation_inventory)).perform(click());

        onView(withId(R.id.add_button)).perform(click());

        onView(withId(R.id.addImage1)).perform(click());

        onView(withText("Saved Photos")).check(matches(isDisplayed()));

        onView(withId(R.id.photoList_back)).perform(click());

        onView(withId(R.id.addImage2)).perform(click());

        onView(withText("Saved Photos")).check(matches(isDisplayed()));

        onView(withId(R.id.photoList_back)).perform(click());

        onView(withId(R.id.addImage3)).perform(click());

        onView(withText("Saved Photos")).check(matches(isDisplayed()));




    }
}
