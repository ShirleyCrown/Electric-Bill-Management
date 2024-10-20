package com.example.electric_bill_management;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyApplication extends Application {

    private MediaPlayer mediaPlayer;
    private SharedPreferences sharedPreferences;
    private int activityReferences = 0;
    private boolean isActivityChangingConfigurations = false;
    private boolean playMusic = false;

    private static final String KEY_MUSIC = "play_music";

    @Override
    public void onCreate() {
        super.onCreate();

        sharedPreferences = getSharedPreferences("app_settings", MODE_PRIVATE);
        playMusic = sharedPreferences.getBoolean(KEY_MUSIC, false);

        registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks(){
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                if (++activityReferences == 1 && !isActivityChangingConfigurations) {
                    if (playMusic) {
                        startMusic();
                    }
                }
            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {
                isActivityChangingConfigurations = activity.isChangingConfigurations();
                if (--activityReferences == 0 && !isActivityChangingConfigurations) {
                    stopMusic();
                }
            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
    }

    // Phat nhac
    public void startMusic() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.sample);
            mediaPlayer.setLooping(true);
        }
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    // Dung phat nhac
    public void stopMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    // Bat/tat phat nhac khi thay doi tai man hinh Setting
    public void toggleMusic(boolean isMusicOn) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_MUSIC, isMusicOn);
        editor.apply();

        if (isMusicOn) {
            startMusic();
        } else {
            stopMusic();
        }
    }
}
