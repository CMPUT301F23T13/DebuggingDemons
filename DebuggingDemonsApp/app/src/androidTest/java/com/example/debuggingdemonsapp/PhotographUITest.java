package com.example.debuggingdemonsapp;


import android.Manifest;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class PhotographUITest {

    // UI Tests
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

        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            System.out.println(e);
        }
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
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            System.out.println(e);
        }
        onView(withId(R.id.retake_button)).check(matches(isDisplayed()));


        // Clicks the save button
        onView(withId(R.id.save_button)).check(matches(isDisplayed())).perform(click());

        // Once the image is saved the app should return to the camera page
        // Checks whether the page is now the camera page
        onView(withId(R.id.navigation_camera)).check(matches(isDisplayed()));

       // Checks whether photo has been added to the appPhotos list
        scenario.getScenario().onActivity(activity -> {
            assertFalse(activity.appPhotos.isEmpty());
        });

    }

    @Test
    public void DeletePhotoTest() {
        // Method to test photo deletion

        // Take a new photo
        onView(withId(R.id.navigation_camera)).perform(click());
        // Perform click on camera button
        onView(withId(R.id.camera_button)).perform(click());

        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            System.out.println(e);
        }
        onView(withId(R.id.save_button)).check(matches(isDisplayed())).perform(click());

        onView(withId(R.id.navigation_inventory)).perform(click());

        onView(withId(R.id.add_button)).perform(click());

        // Checks that photo is added to the imageView of the imagebutton that was pressed on
        onView(withId(R.id.addImage1)).perform(click());

        onView(withText("Saved Photos")).check(matches(isDisplayed()));


        onView(withId(R.id.photoContainer)).perform(click());

        // Change description of item to identify it
        onView(withId(R.id.editTextDescription)).perform(replaceText("delete"));

        // Save changes
        onView(withId(R.id.button_ok)).perform(click());

        onView(withId(R.id.navigation_inventory)).check(matches(isDisplayed()));

        // Click on the created item which opens a dialog fragment
        onView(withText("delete")).perform(click());

        onView(withText("EDIT")).inRoot(isDialog()).check(matches((isDisplayed()))).perform(click());

        onView(withText("EDIT")).check(matches(isDisplayed()));

        // Clicks on item to delete it from the item
        onView(withId(R.id.editImage1)).perform(click());

        // Checks that after click the 'edit page' is still selected
        onView(withText("EDIT")).check(matches(isDisplayed()));

        onView(withId(R.id.confirm_button)).check(matches(isDisplayed())).perform(click());


    }

    @Test
    public void photoListNavigateTest(){
        // Method to test whether clicking on image button correctly navigates to saved photos list
        //  Also checks that back button's functionality is correct

        onView(withId(R.id.navigation_inventory)).perform(click());

        onView(withId(R.id.add_button)).perform(click());
        // Checks that each of the image buttons go to the correct page and back navigation works

        onView(withId(R.id.addImage1)).perform(click());

        onView(withText("Saved Photos")).check(matches(isDisplayed()));

        onView(withId(R.id.photoList_back)).perform(click());

        onView(withId(R.id.addImage2)).perform(click());

        onView(withText("Saved Photos")).check(matches(isDisplayed()));

        onView(withId(R.id.photoList_back)).perform(click());

        onView(withId(R.id.addImage2)).perform(click());

        onView(withText("Saved Photos")).check(matches(isDisplayed()));

        onView(withId(R.id.photoList_back)).perform(click());


    }

    @Test
    public void addPhotoTest() {
        // Method to check adding photo to an item

        // Take a new photo

        onView(withId(R.id.navigation_camera)).perform(click());
        // Perform click on camera button
        onView(withId(R.id.camera_button)).perform(click());

        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            System.out.println(e);
        }
        onView(withId(R.id.save_button)).check(matches(isDisplayed())).perform(click());

        // Navigate to the inventory menu
        onView(withId(R.id.navigation_inventory)).perform(click());

        onView(withId(R.id.add_button)).perform(click());

        // Checks that photo is added to the imageView of the imagebutton that was pressed on
        onView(withId(R.id.addImage1)).perform(click());

        onView(withText("Saved Photos")).check(matches(isDisplayed()));

        onView(withId(R.id.photoContainer)).perform(click());

        onView(withId(R.id.addImage1)).check(matches(isDisplayed()));
        // Need to find a way to check whether the image has changed
        // So far relying on no error messages occurring to verify that action is successful

    }

}
