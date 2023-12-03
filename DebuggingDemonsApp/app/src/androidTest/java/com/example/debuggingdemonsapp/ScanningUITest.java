package com.example.debuggingdemonsapp;

import android.Manifest;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ScanningUITest {
    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule = new ActivityScenarioRule<>(LoginActivity.class);
    // The following rule is from https://stackoverflow.com/questions/63837978/how-do-i-grant-permission-in-espresso
    @Rule
    public GrantPermissionRule permissionCamera = GrantPermissionRule.grant(Manifest.permission.CAMERA);

    @Test
    public void scanButtonTest(){

    }

    @Test
    public void imageScanTest(){

    }
}
