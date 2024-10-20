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

public class UpdateCustomer extends AppCompatActivity {
    private TextInputEditText name, address, consumption, price;
    DatePicker datePicker;
    Spinner spinner;
    Button update;
    ImageButton backToDetails;
    private ArrayList<String> userType = new ArrayList<>();
    private String selectedItem;
    private Customer[] customers;
    private int pos;
    private Customer customerObj;
    private DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_customer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get thong tin customer tu database va hien thi len man hinh
        customers = db.getAllCustomers().toArray(new Customer[0]);
        pos = getIntent().getIntExtra("pos",0);
        customerObj = customers[pos];

        name = findViewById(R.id.name);
        name.setText(customerObj.getName());

        address = findViewById(R.id.address);
        address.setText(customerObj.getAddress());

        consumption = findViewById(R.id.consumptionUpdate);
        consumption.setText(String.valueOf(customerObj.getUsedNumElectric()));

        datePicker = findViewById(R.id.datePickerUpdate);
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

        int month = customerObj.getYyyymm()%100;
        int year = customerObj.getYyyymm()/100;;
        datePicker.updateDate(year, month - 1, 1);

        spinner = findViewById(R.id.userSpinner);
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
        spinner.setSelection(adapter.getPosition(customerObj.getElecUserTypeId()==1?"Private":"Business"));

        // Di chuyen ve man hinh CustomerDetails
        backToDetails = findViewById(R.id.backToDetails);
        backToDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateCustomer.this, CustomerDetails.class);

                intent.putExtra("update",pos);
                startActivity(intent);
            }
        });

        update = findViewById(R.id.updateButton);
        // Xu ly update thong tin Customer vao database dua tren tri input tai man hinh
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name1 = name.getText().toString();
                String addr = address.getText().toString();
                String amount = consumption.getText().toString();
                if (name1.isEmpty() || addr.isEmpty() || amount.isEmpty()){ // truong hop chua nhap ten, dia chi, tien thi khong cho update
                    Toast.makeText(UpdateCustomer.this, "Data missing", Toast.LENGTH_SHORT).show();
                    return;
                }

                customerObj.setName(name1);

                int selectedMonth = datePicker.getMonth() + 1;
                int selectedYear = datePicker.getYear();
                String month = selectedYear + (selectedMonth>=1&&selectedMonth<10?"0":"") + selectedMonth;
                customerObj.setYyyymm(Integer.parseInt(month));

                customerObj.setAddress(addr);

                customerObj.setUsedNumElectric(Double.parseDouble(amount));

                int type = spinner.getSelectedItem().toString().equals("Private")?1:2;
                customerObj.setElecUserTypeId(type);

                db.updateCustomerById(customerObj);

                Toast.makeText(UpdateCustomer.this, "Customer Updated!", Toast.LENGTH_SHORT).show();

            }
        });

    }
}