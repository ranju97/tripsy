package com.example.android.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    public static String deviceId;

    private LinearLayout ll_login_actions;
    private Button but_login , but_signup;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initUIActions();
        init();
    }

    public void initUI() {
        ll_login_actions = (LinearLayout)findViewById(R.id.ll_login_actions);
        but_login = (Button)findViewById(R.id.but_login);
        but_signup = (Button)findViewById(R.id.but_signup);
    }

    public void initUIActions() {
        but_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });
        but_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignupActivity();
            }
        });
    }

    public void init() {
        deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            loadLoginView();
        } else
            loadLandingPage();
    }

    public void loadLoginView() {
        ll_login_actions.setVisibility(View.VISIBLE);
    }

    public void openLoginActivity() {
        Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(loginActivity);
    }

    public void openSignupActivity() {
        Intent signupActivity = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(signupActivity);
    }

    public void loadLandingPage() {
        Intent landingActivity = new Intent(getApplicationContext(), LandingActivity.class);
        startActivity(landingActivity);
        finish();
    }
}
