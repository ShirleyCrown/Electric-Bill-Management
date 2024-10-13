package com.example.electric_bill_management;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SearchCustomer extends AppCompatActivity {
    private Spinner spinner;
    TextInputEditText search;
    private Button searchButton;
    private ImageButton backButton4;
    private DatabaseHelper databaseHelper;
    private TextView id, name, month, address, amount, userType, price;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_customer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        spinner = findViewById(R.id.spinner);
        ArrayList<String> criteria = new ArrayList<>();
        criteria.add("None");
        criteria.add("Name");
        criteria.add("Address");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, criteria);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_item);
        spinner.setAdapter(adapter);

        search = findViewById(R.id.search);

        id = findViewById(R.id.id);
        name = findViewById(R.id.name);
        month = findViewById(R.id.month);
        address = findViewById(R.id.address);
        amount = findViewById(R.id.electricUsedAmount);
        userType = findViewById(R.id.userType);
        price = findViewById(R.id.price);

        databaseHelper = new DatabaseHelper(this);

        searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spinner.getSelectedItem().toString().equals("None")){
                    Toast.makeText(SearchCustomer.this,"Please choose criteria", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(search.getText().toString().isEmpty()){
                    Toast.makeText(SearchCustomer.this,"Please insert name or address!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(spinner.getSelectedItem().toString().equals("Name")){
                    List<Customer> customers = databaseHelper.getCustomersByName(search.getText().toString());
                    if (customers == null || customers.isEmpty()) {
                        Toast.makeText(SearchCustomer.this, "No customer found!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    id.setText(String.valueOf(customers.get(0).getId()));
                    name.setText(customers.get(0).getName());

                    int monthNum = customers.get(0).getYyyymm()%100;
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.MONTH, monthNum - 1); // Tháng bắt đầu từ 0
                    SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
                    month.setText(sdf.format(calendar.getTime())+" "+customers.get(0).getYyyymm()/100);

                    address.setText(customers.get(0).getAddress());
                    String res = customers.get(0).getUsedNumElectric()+"";
                    amount.setText(res);

                    String type = customers.get(0).getElecUserTypeId() == 1?"Private":"Business";
                    userType.setText(type);

                    int unit_price = databaseHelper.getPriceByType(customers.get(0).getElecUserTypeId());
                    price.setText(String.valueOf(customers.get(0).getUsedNumElectric()*unit_price));
                }
                if(spinner.getSelectedItem().toString().equals("Address")){
                    List<Customer> customers = databaseHelper.getCustomersByAddress(search.getText().toString());
                    if (customers == null || customers.isEmpty()) {
                        Toast.makeText(SearchCustomer.this, "No customer found!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    id.setText(String.valueOf(customers.get(0).getId()));
                    name.setText(customers.get(0).getName());

                    int monthNum = customers.get(0).getYyyymm()%100;
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.MONTH, monthNum - 1); // Tháng bắt đầu từ 0
                    SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
                    month.setText(sdf.format(calendar.getTime())+" "+customers.get(0).getYyyymm()/100);

                    address.setText(customers.get(0).getAddress());
                    String res = customers.get(0).getUsedNumElectric()+"";
                    amount.setText(res);
                    userType.setText(String.valueOf(customers.get(0).getElecUserTypeId()));

                    int unit_price = databaseHelper.getPriceByType(customers.get(0).getElecUserTypeId());
                    price.setText(String.valueOf(customers.get(0).getUsedNumElectric()*unit_price));
                }
            }
        });

        backButton4 = findViewById(R.id.backButton1000);
        backButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchCustomer.this, MainMenu.class);
                startActivity(intent);
            }
        });
    }
}