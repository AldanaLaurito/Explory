package com.example.figur;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.figur.ui.login.LoggedInUserView;
import com.example.figur.ui.login.LoginFormState;
import com.example.figur.ui.login.LoginResult;
import com.example.figur.ui.login.LoginViewModel;
import com.example.figur.ui.login.LoginViewModelFactory;
import com.example.figur.ui.register.RegisterFormState;

import Classes.UserDao;
import Database.AppDatabase;

import static java.util.Objects.isNull;

public class LoginActivity extends AppCompatActivity {

    private AppDatabase db;

    public void initializeDatabase(){
        //Instance of the initializeDatabase
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "figur.db").allowMainThreadQueries().build();
}

    public AppDatabase getDb(){
        return this.db;
    }



    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText emailEditText = findViewById(R.id.et_email);
        final EditText passwordEditText = findViewById(R.id.et_password);
        final Button loginButton = findViewById(R.id.btn_login);
        final Button registerButton = findViewById(R.id.btn_register);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        initializeDatabase();
        initializeDatabase();
        UserDao userDao = getDb().userDao();

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                registerButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    emailEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                    setResult(Activity.RESULT_OK);
                    finish();
                }
                if ((loginResult.getSuccess() != null) && (loginValidation(loginViewModel.getLoginFormState().getValue(), emailEditText, passwordEditText, userDao))) {
                    updateUiWithUser(loginResult.getSuccess());
                    setResult(Activity.RESULT_OK);
                    Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(mainActivity);
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
                loginViewModel.loginDataChanged(emailEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        emailEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(emailEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(emailEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerActivity = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerActivity);
                finish();
            }
        });
    }

    private boolean loginValidation(LoginFormState loginFormState, EditText emailEditText, EditText passwordEditText, UserDao userDao){
        if (loginFormState.getUsernameError() != null) {
            return false;
        }else if (loginFormState.getPasswordError() != null) {
            return false;
        }

        if(isNull(userDao.findByMail(emailEditText.getText().toString()))){
            showLoginMessage("There is no user with the email "+ emailEditText.getText().toString() +" .");
            return false;
        }else{
            if(!userDao.findByMail(emailEditText.getText().toString()).getPassword().matches(passwordEditText.getText().toString())){
                showLoginMessage("The password is incorrect.");
                return false;
            }
        }

        return true;
    }

    private void showLoginMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
