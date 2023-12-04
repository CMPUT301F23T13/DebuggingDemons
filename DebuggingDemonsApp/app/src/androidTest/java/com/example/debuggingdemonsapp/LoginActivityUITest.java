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
        // Verify that the username input field is visible
        onView(withId(R.id.usernameEditText)).check(ViewAssertions.matches(isDisplayed()));

        // Verify that the login button is visible
        onView(withId(R.id.loginButton)).check(ViewAssertions.matches(isDisplayed()));

        // Verify that the register button is visible
        onView(withId(R.id.registerButton)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void test2_RegisterButton_withEmptyUsername() {
        // Enter an empty string as the username
        onView(withId(R.id.usernameEditText)).perform(typeText(""), closeSoftKeyboard());
        // Click the register button
        onView(withId(R.id.registerButton)).perform(click());
        // Expect an error message stating that the username cannot be empty
        onView(withText("Username cannot be empty"))
                .inRoot(isDialog())
                .check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void test3_LoginButton_withEmptyUsername() {
        // Enter an empty string as the username
        onView(withId(R.id.usernameEditText)).perform(typeText(""), closeSoftKeyboard());
        // Click the login button
        onView(withId(R.id.loginButton)).perform(click());
        // Expect an error message stating that the username cannot be empty
        onView(withText("Username cannot be empty"))
                .inRoot(isDialog())
                .check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void test4_RegisterButton_withValidUsername() throws InterruptedException {
        // Enter a valid username (assuming "user" is not already registered)
        onView(withId(R.id.usernameEditText)).perform(typeText("user"), closeSoftKeyboard());
        onView(withId(R.id.registerButton)).perform(click());
        // Expect a successful registration message
        onView(withText("Register Successfully"))
                .inRoot(isDialog())
                .check(ViewAssertions.matches(isDisplayed()));
        // Wait for 3 seconds before checking if MainActivity is displayed
        Thread.sleep(3000);
        // Verify that MainActivity is displayed
        onView(withId(R.id.container)) // Replace with a valid ID from MainActivity
                .check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void test5_LoginButton_withValidUsername() {
        // Enter a valid username (assuming "user" is already registered)
        onView(withId(R.id.usernameEditText)).perform(typeText("user"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
        // Expect a successful login message
        onView(withText("Logged in successfully"))
                .inRoot(isDialog())
                .check(ViewAssertions.matches(isDisplayed()));
        // Wait for 3 seconds before checking if MainActivity is displayed
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Verify that MainActivity is displayed
        onView(withId(R.id.container)) // Replace with a valid ID from MainActivity
                .check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void test6_RegisterButton_withExistUsername() {
        // Enter a username that is already registered (assuming "user" is registered)
        onView(withId(R.id.usernameEditText)).perform(typeText("user"), closeSoftKeyboard());
        onView(withId(R.id.registerButton)).perform(click());
        // Expect a message indicating that the username already exists
        onView(withText("Username already exists"))
                .inRoot(isDialog())
                .check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void test7_LoginButton_withNonExistUsername() {
        // Enter a username that is not in the database (assuming "nouser" is not registered)
        onView(withId(R.id.usernameEditText)).perform(typeText("nouser"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
        // Expect a message indicating that the username does not exist
        onView(withText("Username does not exist"))
                .inRoot(isDialog())
                .check(ViewAssertions.matches(isDisplayed()));
    }

}


