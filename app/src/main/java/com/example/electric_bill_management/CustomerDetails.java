package com.example.electric_bill_management;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.electric_bill_management.Customer_RecyclerView.CustomerList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class CustomerDetails extends AppCompatActivity {
    private TextView id, name, month, address, amount, userType, price;
    int pos;
    ImageButton first, last, previous, next, back;
    DatabaseHelper db = new DatabaseHelper(this);
    Button update;
    Customer[] customers;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        customers = db.getAllCustomers().toArray(new Customer[0]);
        if(getIntent().hasExtra("pos")){
            pos = getIntent().getIntExtra("pos",0);
        }else{
            pos = getIntent().getIntExtra("update",0);
        }

        displayData(customers,pos);

        back = findViewById(R.id.backToList);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerDetails.this, CustomerList.class);
                startActivity(intent);
            }
        });

        first = findViewById(R.id.firstPage);
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pos = 0;
                displayData(customers,pos);
            }
        });

        last = findViewById(R.id.lastPage);
        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pos = customers.length - 1;
                displayData(customers,pos);
            }
        });

        previous = findViewById(R.id.previousPage);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pos == 0){
                    Toast.makeText(CustomerDetails.this, "This is the first page!", Toast.LENGTH_SHORT).show();
                    return;
                }

                pos -= 1;
                displayData(customers,pos);
            }
        });

        next = findViewById(R.id.nextPage);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pos == customers.length - 1){
                    Toast.makeText(CustomerDetails.this, "This is the last page!", Toast.LENGTH_SHORT).show();
                    return;
                }

                pos+=1;
                displayData(customers,pos);
            }
        });

        update = findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerDetails.this, UpdateCustomer.class);

                intent.putExtra("pos", pos);

                startActivity(intent);
            }
        });

    }

    public void displayData(Customer[] customers, int pos){
        id = findViewById(R.id.id);
        name = findViewById(R.id.name);
        month = findViewById(R.id.month);
        address = findViewById(R.id.address);
        amount = findViewById(R.id.electricUsedAmount);
        userType = findViewById(R.id.userType);
        price = findViewById(R.id.price);

        id.setText(String.valueOf(customers[pos].getId()));
        name.setText(customers[pos].getName());

        int monthNum = customers[pos].getYyyymm()%100;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, monthNum - 1); // Tháng bắt đầu từ 0
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
        month.setText(sdf.format(calendar.getTime())+" "+customers[0].getYyyymm()/100);

        String res = customers[pos].getUsedNumElectric()+"";
        String type = customers[pos].getElecUserTypeId() == 1?"Private":"Business";
        int unit_price = db.getPriceByType(customers[pos].getElecUserTypeId());

        sharedPreferences = getSharedPreferences("app_settings", MODE_PRIVATE);
        boolean showInfo = sharedPreferences.getBoolean("show_details", true);

        if (!showInfo) {
            LinearLayout addressLayout = (LinearLayout) findViewById(R.id.addressLayout);
            LinearLayout amountLayout = (LinearLayout) findViewById(R.id.amountLayout);
            LinearLayout userTypeLayout = (LinearLayout) findViewById(R.id.userTypeLayout);
            LinearLayout priceLayout = (LinearLayout) findViewById(R.id.priceLayout);

            addressLayout.setVisibility(View.GONE);
            amountLayout.setVisibility(View.GONE);
            userTypeLayout.setVisibility(View.GONE);
            priceLayout.setVisibility(View.GONE);
        } else {
            address.setText(customers[pos].getAddress());
            amount.setText(res);
            userType.setText(type);
            price.setText(String.valueOf(customers[pos].getUsedNumElectric()*unit_price));
        }
    }
}