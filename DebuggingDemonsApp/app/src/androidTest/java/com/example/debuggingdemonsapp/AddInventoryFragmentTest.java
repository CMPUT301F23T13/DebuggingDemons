package com.example.debuggingdemonsapp;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.MediumTest;
import com.example.debuggingdemonsapp.ui.inventory.AddInventoryFragment;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class AddInventoryFragmentTest {

    private FirebaseFirestore firestore;

    @Before
    public void setUp() {
        // Point to the Firebase Emulator host and port
        System.setProperty("firebase.emulators.http.enabled", "true"); // Enable Firestore Emulator
        firestore = FirebaseFirestore.getInstance();
        firestore.useEmulator("10.0.2.2", 8080); // Use 10.0.2.2 for the Android emulator's loopback address
    }

    @Test
    public void addButton_addsItem() throws InterruptedException {
        // Create a new CountDownLatch to wait for the Firestore query to complete
        CountDownLatch latch = new CountDownLatch(1);

        // Perform the Firestore query
        firestore.collection("items").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null) {
                    int previousSize = querySnapshot.size(); // Get the previous size

                    firestore.collection("items").add(new DummyItem("Item Name", "Description"))
                            .addOnCompleteListener(addTask -> {
                                if (addTask.isSuccessful()) {
                                    // Query the Firestore collection again after adding the item
                                    firestore.collection("items").get().addOnCompleteListener(newTask -> {
                                        if (newTask.isSuccessful()) {
                                            QuerySnapshot newQuerySnapshot = newTask.getResult();
                                            if (newQuerySnapshot != null) {
                                                int expectedSize = previousSize + 1;
                                                assertEquals(expectedSize, newQuerySnapshot.size());
                                                // Signal that the query is complete
                                                latch.countDown();
                                            }
                                        }
                                    });
                                }
                            });
                }
            }
        });

        // Wait for the Firestore query to complete or timeout after 10 seconds
        assertTrue(latch.await(10, TimeUnit.SECONDS));
    }

    // Define a dummy item class for testing purposes
    private static class DummyItem {
        public String name;
        public String description;

        public DummyItem(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }
}
