package com.example.debuggingdemonsapp;


import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.filters.LargeTest;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule
            = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void testComponentsAreDisplayed() {
        // Check if the username edit text is displayed
        onView(withId(R.id.usernameEditText)).check(ViewAssertions.matches(isDisplayed()));

        // Check if the login button is displayed
        onView(withId(R.id.loginButton)).check(ViewAssertions.matches(isDisplayed()));

        // Check if the register button is displayed
        onView(withId(R.id.registerButton)).check(ViewAssertions.matches(isDisplayed()));

    }

    @Test
    public void testRegisterButton_withEmptyUsername() {
        // input the empty username
        onView(withId(R.id.usernameEditText)).perform(typeText(""), closeSoftKeyboard());
        // click register button
        onView(withId(R.id.registerButton)).perform(click());
        // We expect to see a Username cannot be empty message
        onView(withText("Username cannot be empty"))
                .inRoot(isDialog())
                .check(ViewAssertions.matches(isDisplayed()));

    }

    @Test
    public void testLoginButton_withEmptyUsername() {
        // input the empty username
        onView(withId(R.id.usernameEditText)).perform(typeText(""), closeSoftKeyboard());
        // click login button
        onView(withId(R.id.loginButton)).perform(click());
        // We expect to see a Username cannot be empty message
        onView(withText("Username cannot be empty"))
                .inRoot(isDialog())
                .check(ViewAssertions.matches(isDisplayed()));

    }

    @Test
    public void testRegisterButton_withValidUsername() {
        // Assume "newuser" is not already in the database for this test
        onView(withId(R.id.usernameEditText)).perform(typeText("newuser"), closeSoftKeyboard());
        onView(withId(R.id.registerButton)).perform(click());
        // We expect to see a successful registration message
        onView(withText("Register Successfully"))
                .inRoot(isDialog())
                .check(ViewAssertions.matches(isDisplayed()));
        // After the message is shown, we expect to navigate to the MainActivity
        // This may need to wait for the navigation to happen, potentially with an IdlingResource
    }

    @Test
    public void testLoginButton_withValidUsername() {
        // Assume "newuser" is already in the database for this test
        onView(withId(R.id.usernameEditText)).perform(typeText("newuser"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
        // We expect to see a successful login message
        onView(withText("Logged in successfully"))
                .inRoot(isDialog())
                .check(ViewAssertions.matches(isDisplayed()));
        // Also, expect navigation to MainActivity after successful login
    }


    @Test
    public void testRegisterButton_withExistUsername() {
        // Assume "newuser" is already in the database for this test
        onView(withId(R.id.usernameEditText)).perform(typeText("newuser"), closeSoftKeyboard());
        onView(withId(R.id.registerButton)).perform(click());
        // We expect to see a user already exits message
        onView(withText("Username already exists"))
                .inRoot(isDialog())
                .check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testLoginButton_withNonExistUsername() {
        // Assume "bonobo" is not in the database for this test
        onView(withId(R.id.usernameEditText)).perform(typeText("bonobo"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
        // We expect to see a username does not exist message
        onView(withText("Username does not exist"))
                .inRoot(isDialog())
                .check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testRegisterButton_withValidUsername_navigatesToMainActivity() {
        // Type the new username
        onView(withId(R.id.usernameEditText)).perform(typeText("newuser"), closeSoftKeyboard());

        // Click the register button
        onView(withId(R.id.registerButton)).perform(click());

        // Assuming the AlertDialog shows up and you need to click "OK"
        onView(withText(android.R.string.ok))
                .inRoot(isDialog()) // Only if you are expecting a dialog to show up
                .perform(click());

        // Add a delay or IdlingResource here if there is an asynchronous operation

        // Check that the MainActivity is displayed
        onView(withId(R.id.container)) // Use an actual ID from MainActivity
                .check(ViewAssertions.matches(isDisplayed()));
    }




}

