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
import android.widget.Toast;

import com.example.android.myapplication.utils.Validation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    private String username , password;
    private EditText et_username , et_password , et_password_repeat;
    private CoordinatorLayout cl_signup;
    private Snackbar snackbar;
    private Button but_signup;

    private ProgressDialog pg_logging;

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUI();
        initUIActions();
        init();
    }

    public void initUI() {
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        et_password_repeat = (EditText) findViewById(R.id.et_password_repeat);
        but_signup = (Button)findViewById(R.id.but_login);
        cl_signup = (CoordinatorLayout)findViewById(R.id.cl_signup);
        pg_logging = new ProgressDialog(this);
        snackbar = Snackbar.make(cl_signup, "", Snackbar.LENGTH_SHORT);
    }

    public void initUIActions() {
        but_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupPlease();
            }
        });
    }

    public void init() {
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    public void signupPlease() {
        boolean isValidEntries = validate();
        if (isValidEntries) {
            username = et_username.getText().toString();
            password = et_password.getText().toString();
            pg_logging.setMessage("Signing Up");
            pg_logging.show();
            mFirebaseAuth.createUserWithEmailAndPassword(username , password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            pg_logging.hide();
                            if (task.isSuccessful()) {
                                Toast.makeText(SignupActivity.this , "Registered Successfully. Please verify your email" , Toast.LENGTH_SHORT).show();
                                openLoginActivity();
                            } else {
                                snackbar.setText(task.getException().getMessage());
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
        String pwd , pwd_repeat;
        pwd = et_password.getText().toString();
        pwd_repeat = et_password_repeat.getText().toString();
        if(pwd.isEmpty()){
            et_password.setError("Please enter password");
            valid = false;
        }
        else
            et_password.setError(null);
        if(pwd_repeat.isEmpty()){
            et_password_repeat.setError("Please enter password");
            valid = false;
        }
        else
            et_password_repeat.setError(null);
        if(!pwd.equals(pwd_repeat)) {
            snackbar.setText("Both Passwords are not same");
            snackbar.show();
            valid = false;
        }
        return valid;

    }

    public void openLoginActivity() {
        Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(loginActivity);
        finish();
    }
}
