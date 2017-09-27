package com.example.android.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.myapplication.adapters.DestinationAdapter;
import com.example.android.myapplication.callback.RecyclerViewOnClickInterface;
import com.example.android.myapplication.data.Destination;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LandingActivity extends AppCompatActivity implements RecyclerViewOnClickInterface {

    private RecyclerView rv_dest;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabase;

    private DestinationAdapter dst_adapter;
    private LinearLayoutManager dst_layout_manager;
    private List<Destination> myDataset;

    private RecyclerViewOnClickInterface rv_callback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUI();
        initUIActions();
        init();
        loadData();
    }

    public void initUI() {
        rv_dest = (RecyclerView)findViewById(R.id.rv_dest);
    }

    public void initUIActions() {

    }

    public void init() {
        rv_callback = this;
        myDataset = new ArrayList<>();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rv_dest.setHasFixedSize(true);

        // use a linear layout manager
        dst_layout_manager = new LinearLayoutManager(this);
        rv_dest.setLayoutManager(dst_layout_manager);

        // specify an adapter (see also next example)
        dst_adapter = new DestinationAdapter(myDataset , this , rv_callback);
        rv_dest.setAdapter(dst_adapter);


    }

    public void loadData() {
        mDatabase = mDatabase.child("destination");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> destinations_snap = dataSnapshot.getChildren();
                myDataset.clear();
                //How to use iterables
                //Iterable<Boys> bad_boys = ds.getChildren();
                //for(Boys boy : bad_boys)
                for(DataSnapshot destination_snap : destinations_snap) {
                    String dst_id , dst_name , dst_description , dst_pic_url;
                    //Using keys as ids for easy querying of database
                    dst_id = destination_snap.getKey().toString();
                    dst_name = destination_snap.child("name").getValue().toString();
                    dst_description = destination_snap.child("details").child("description").getValue().toString();
                    dst_pic_url = destination_snap.child("pic").getValue().toString();
                    Destination destination = new Destination(dst_id , dst_name , dst_description , dst_pic_url);
                    myDataset.add(destination);
                }
                dst_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void logoutPlease() {
        mFirebaseAuth.signOut();
        openMainActivity();
        finish();
    }

    public void openMainActivity() {
        Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainActivity);
        finish();
    }

    @Override
    public void onRVItemClick(int position) {
        String dst_id = myDataset.get(position).getId();
        String dst_name = myDataset.get(position).getName();
        openDstDetailActivity(dst_id , dst_name);
    }

    public void openDstDetailActivity(String dst_id , String dst_name) {
        Intent dstDetailActivity = new Intent(getApplicationContext(), DstDetailActivity.class);
        dstDetailActivity.putExtra(Constants.IntentExtras.DST_ID , dst_id);
        dstDetailActivity.putExtra(Constants.IntentExtras.DST_NAME , dst_name);
        startActivity(dstDetailActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                logoutPlease();
                return true;
            case R.id.refresh:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
