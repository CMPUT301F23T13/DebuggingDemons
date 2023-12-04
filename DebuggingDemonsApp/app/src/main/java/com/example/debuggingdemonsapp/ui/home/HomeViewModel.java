package com.example.debuggingdemonsapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();

        mText.setValue("Welcome back!");
    }
    public HomeViewModel(String current_user){
        mText = new MutableLiveData<>();

        mText.setValue("Welcome back " + current_user + "!");
    }
    public LiveData<String> getText() {
        return mText;
    }
}