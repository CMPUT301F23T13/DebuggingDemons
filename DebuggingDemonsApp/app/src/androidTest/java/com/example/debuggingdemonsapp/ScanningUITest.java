package com.example.debuggingdemonsapp;

import android.Manifest;
import android.view.View;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;
import com.example.debuggingdemonsapp.ui.scanning.Scanner;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class ScanningUITest {
    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule = new ActivityScenarioRule<>(LoginActivity.class);
    // The following rule is from https://stackoverflow.com/questions/63837978/how-do-i-grant-permission-in-espresso


    public ActivityScenarioRule<MainActivity> mainActivityActivityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule permissionCamera = GrantPermissionRule.grant(Manifest.permission.CAMERA);

    Scanner imageScanner = new Scanner();
    @Test
    public void imageScanTest() {
        imageScanner.initializeScanner();
        // 'bonobo' is an existing username in the databasee
        onView(withId(R.id.usernameEditText)).perform(typeText("bonobo"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        // After the message is shown, we expect to navigate to the MainActivity
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Navigate to the camera
        onView(withId(R.id.navigation_camera)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // Perform click on camera button
        onView(withId(R.id.camera_button)).perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //Checks that the scan button is on current view (means we are in photoPreview view)
        onView(withId(R.id.scan_button)).check(matches(isDisplayed()));

        onView(withId(R.id.scan_button)).perform(click());

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // Checks that the 'Barcode(s) Found' dialog opens
        onView(withText("Barcode(s) Found")).inRoot(isDialog())
                .check(matches(isDisplayed()));

        // Dialog is closed by clicking 'ok'
        onView(withText("OK")).perform(click());

    }

    @Test
    public void autofillSerialNumberTest(){
        // This test is used to check that a saved photo can be used to autofill the serial number of an item
        // 'bonobo' is an existing username in the databasee
        onView(withId(R.id.usernameEditText)).perform(typeText("bonobo"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        // After the message is shown, we expect to navigate to the MainActivity
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Navigate to the inventory and add a new item
        onView(withId(R.id.navigation_inventory)).perform(click());

        onView(withId(R.id.add_button)).check(matches(isDisplayed()));
        onView(withId(R.id.add_button)).perform(click());


        // Check that the serial number textbox has no text
        onView(withId(R.id.editTextSerialNumber)).check(matches(withText("")));

        onView(withId(R.id.addImage1)).check(matches(isDisplayed()));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        onView(withId(R.id.addImage1)).perform(click());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        onView(withId(R.id.photoContainer)).check(matches(isDisplayed()));

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

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // Checks that the serial number textbox has taken the serial number from the image
        onView(withId(R.id.editTextSerialNumber)).check(matches(withText("AVATARO00B")));

    }
}
