package com.example.figur;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.figur.ui.login.LoginResult;
import com.example.figur.ui.register.RegisterFormState;
import com.example.figur.ui.register.RegisterResult;
import com.example.figur.ui.register.RegisterViewModel;
import com.example.figur.ui.register.RegisterViewModelFactory;

import Classes.User;
import Classes.UserDao;
import Database.AppDatabase;

import static java.util.Objects.isNull;

public class RegisterActivity extends AppCompatActivity {

    private AppDatabase db;

    public void initializeDatabase(){
        //Instance of the initializeDatabase
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "figur.db").allowMainThreadQueries().build();
    }

    public AppDatabase getDb(){
        return this.db;
    }

    private RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerViewModel = ViewModelProviders.of(this, new RegisterViewModelFactory())
                .get(RegisterViewModel.class);

        initializeDatabase();
        UserDao userDao = getDb().userDao();

        final EditText emailEditText = findViewById(R.id.et_email);
        final EditText passwordEditText = findViewById(R.id.et_password);

        final Button registerButton = findViewById(R.id.btn_register);
        final Button loginButton = findViewById(R.id.btn_login);

        registerViewModel.getRegisterFormState().observe(this, new Observer<RegisterFormState>() {
            @Override
            public void onChanged(RegisterFormState registerFormState) {
                if (registerFormState == null) {
                    return;
                }
                loginButton.setEnabled(registerFormState.isDataValid());
                registerButton.setEnabled(registerFormState.isDataValid());
                if (registerFormState.getUsernameError() != null) {
                    emailEditText.setError(getString(registerFormState.getUsernameError()));
                }
                if (registerFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(registerFormState.getPasswordError()));
                }
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                registerViewModel.registerDataChanged(emailEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };

        emailEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!registerValidation(registerViewModel.getRegisterFormState().getValue(),emailEditText,userDao)){
                    showRegisterMessage("Registration has failed.");
                }else{
                    showRegisterMessage("Registration was successful.");
                    try{
                        userDao.insertAll(new User(emailEditText.getText().toString(),passwordEditText.getText().toString()));
                        Intent loginActivity = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(loginActivity);
                        finish();
                    }catch (Exception e){
                        showRegisterMessage("Registration has failed.");
                    }

                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginActivity = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginActivity);
                finish();
            }
        });

        registerViewModel.getRegisterResult().observe(this, new Observer<RegisterResult>() {
            @Override
            public void onChanged(RegisterResult registerResult) {
                String message = "";

                if (registerResult == null) {
                    return;
                }
                if (registerResult.getError() != null) {
                    showRegisterMessage(registerResult.getError().toString());
                    setResult(Activity.RESULT_OK);
                    finish();
                }
                if (registerResult.getSuccess() != null) {
                    showRegisterMessage("The registration was successfull.");
                    setResult(Activity.RESULT_OK);
                    Intent loginActivity = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(loginActivity);
                    finish();
                }else{
                    setResult(Activity.RESULT_OK);
                    finish();
                }
                //setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                //finish();
            }
        });
    }

    private void showRegisterMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private boolean registerValidation(RegisterFormState registerFormState, EditText emailEditText, UserDao userDao){
        if (registerFormState.getUsernameError() != null) {
            return false;
        }else if (registerFormState.getPasswordError() != null) {
            return false;
        }

        if(!isNull(userDao.findByMail(emailEditText.getText().toString()))){
            showRegisterMessage("The mail is already registered.");
            return false;
        }

        return true;
    }
}
