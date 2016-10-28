package com.sergon146.drawer.recycler;

/**
 * Created by sergon on 28.10.16.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sergon146.drawer.R;

import java.util.ArrayList;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<String> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView fileName;

        public ViewHolder(View v) {
            super(v);
            fileName = (TextView) v.findViewById(R.id.file_name);
        }
    }

    public RecyclerAdapter(ArrayList<String> dataset) {
        mDataset = dataset;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.file_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.fileName.setText(mDataset.get(position));

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}