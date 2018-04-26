package com.example.dovebook.location;


import android.support.v4.app.Fragment;
import android.view.View;

import com.example.dovebook.R;
import com.example.dovebook.base.BaseFragment;
import com.example.dovebook.contact.contactAdapter;
import com.example.dovebook.widget.recycler.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class LocationPageFragment extends BaseFragment {

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_location_page;
    }

//    @Override
//    protected void initData() {
//        initList();
//        adapter = new RecyclerAdapter<String>() {
//            //传入item布局id
//            @Override
//            protected int getItemViewType(int position, String s) {
//                return R.layout.share_recycler_item;
//            }
//
//            @Override
//            protected ViewHolder onCreateViewHolder(View root, int viewType) {
//                return new LocationPageFragment.ViewHolder(root);
//            }
//        };

        /**adapter.setListener(new RecyclerAdapter.AdapterListener<String>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder<String> holder, String s) {
                ((ViewHolder) holder).tv.setText("item changed");
                Toast.makeText(getActivity(), "item click", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(RecyclerAdapter.ViewHolder<String> holder, String s) {

            }
        });*/
//        adapter.add("just an item");
//        adapter.add(list);
//    }

//    @Override
//    protected void initWidget(View view) {
//        super.initWidget(view);
//    }
}
