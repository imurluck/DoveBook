package com.example.dovebook.location;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.dovebook.widget.recycler.RecyclerAdapter;

/**
 * Created by zzx on 18-1-19.
 */

public class LocationAdapter extends RecyclerAdapter {


    @Override
    protected int getItemViewType(int position, Object o) {
        return 0;
    }

    @Override
    protected ViewHolder onCreateViewHolder(View root, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public void update(Object o, ViewHolder holder) {

    }
}
