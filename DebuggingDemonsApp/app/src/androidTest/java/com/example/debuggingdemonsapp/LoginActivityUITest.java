package com.example.debuggingdemonsapp;


import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginActivityUITest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule
            = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void test1_ComponentsAreDisplayed() {
        // Check if the username edit text is displayed
        onView(withId(R.id.usernameEditText)).check(ViewAssertions.matches(isDisplayed()));

        // Check if the login button is displayed
        onView(withId(R.id.loginButton)).check(ViewAssertions.matches(isDisplayed()));

        // Check if the register button is displayed
        onView(withId(R.id.registerButton)).check(ViewAssertions.matches(isDisplayed()));

    }

    @Test
    public void test2_RegisterButton_withEmptyUsername() {
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
    public void test3_LoginButton_withEmptyUsername() {
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
    public void test4_RegisterButton_withValidUsername() throws InterruptedException {
        // Assume "newuser" is not already in the database for this test
        onView(withId(R.id.usernameEditText)).perform(typeText("newuser"), closeSoftKeyboard());
        onView(withId(R.id.registerButton)).perform(click());
        // We expect to see a successful registration message
        onView(withText("Register Successfully"))
                .inRoot(isDialog())
                .check(ViewAssertions.matches(isDisplayed()));
        // After the message is shown, we expect to navigate to the MainActivity
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Check that the MainActivity is displayed
        onView(withId(R.id.container)) // Use an actual ID from MainActivity
                .check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void test5_LoginButton_withValidUsername() {
        // Assume "newuser" is already in the database for this test
        onView(withId(R.id.usernameEditText)).perform(typeText("newuser"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
        // We expect to see a successful login message
        onView(withText("Logged in successfully"))
                .inRoot(isDialog())
                .check(ViewAssertions.matches(isDisplayed()));
        // Also, expect navigation to MainActivity after successful login
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Check that the MainActivity is displayed
        onView(withId(R.id.container)) // Use an actual ID from MainActivity
                .check(ViewAssertions.matches(isDisplayed()));
    }


    @Test
    public void test6_RegisterButton_withExistUsername() {
        // Assume "newuser" is already in the database for this test
        onView(withId(R.id.usernameEditText)).perform(typeText("newuser"), closeSoftKeyboard());
        onView(withId(R.id.registerButton)).perform(click());
        // We expect to see a user already exits message
        onView(withText("Username already exists"))
                .inRoot(isDialog())
                .check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void test7_LoginButton_withNonExistUsername() {
        // Assume "bonobo" is not in the database for this test
        onView(withId(R.id.usernameEditText)).perform(typeText("nouser"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
        // We expect to see a username does not exist message
        onView(withText("Username does not exist"))
                .inRoot(isDialog())
                .check(ViewAssertions.matches(isDisplayed()));
    }

}

