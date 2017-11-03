package com.example.dovebook;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class bookPageFragment extends Fragment {

    private ArrayList<Fragment> mFragment;

    public bookPageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


       View view = inflater.inflate(R.layout.fragment_book_page, container, false);

        mFragment = new ArrayList<>();
        mFragment.add(new bookReceived_fragment());
        mFragment.add(new bookSent_fragment());

        ViewPager bookPager = (ViewPager) view.findViewById (R.id.book_pager);

        /*getChildFragmentManager   和 getFragmentManager 的区别*/
        bookPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
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

        return view;
    }

}
