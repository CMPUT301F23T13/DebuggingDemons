package com.example.debuggingdemonsapp;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class PhotographTest {
    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new
            ActivityScenarioRule<MainActivity>(MainActivity.class);
    @Test
    public void TakePhotoTest(){

    }

    @Test
    public void RetakePhotoTest(){

    }

    @Test
    public void SavePhotoTest(){

    }
}
