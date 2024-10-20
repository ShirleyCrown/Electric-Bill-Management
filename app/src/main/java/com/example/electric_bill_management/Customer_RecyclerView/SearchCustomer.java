package com.example.electric_bill_management.Customer_RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.electric_bill_management.Customer;
import com.example.electric_bill_management.DatabaseHelper;
import com.example.electric_bill_management.MainMenu;
import com.example.electric_bill_management.R;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SearchCustomer extends AppCompatActivity {
    private Spinner spinner;
    TextInputEditText search;
    private Button searchButton;
    private ImageButton backButton4;
    private RecyclerView recyclerView;
    private DatabaseHelper databaseHelper;
    List<CustomerSearchModel> customerModels = new ArrayList<>();

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

        databaseHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerView);

        // Get tri tu database va set tri vao recyclerView
        setUpCustomerModels();
        CustomerSearchAdapter adapter = new CustomerSearchAdapter(this,customerModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        spinner = findViewById(R.id.spinner);
        ArrayList<String> criteria = new ArrayList<>();
        criteria.add("None");
        criteria.add("Name");
        criteria.add("Address");
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, criteria);
        adapterSpinner.setDropDownViewResource(android.R.layout.select_dialog_item);
        spinner.setAdapter(adapterSpinner);

        search = findViewById(R.id.search);

        searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = search.getText().toString();
                // Truong hop chua chon criteria thi thong bao ra man hinh
                if (spinner.getSelectedItem().toString().equals("None")){
                    Toast.makeText(SearchCustomer.this,"Please choose criteria", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(query.isEmpty()){
                    Toast.makeText(SearchCustomer.this,"Please insert name or address!",Toast.LENGTH_SHORT).show();
                    return;
                }
                // Tim kiem theo Name/Address tu spinner va tu khoa nhap trong textview
                adapter.filter(spinner.getSelectedItem().toString(), query);

            }
        });

        // Xu ly tro ve Man hinh Menu khi nhan button back
        backButton4 = findViewById(R.id.backButton1000);
        backButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchCustomer.this, MainMenu.class);
                startActivity(intent);
            }
        });
    }

    private void setUpCustomerModels(){
        List<Customer> customers = databaseHelper.getAllCustomers();

        // Loop list customer tu database va set tri vao list CustomerSearchModel
        for (int i = 0; i < customers.size(); i++) {
            int monthNum = customers.get(i).getYyyymm()%100;
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, monthNum - 1); // Tháng bắt đầu từ 0
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
            String month = String.valueOf(sdf.format(calendar.getTime())+" "+ customers.get(i).getYyyymm() / 100);
            String type = customers.get(i).getElecUserTypeId() == 1 ? "Private" : "Business";
            int unit_price = databaseHelper.getPriceByType(customers.get(i).getElecUserTypeId());
            customerModels.add(new CustomerSearchModel(
                    String.valueOf(customers.get(i).getId()),
                    customers.get(i).getName(),
                    month,
                    customers.get(i).getAddress(),
                    String.valueOf(customers.get(i).getUsedNumElectric()),
                    type,
                    String.valueOf(unit_price)));
        }
    }
}