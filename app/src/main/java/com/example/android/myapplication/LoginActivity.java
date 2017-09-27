package com.example.android.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.myapplication.utils.Validation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private String username , password;
    private EditText et_username , et_password;
    private CoordinatorLayout cl_login;
    private Snackbar snackbar;
    private Button but_login;

    private ProgressDialog pg_logging;

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUI();
        initUIActions();
        init();
    }

    public void initUI() {
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        but_login = (Button)findViewById(R.id.but_login);
        cl_login = (CoordinatorLayout)findViewById(R.id.cl_login);
        pg_logging = new ProgressDialog(this);
        snackbar = Snackbar.make(cl_login, "", Snackbar.LENGTH_SHORT);
    }

    public void initUIActions() {
        but_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPlease();
            }
        });
    }

    public void init() {
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    public void loginPlease() {
        boolean isValidEntries = validate();
        if(isValidEntries) {
            username = et_username.getText().toString();
            password = et_password.getText().toString();
            pg_logging.setMessage("Logging In");
            pg_logging.show();
            mFirebaseAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            pg_logging.hide();
                            if (task.isSuccessful()) {
                                loadLandingPage();
                            } else {
                                snackbar.setText("Login not successful");
                                snackbar.show();
                            }
                        }
                    });
        }
    }

    public boolean validate(){
        boolean valid = true;
        if(!Validation.isValidEmail(et_username.getText().toString())){
            et_username.setError("Please enter a valid email");
            valid = false;
        }
        else
            et_username.setError(null);
        if(et_password.getText().toString().isEmpty()){
            et_password.setError("Please enter password");
            valid = false;
        }
        else
            et_password.setError(null);
        return valid;

    }

    public void loadLandingPage() {
        Intent landingActivity = new Intent(getApplicationContext(), LandingActivity.class);
        startActivity(landingActivity);
        finish();
    }
}
