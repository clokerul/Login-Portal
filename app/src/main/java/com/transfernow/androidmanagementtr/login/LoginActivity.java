package com.transfernow.androidmanagementtr.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.transfernow.androidmanagementtr.ProductActivity;
import com.transfernow.androidmanagementtr.R;
import com.transfernow.androidmanagementtr.connection.RequestHandler;
import com.transfernow.androidmanagementtr.model.LoginCredentials;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class LoginActivity extends AppCompatActivity {

    private RequestHandler requestHandler;
    private Button signUpBtn, logInBtn, forgotPassBtn;
    private TextInputLayout usernameTV, passwordTV;
    private ExecutorService executor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init_app();
    }

    private void init_app() {
        activateFullScreen();

        signUpBtn = findViewById(R.id.signUpBtn);
        logInBtn = findViewById(R.id.logInBtn);
        forgotPassBtn = findViewById(R.id.forgotPassBtn);

        usernameTV = findViewById(R.id.username);
        passwordTV = findViewById(R.id.password);
        executor = Executors.newFixedThreadPool(2);
        requestHandler = new RequestHandler();

        addBtnListeners();
    }

    private void addBtnListeners() {

        // Triggers register activity
        signUpBtn.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        // Triggers Login Activity
        logInBtn.setOnClickListener(view -> {
            // Get login credentials
            LoginCredentials credentials = new LoginCredentials(
                    Objects.requireNonNull(usernameTV.getEditText()).getText().toString(),
                    Objects.requireNonNull(passwordTV.getEditText()).getText().toString());

            // Create a login task
            FutureTask<Boolean> loginTask = new FutureTask<Boolean>((Callable<Boolean>) () ->
                    requestHandler.try_login(credentials));

            // Add the thread to the thread pool
            executor.execute(loginTask);

            // Temporary busy-waiting until is done
            // In future shall have a splash loading screen here
            while (!loginTask.isDone()) {
                System.out.println("FUTURE NOT DONE");
            }

            // Verify login credentials
            try {
                if (loginTask.get()) { // OK CREDENTIALS
                    Intent intent = new Intent(LoginActivity.this, ProductActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this,"Incorrect credentials!", Toast.LENGTH_SHORT).show();
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });
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