package com.example.dovebook.book;


import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dovebook.R;
import com.example.dovebook.base.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import permission.util.PermissionFail;
import permission.util.PermissionGen;
import permission.util.PermissionSuccess;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookPageFragment extends BaseFragment {

    private static final String TAG = "BookPageFragment";

    private ArrayList<Fragment> mFragment;

    @BindView(R.id.book_pager)
    ViewPager bookPager;

    public BookPageFragment() {
        // Required empty public constructor
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        super.onCreateView(inflater, container, savedInstanceState);
//
//       View view = inflater.inflate(R.layout.fragment_book_page, container, false);
//
//        Log.d(TAG, "onCreateView: 111");
//        mFragment = new ArrayList<>();
//        mFragment.add(new BookReceived_fragment());
//        mFragment.add(new BookSent_fragment());
//        initViews(view);
//
//        return view;
//    }


    @Override
    protected void initWidget(View view) {
        mFragment = new ArrayList<>();
        mFragment.add(new BookReceived_fragment());
        mFragment.add(new BookSent_fragment());
        initViews(view);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_book_page;
    }

    private void initViews(View view) {



        /*getChildFragmentManager   和 getFragmentManager 的区别*/
        bookPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "收到的书";
                    case 1:
                        return "发出的书";
                    default:
                        return "收到的书";
                }
            }
        });
    }
}
