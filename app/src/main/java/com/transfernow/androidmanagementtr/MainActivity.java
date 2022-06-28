package com.transfernow.androidmanagementtr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.transfernow.androidmanagementtr.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    ImageView tr_logo;
    TextView app_name, app_slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AndroidManagementTR);

        // Activate fullscreen
        setContentView(R.layout.activity_main);

        init_app();
    }

    private void init_app() {

        activateFullScreen();

        // Find views
        tr_logo = findViewById(R.id.tr_logo);
        app_name = findViewById(R.id.app_name);
        app_slogan = findViewById(R.id.app_slogan);

        loadAnimations();
        loadLoginScreen();

    }

    private void loadLoginScreen() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, 1500);
    }

    private void activateFullScreen() {
        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        final View decorView = getWindow().getDecorView();

        decorView.setSystemUiVisibility(flags);
    }

    private void loadAnimations() {
        Animation topAnim, bottomAnim;

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        tr_logo.setAnimation(topAnim);
        app_name.setAnimation(bottomAnim);
        app_slogan.setAnimation(bottomAnim);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus)
            activateFullScreen();
    }

    @Override
    protected void onResume() {
        super.onResume();
        activateFullScreen();
    }
}