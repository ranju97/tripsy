package com.example.android.myapplication.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.myapplication.R;
import com.example.android.myapplication.callback.RecyclerViewOnClickInterface;
import com.example.android.myapplication.data.Destination;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Avilash on 27-09-2017.
 */

public class DestinationAdapter extends RecyclerView.Adapter<DestinationAdapter.ViewHolder> {

    private List<Destination> mDataset;
    private Context mContext;
    private RecyclerViewOnClickInterface rv_callback;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tv_dst_name , tv_dst_desc;
        private ImageView iv_dst_bg;
        public CardView cv_dst;

        public ViewHolder(View itemView, TextView tv_dst_name, TextView tv_dst_desc, ImageView iv_dst_bg, CardView cv_dst) {
            super(itemView);
            this.tv_dst_name = tv_dst_name;
            this.tv_dst_desc = tv_dst_desc;
            this.iv_dst_bg = iv_dst_bg;
            this.cv_dst = cv_dst;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)


    public DestinationAdapter(List<Destination> mDataset, Context mContext, RecyclerViewOnClickInterface rv_callback) {
        this.mDataset = mDataset;
        this.mContext = mContext;
        this.rv_callback = rv_callback;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DestinationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dst_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        CardView cv_dst = (CardView)v.findViewById(R.id.cv_dst);
        TextView tv_dst_name = (TextView)v.findViewById(R.id.tv_dst_name);
        TextView tv_dst_desc = (TextView)v.findViewById(R.id.tv_dst_desc);
        ImageView iv_dst_bg = (ImageView)v.findViewById(R.id.iv_dst_bg);
        ViewHolder vh = new ViewHolder(v , tv_dst_name , tv_dst_desc , iv_dst_bg , cv_dst);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rv_callback.onRVItemClick(position);
            }
        });
        holder.tv_dst_name.setText(mDataset.get(position).getName());
        holder.tv_dst_desc.setText(mDataset.get(position).getDescription());
        Picasso.with(mContext).load(mDataset.get(position).getPic_url()).into(holder.iv_dst_bg);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
