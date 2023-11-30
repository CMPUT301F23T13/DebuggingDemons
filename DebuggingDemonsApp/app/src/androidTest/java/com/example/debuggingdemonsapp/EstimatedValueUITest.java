package com.example.debuggingdemonsapp;


import android.view.View;
import android.widget.TextView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;


import org.hamcrest.Matcher;

import java.util.regex.Pattern;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class EstimatedValueUITest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);


    private static float extractValueFromText(final Matcher<View> matcher) {
        final String[] stringHolder = { null };
        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "getting text from a TextView";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView tv = (TextView)view; // Save, because of check in getConstraints()
                stringHolder[0] = tv.getText().toString();
            }
        });

        String text = stringHolder[0];
        // Extract the numeric part from the text
        if (text != null && !text.isEmpty()) {
            Pattern pattern = Pattern.compile("(\\d+(\\.\\d+)?)");
            java.util.regex.Matcher regexMatcher = pattern.matcher(text);
            if (regexMatcher.find()) {
                return Float.parseFloat(regexMatcher.group(1));
            }
        }
        return 0;
    }





    @Test
    public void testTotalEstimatedValueTextView() {

        // Navigate to inventory
        onView(withId(R.id.navigation_inventory)).perform(click());

        // Check if the TextView is displayed
        onView(withId(R.id.total_estimated_value)).check(matches(isDisplayed()));

        // Additional tests can be added here, like checking text color or background
    }

    @Test
    public void testValueUpdateAfterAddingItem() throws InterruptedException {

        // Navigate to inventory
        onView(withId(R.id.navigation_inventory)).perform(click());


//        // Fetch the initial value
//        String initialValueString = getText(withId(R.id.total_estimated_value));
//        float initialValue = initialValueString.isEmpty() ? 0 : Float.parseFloat(initialValueString);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Fetch the initial value
        float initialValue = extractValueFromText(withId(R.id.total_estimated_value));



        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        // Interact with UI to add an item
        // For example, click the 'Add' button
        onView(withId(R.id.add_button)).perform(click());

        // Fill in the details of the item, including the value
        // Assume there's an EditText to enter the value and a button to submit
        onView(withId(R.id.editTextDescription)).perform(typeText("estimate"), closeSoftKeyboard());
        onView(withId(R.id.editTextEstimatedValue)).perform(typeText("100"), closeSoftKeyboard());
        onView(withId(R.id.button_ok)).perform(click());


        // Calculate the expected updated value
        float addedValue = 100; // This is the value you added
        float expectedValue = initialValue + addedValue;

        // Format the expected value to match the actual text format in the TextView
        String expectedText = String.format("Total Estimated Value: %.1f", expectedValue);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }



        // Check if the TextView reflects the correct updated value
        onView(withId(R.id.total_estimated_value)).check(matches(withText(expectedText)));
    }

}
