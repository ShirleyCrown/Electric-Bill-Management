package com.example.electric_bill_management;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingActivity extends AppCompatActivity {
    private Switch switchVisibility, switchMusic;
    private SharedPreferences sharedPreferences;
    private ImageButton buttonBack;

    private static final String PREFS_NAME = "app_settings";
    private static final String KEY_VISIBILITY = "show_details";
    private static final String KEY_MUSIC = "play_music";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);

        final MyApplication app = (MyApplication) getApplication();

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        switchVisibility = findViewById(R.id.switch_visibility);
        switchMusic = findViewById(R.id.switch_music);
        buttonBack = findViewById(R.id.btnBack);

        switchVisibility.setChecked(sharedPreferences.getBoolean(KEY_VISIBILITY, true));
        switchMusic.setChecked(sharedPreferences.getBoolean(KEY_MUSIC, false));

        switchVisibility.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(KEY_VISIBILITY, isChecked);
            editor.apply();
        });

        switchMusic.setOnCheckedChangeListener((buttonView, isChecked) -> {
            app.toggleMusic(isChecked);
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, MainMenu.class);
                startActivity(intent);
            }
        });
    }
}