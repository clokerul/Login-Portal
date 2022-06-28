package com.transfernow.androidmanagementtr.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.transfernow.androidmanagementtr.R;

public class SignUpActivity extends AppCompatActivity {

    private Button backToLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        init();
    }

    private void init() {
        backToLoginBtn = findViewById(R.id.backToLoginBtn);

        activateFullScreen();
        addBtnListeners();
    }

    private void addBtnListeners() {
        // When pressed, it finishes the activity
        backToLoginBtn.setOnClickListener(view -> finish());
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
}