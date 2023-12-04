package com.example.debuggingdemonsapp;


import android.Manifest;
import android.view.View;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.hasWindowLayoutParams;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.*;
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
    public void takePhotoTest(){
        // Method tests the photo taking functionality of the application

        // Navigate to the camera
        onView(withId(R.id.navigation_camera)).perform(click());
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            System.out.println(e);
        }
       // Perform click on camera button
        onView(withId(R.id.camera_button)).perform(click());
        // Checks whether the fragment has changed to the 'photo preview' page
        //  Does this by checking if a button that is on the 'photo preview' page is displayed
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            System.out.println(e);
        }
        onView(withId(R.id.retake_button)).check(matches(isDisplayed()));

    }

    @Test
    public void retakePhotoTest(){
        // Method tests the photo retaking functionality of the application

        // Navigate to the camera
        onView(withId(R.id.navigation_camera)).perform(click());
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            System.out.println(e);
        }
        // Perform click on camera button
        onView(withId(R.id.camera_button)).perform(click());
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            System.out.println(e);
        }
        // Checks whether the fragment has changed to the 'photo preview' page
        //  Does this by checking if a button that is on the 'photo preview' page is displayed
        onView(withId(R.id.retake_button)).check(matches(isDisplayed()));

        // Clicks on the retake button
        onView(withId(R.id.retake_button)).perform(click());

        // Checks whether the view has changed back to the camera
        onView(withId(R.id.navigation_camera)).check(matches(isDisplayed()));


    }

    @Test
    public void savePhotoTest(){
        // Method tests the photo saving functionality of the app


        // Navigate to the camera
        onView(withId(R.id.navigation_camera)).perform(click());
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            System.out.println(e);
        }
        // Perform click on camera button
        onView(withId(R.id.camera_button)).perform(click());
        // Checks whether the fragment has changed to the 'photo preview' page
        //  Does this by checking if a button that is on the 'photo preview' page is displayed
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            System.out.println(e);
        }
        onView(withId(R.id.retake_button)).check(matches(isDisplayed()));


        // Clicks the save button
        onView(withId(R.id.save_button)).check(matches(isDisplayed())).perform(click());

        // Once the image is saved the app should return to the camera page
        // Checks whether the page is now the camera page
        onView(withId(R.id.navigation_camera)).check(matches(isDisplayed()));



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

        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            System.out.println(e);
        }

        onView(withId(R.id.add_button)).perform(click());

        // Checks that photo is added to the imageView of the imagebutton that was pressed on
        onView(withId(R.id.addImage1)).perform(click());

        onView(withText("Saved Photos")).check(matches(isDisplayed()));

        // Code for new ViewAction() from https://stackoverflow.com/questions/28834579/click-on-not-fully-visible-imagebutton-with-espresso
        // Used to ensure that the image is clicked on
        onView(withTagValue(is(0))).perform(new ViewAction() {
            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isEnabled();
            }

            @Override
            public void perform(UiController uiController, View view) {
                view.performClick();
            }
        });

        onView(withId(R.id.addImage1)).check(matches(isDisplayed()));


    }


    @Test
    public void deletePhotoTest(){
        // Take a new photo

        onView(withId(R.id.navigation_camera)).perform(click());
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            System.out.println(e);
        }
        // Perform click on camera button
        onView(withId(R.id.camera_button)).perform(click());

        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            System.out.println(e);
        }
        onView(withId(R.id.save_button)).check(matches(isDisplayed())).perform(click());

        onView(withId(R.id.photos_button)).perform(click());

        onView(withTagValue(is(0))).perform(new ViewAction() {
            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public Matcher<View> getConstraints() {
               return ViewMatchers.isEnabled();
            }

            @Override
            public void perform(UiController uiController, View view) {
                view.performLongClick();
            }
        });

        onView(withText("Delete Photo")).inRoot(isDialog()).check(matches(isDisplayed()));

        onView(withText("Yes")).perform(click());

        onView(withTagValue(is(0))).check(doesNotExist());
    }

}
