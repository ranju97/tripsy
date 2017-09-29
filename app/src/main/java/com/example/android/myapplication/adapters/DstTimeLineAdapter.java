package com.example.android.myapplication.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.myapplication.R;
import com.example.android.myapplication.data.TimeLine;

import java.util.List;

/**
 * Created by Avilash on 29-09-2017.
 */

public class DstTimeLineAdapter extends RecyclerView.Adapter<DstTimeLineAdapter.ViewHolder>  {

    private List<TimeLine> mDataset;
    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tv_timeline_dsc,tv_timeline_day;

        public ViewHolder(View itemView, TextView tv_timeline_dsc, TextView tv_timeline_day) {
            super(itemView);
            this.tv_timeline_dsc = tv_timeline_dsc;
            this.tv_timeline_day = tv_timeline_day;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)


    public DstTimeLineAdapter(List<TimeLine> mDataset, Context mContext) {
        this.mDataset = mDataset;
        this.mContext = mContext;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DstTimeLineAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dst_timeline_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        TextView tv_timeline_dsc = (TextView)v.findViewById(R.id.tv_timeline_dsc);
        TextView tv_timeline_day = (TextView)v.findViewById(R.id.tv_timeline_day);
        ViewHolder vh = new ViewHolder(v , tv_timeline_dsc, tv_timeline_day);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.tv_timeline_dsc.setText(mDataset.get(position).getDescription());
        holder.tv_timeline_day.setText(mDataset.get(position).getDay());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}
