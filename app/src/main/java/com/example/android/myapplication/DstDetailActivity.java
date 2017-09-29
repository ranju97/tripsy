package com.example.android.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.android.myapplication.adapters.DstTimeLineAdapter;
import com.example.android.myapplication.data.TimeLine;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DstDetailActivity extends AppCompatActivity {

    private String dst_id , dst_name;
    private DatabaseReference mDatabase ,mDstNameDBRef ,  mTimeLineDBRef;

    private DstTimeLineAdapter dst_timeline_adapter;
    private LinearLayoutManager layout_manager;
    private List<TimeLine> myDataset;

    private RecyclerView rv_dest_timeline;

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
        initUI();
        initUIActions();
        init();
        loadData();

    }

    public void initValues() {
        dst_name = "";
        mDatabase = FirebaseDatabase.getInstance().getReference();
        myDataset = new ArrayList<>();
    }

    public void initUI() {
        rv_dest_timeline = (RecyclerView)findViewById(R.id.rv_dest_timeline);
    }

    public void initUIActions() {

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

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rv_dest_timeline.setHasFixedSize(true);

        // use a linear layout manager
        layout_manager = new LinearLayoutManager(this);
        rv_dest_timeline.setLayoutManager(layout_manager);

        // specify an adapter (see also next example)
        dst_timeline_adapter = new DstTimeLineAdapter(myDataset , this);
        rv_dest_timeline.setAdapter(dst_timeline_adapter);

    }

    public void loadData() {
        mDatabase = mDatabase.child("destination").child(dst_id);
        mDstNameDBRef = mDatabase.child("name");
        mDstNameDBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dst_name = dataSnapshot.getValue().toString();
                toolbar.setTitle(dst_name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mTimeLineDBRef = mDatabase.child("timeline");
        mTimeLineDBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> timeline_snap = dataSnapshot.getChildren();
                myDataset.clear();
                //How to use iterables
                //Iterable<Boys> bad_boys = ds.getChildren();
                //for(Boys boy : bad_boys)
                for(DataSnapshot timeline_item : timeline_snap) {
                    String timeline_dsc, timeline_day;
                    //Using keys as ids for easy querying of database
                    timeline_dsc = timeline_item.child("dsc").getValue().toString();
                    timeline_day = timeline_item.child("day").getValue().toString();
                    TimeLine timeLine = new TimeLine(timeline_dsc, timeline_day);
                    myDataset.add(timeLine);
                }
                dst_timeline_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
