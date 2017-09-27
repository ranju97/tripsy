package com.example.android.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DstDetailActivity extends AppCompatActivity {

    private String dst_id;
    private DatabaseReference mDatabase;
    String dst_name;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dst_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //The two commands below bring in the back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        initValues();
        init();

    }

    public void initValues() {
        dst_name = "";
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void init() {

        if(getIntent().hasExtra(Constants.IntentExtras.DST_ID)){
            dst_id = getIntent().getStringExtra(Constants.IntentExtras.DST_ID);
        }else {
            dst_id = "";
        }
        if(getIntent().hasExtra(Constants.IntentExtras.DST_NAME)){
            dst_name = getIntent().getStringExtra(Constants.IntentExtras.DST_NAME);
        }else {
            dst_name = "";
        }
        toolbar.setTitle(dst_name);
        mDatabase = mDatabase.child("destination").child(dst_id);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dst_name = dataSnapshot.child("name").getValue().toString();
                toolbar.setTitle(dst_name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
