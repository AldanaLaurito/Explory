package com.example.figur;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.figur.R;
import com.example.figur.ui.login.LoginViewModel;
import com.example.figur.ui.login.LoginViewModelFactory;
import com.example.figur.ui.register.RegisterViewModelFactory;

public class RegisterActivity extends AppCompatActivity {

    private LoginViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        registerViewModel = ViewModelProviders.of(this, new RegisterViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.et_name);
        final EditText passwordEditText = findViewById(R.id.et_password);

        final Button registerButton = findViewById(R.id.btn_register);
        final Button loginButton = findViewById(R.id.btn_login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

    }
}
