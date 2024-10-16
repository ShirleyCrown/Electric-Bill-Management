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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class IncreaseElectricPrice extends AppCompatActivity {
    private TextInputEditText priceUpdate;
    private Spinner spinner;
    private ArrayList<String> userType = new ArrayList<>();
    private String selectedItem;
    private Button submit;
    private ImageButton backButton;
    private int type, electricPrice;
    private TextView textPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_increase_electric_price);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        priceUpdate = findViewById(R.id.consumptionUpdate);

        submit = findViewById(R.id.button);

        backButton = findViewById(R.id.backButton2);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IncreaseElectricPrice.this, MainMenu.class);
                startActivity(intent);
            }
        });

        DatabaseHelper db = new DatabaseHelper(this);

        spinner = findViewById(R.id.userSpinner);
        userType.add("None");
        userType.add("Private");
        userType.add("Business");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, userType);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItem = adapterView.getItemAtPosition(i).toString();
                if (selectedItem.equals("None")) {
                    textPrice.setText("");
                }
                else {
                    type = selectedItem.equals("Private") ? 1 : 2;
                    electricPrice = db.getPriceByType(type);

                    if (priceUpdate.length() == 0) {
                        textPrice.setText(String.valueOf(electricPrice));
                        return;
                    }
                    try {
                        int inputValue = Integer.parseInt(priceUpdate.getText().toString());
                        int price = inputValue + electricPrice;
                        textPrice.setText(String.valueOf(price));
                    } catch (NumberFormatException e) {
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        textPrice = findViewById(R.id.textPrice);

        textPrice.setText("");

        priceUpdate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!selectedItem.equals("None")) {
                    type = selectedItem.equals("Private") ? 1 : 2;
                    electricPrice = db.getPriceByType(type);

                    if (charSequence.length() == 0) {
                        textPrice.setText(String.valueOf(electricPrice));
                        return;
                    }
                    try {
                        int inputValue = Integer.parseInt(charSequence.toString());
                        int price = inputValue + electricPrice;
                        textPrice.setText(String.valueOf(price));
                    } catch (NumberFormatException e) {
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (priceUpdate.getText().toString().isEmpty()) {
                    Toast.makeText(IncreaseElectricPrice.this, "Please insert increasing price!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (spinner.getSelectedItem().toString().equals("None")) {
                    Toast.makeText(IncreaseElectricPrice.this, "Please choose user type!", Toast.LENGTH_SHORT).show();
                    return;
                }
                type = spinner.getSelectedItem().toString().equals("Private") ? 1 : 2;
                db.increaseElectricUnitPrice(type,Integer.parseInt(priceUpdate.getText().toString()));
                Toast.makeText(IncreaseElectricPrice.this, "Update success!", Toast.LENGTH_SHORT).show();
                int price = db.getPriceByType(type);
                textPrice.setText(String.valueOf(price));
                priceUpdate.setText("");
            }
        });
    }
}