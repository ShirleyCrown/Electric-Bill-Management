package com.example.electric_bill_management;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.electric_bill_management.Customer_RecyclerView.CustomerList;

public class MainMenu extends AppCompatActivity {
    private Button addCustomerButton, listButton, updatePriceButton, searchCustomerButton;
    private DatabaseHelper databaseHelper;

    private MediaPlayer mediaPlayer;
    private SharedPreferences sharedPreferences;
    private ImageView imageSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseHelper = new DatabaseHelper(this);
        databaseHelper.createDatabase();

        addCustomerButton = findViewById(R.id.addCustomerButton);
        listButton = findViewById(R.id.listCustomerButton);
        updatePriceButton = findViewById(R.id.updatePriceButton);
        searchCustomerButton = findViewById(R.id.searchCustomerButton);

        addCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, AddCustomer.class);
                startActivity(intent);
            }
        });
        searchCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, SearchCustomer.class);
                startActivity(intent);
            }
        });


        updatePriceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, IncreaseElectricPrice.class);
                startActivity(intent);
            }
        });

        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, CustomerList.class);
                startActivity(intent);
            }
        });

        imageSetting = findViewById(R.id.imageSetting);
        imageSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }
}