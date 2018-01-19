package com.example.dovebook.location;


import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.dovebook.R;
import com.example.dovebook.base.BaseFragment;
import com.example.dovebook.widget.recycler.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class LocationPageFragment extends BaseFragment {
    @BindView(R.id.location_fragment_recycler)
    RecyclerView mRecycler;

    private List<String> list;

    public static LocationPageFragment newInstance(String param1) {
        LocationPageFragment fragment = new LocationPageFragment();
        return fragment;
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_location_page;
    }

    @Override
    protected void initData() {
        initList();
        RecyclerAdapter<String> adapter = new RecyclerAdapter<String>() {
            @Override
            protected int getItemViewType(int position, String s) {
                return 0;
            }

            @Override
            protected ViewHolder<String> onCreateViewHolder(View root, int viewType) {
                return null;
            }

            @Override
            public void update(String s, ViewHolder<String> holder) {

            }
        }
    }

    protected void initList() {
        if (list == null) {
            list = new ArrayList<>();
        }
        for (int i = 0; i < 20; i++) {
            list.add("item " + (i + 1));
        }
    }
}
