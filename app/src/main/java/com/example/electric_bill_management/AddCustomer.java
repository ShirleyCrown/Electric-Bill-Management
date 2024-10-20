package com.example.electric_bill_management;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;

public class AddCustomer extends AppCompatActivity {
    private TextInputEditText name, yyyymm, address, consumption;
    private Spinner spinner;
    private ArrayList<String> userType = new ArrayList<>();
    private String selectedItem;
    private Button addButton;
    private ImageButton backButton;
    private DatePicker datePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_customer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        name = findViewById(R.id.name);
        //yyyymm  = findViewById(R.id.yyyymm);
        address = findViewById(R.id.address);
        consumption = findViewById(R.id.consumptionUpdate);

        datePicker = findViewById(R.id.datePicker);
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        datePicker.init(currentYear, currentMonth, calendar.get(Calendar.DAY_OF_MONTH), null);
        int daySpinnerId = getResources().getIdentifier("day", "id", "android");
        if (daySpinnerId != 0) {
            View daySpinner = datePicker.findViewById(daySpinnerId);
            if (daySpinner != null) {
                daySpinner.setVisibility(View.GONE);
            }
        }
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        DatabaseHelper db =  new DatabaseHelper(this);

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spinner.getSelectedItem().toString().equals("None")){
                    Toast.makeText(AddCustomer.this,"Please choose user type", Toast.LENGTH_SHORT).show();
                    return;
                }
                String name1 = name.getText().toString();
                String addr = address.getText().toString();
                String amount = consumption.getText().toString();
                if (name1.isEmpty() || addr.isEmpty() || amount.isEmpty()){
                    Toast.makeText(AddCustomer.this, "Data missing", Toast.LENGTH_SHORT).show();
                    return;
                }

                int selectedMonth = datePicker.getMonth() + 1;
                int selectedYear = datePicker.getYear();
                String month = selectedYear + (selectedMonth>=1&&selectedMonth<10?"0":"") + selectedMonth;

                int type = spinner.getSelectedItem().toString().equals("Private")?1:2;
                Customer customer = new Customer(name1,Integer.parseInt(month), addr, Double.parseDouble(amount),type);
                db.addCustomer(customer); // Add customer vao Database
                Toast.makeText(AddCustomer.this, "Customer added!", Toast.LENGTH_SHORT).show();
            }
        });

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCustomer.this, MainMenu.class);
                startActivity(intent);
            }
        });
    }
}