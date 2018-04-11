package com.example.dovebook.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dovebook.R;
import com.example.dovebook.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFragment extends BaseFragment {


    public HomePageFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_home_page;
    }

}
