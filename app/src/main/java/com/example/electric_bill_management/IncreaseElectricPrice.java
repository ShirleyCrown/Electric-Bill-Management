package com.example.electric_bill_management;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class IncreaseElectricPrice extends AppCompatActivity {
    private TextInputEditText priceUpdate;
    private Spinner spinner;
    private ArrayList<String> userType = new ArrayList<>();
    private String selectedItem = "None"; // Initialize with default value
    private Button submit;
    private ImageButton backButton;
    private int type, electricPrice;
    private TextView textPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_increase_electric_price);

        priceUpdate = findViewById(R.id.consumptionUpdate);
        textPrice = findViewById(R.id.textPrice);
        textPrice.setText(""); // Ensure textPrice is cleared on start

        spinner = findViewById(R.id.userSpinner);
        submit = findViewById(R.id.button);
        backButton = findViewById(R.id.backButton2);

        DatabaseHelper db = new DatabaseHelper(this);

        // Set up the Spinner with user types
        userType.add("None");
        userType.add("Private");
        userType.add("Business");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, userType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItem = adapterView.getItemAtPosition(i).toString();

                if (!selectedItem.equals("None")) {
                    type = selectedItem.equals("Private") ? 1 : 2;
                    electricPrice = db.getPriceByType(type);

                    if (priceUpdate.length() == 0) {
                        textPrice.setText(String.valueOf(electricPrice));
                    } else {
                        updatePriceDisplay(electricPrice);
                    }
                } else {
                    textPrice.setText(""); // Clear text when "None" is selected
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // No action needed here
            }
        });

        priceUpdate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No action needed before text changes
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!selectedItem.equals("None")) {
                    type = selectedItem.equals("Private") ? 1 : 2;
                    electricPrice = db.getPriceByType(type);
                    updatePriceDisplay(electricPrice);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No action needed after text changes
            }
        });

        // Back button to navigate to the main menu
        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(IncreaseElectricPrice.this, MainMenu.class);
            startActivity(intent);
        });

        // Submit button action
        submit.setOnClickListener(view -> {
            if (priceUpdate.getText().toString().isEmpty()) {
                Toast.makeText(IncreaseElectricPrice.this, "Please insert increasing price!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedItem.equals("None")) {
                Toast.makeText(IncreaseElectricPrice.this, "Please choose user type!", Toast.LENGTH_SHORT).show();
                return;
            }
            type = selectedItem.equals("Private") ? 1 : 2;
            int increaseAmount = Integer.parseInt(priceUpdate.getText().toString());
            db.increaseElectricUnitPrice(type, increaseAmount);
            Toast.makeText(IncreaseElectricPrice.this, "Update success!", Toast.LENGTH_SHORT).show();
            textPrice.setText(String.valueOf(db.getPriceByType(type)));
            priceUpdate.setText("");
        });
    }

    // Helper method to update the displayed price
    private void updatePriceDisplay(int basePrice) {
        try {
            int inputValue = Integer.parseInt(priceUpdate.getText().toString());
            int newPrice = inputValue + basePrice;
            textPrice.setText(String.valueOf(newPrice));
        } catch (NumberFormatException e) {
            textPrice.setText(String.valueOf(basePrice));
        }
    }
}
