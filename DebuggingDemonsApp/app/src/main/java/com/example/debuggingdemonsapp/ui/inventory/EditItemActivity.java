package com.example.debuggingdemonsapp.ui.inventory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.debuggingdemonsapp.R;

public class EditItemActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_add_item);
        TextView DoP = findViewById(R.id.editTextDateOfPurchase);
        TextView Description = findViewById(R.id.editTextDescription);
        TextView Make = findViewById(R.id.editTextMake);
        TextView Model = findViewById(R.id.editTextModel);
        TextView SerialNumber = findViewById(R.id.editTextSerialNumber);
        TextView EstimatedValue = findViewById(R.id.editTextEstimatedValue);
        TextView Comment = findViewById(R.id.editTextComment);
        Button confirmButton = findViewById(R.id.buttonDone);

        Intent intent = getIntent();
        String doP = intent.getStringExtra("");
        String description = intent.getStringExtra("");
        String make = intent.getStringExtra("");
        String model = intent.getStringExtra("");
        String serialNumber = intent.getStringExtra("");
        String estimatedValue = intent.getStringExtra("");
        String comment = intent.getStringExtra("");

        DoP.setText(doP);
        Description.setText(description);
        Make.setText(make);
        Model.setText(model);
        SerialNumber.setText(serialNumber);
        EstimatedValue.setText(estimatedValue);
        Comment.setText(comment);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




    }
}
