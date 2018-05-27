package com.example.dovebook.widget.recycler;

/**
 * Created by zzx on 18-1-19.
 */

public interface AdapterCallback<Data> {

    void update(Data data, RecyclerAdapter.ViewHolder<Data> holder);
}
