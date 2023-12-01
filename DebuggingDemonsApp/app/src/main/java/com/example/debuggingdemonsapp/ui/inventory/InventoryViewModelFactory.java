package com.example.debuggingdemonsapp.ui.inventory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import org.jetbrains.annotations.NotNull;

/**
 * This class implements ViewModelProvider.Factory and is used to be able to call the InventoryViewModel with an argument
 */
public class InventoryViewModelFactory implements ViewModelProvider.Factory {

    // This user string corresponds to the current_user which will be passed to the view model
    private String user;

    public InventoryViewModelFactory(String user){
        this.user = user;
    }

    @NotNull
    @Override
    public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
        // Creates and returns the InventoryViewModel with the current user passed in
        return (T) new InventoryViewModel(user);
    }
}
