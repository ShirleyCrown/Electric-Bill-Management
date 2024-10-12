package com.example.electric_bill_management;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {
    private TextInputEditText username,password;
    private Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        username = findViewById(R.id.search);
        password = findViewById(R.id.password);
        signInButton = findViewById(R.id.signInButton);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = username.getText().toString();
                String pass = password.getText().toString();

                if(name.isEmpty() && pass.isEmpty()){
                    Toast.makeText(Login.this,"Please enter Username and Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(name.isEmpty()){
                    Toast.makeText(Login.this,"Please enter username!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pass.isEmpty()){
                    Toast.makeText(Login.this,"Please enter password!",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(name.equals("user") && pass.equals("user")){
                    Intent intent = new Intent(Login.this, MainMenu.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(Login.this, "Wrong Login Info",Toast.LENGTH_SHORT).show();
                    username.setText("");
                    password.setText("");
                }

            }
        });

    }
}