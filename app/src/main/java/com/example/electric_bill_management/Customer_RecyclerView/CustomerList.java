package com.example.electric_bill_management.Customer_RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.electric_bill_management.CustomerDetails;
import com.example.electric_bill_management.DatabaseHelper;
import com.example.electric_bill_management.MainMenu;
import com.example.electric_bill_management.R;

import java.util.ArrayList;

public class CustomerList extends AppCompatActivity implements RecyclerViewInterface{

    ArrayList<CustomerModel> customerModels = new ArrayList<>();
    int img = R.drawable.customer_avatar;
    RecyclerView recyclerView;
    ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.myRecyclerView);

        // Add list customer tu database va set tri vao recyclerView
        setUpCustomerModels();
        Customer_RecyclerViewAdapter adapter = new Customer_RecyclerViewAdapter(this,customerModels, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        imageButton = findViewById(R.id.imageBackButton);

        // Xu ly tro ve Man hinh Menu khi nhan button back
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerList.this, MainMenu.class);
                startActivity(intent);
            }
        });

    }

    DatabaseHelper db = new DatabaseHelper(this);
    private void setUpCustomerModels(){
        String[] customerNames = db.getAllCustomerNames().toArray(new String[0]);
        String[] customerAddresses = db.getAllCustomerAddresses().toArray(new String[0]);

        for (int i = 0; i < customerNames.length; i++) {
            customerModels.add(new CustomerModel(customerNames[i],customerAddresses[i],img));
        }
    }

    // Su kien click vao thong tin customer tu recyclerView de di chuyen den man hinh chi tiet customer
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(CustomerList.this, CustomerDetails.class);

        intent.putExtra("pos",position);

        startActivity(intent);
    }
}