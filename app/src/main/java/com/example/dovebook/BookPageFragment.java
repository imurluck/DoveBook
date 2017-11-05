package com.example.dovebook;


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

import java.util.ArrayList;

import permission.util.PermissionFail;
import permission.util.PermissionGen;
import permission.util.PermissionSuccess;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookPageFragment extends Fragment {

    private static final String TAG = "BookPageFragment";

    private ArrayList<Fragment> mFragment;

    public BookPageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


       View view = inflater.inflate(R.layout.fragment_book_page, container, false);

        mFragment = new ArrayList<>();
        mFragment.add(new BookReceived_fragment());
        mFragment.add(new BookSent_fragment());
        initViews(view);
        requestPermissions();
        return view;
    }

    private void initViews(View view) {
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
    }

    private void requestPermissions() {
        PermissionGen
                .with(this)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .addRequestCode(100)
                .request();
    }

    @PermissionSuccess(requestCode = 100)
    private void requestPermissionSuccess() {
        Toast.makeText(this.getContext(), "permission rquest success",
                Toast.LENGTH_SHORT).show();
    }

    @PermissionFail(requestCode = 100)
    public void requestPermissionFail() {
        Toast.makeText(this.getContext(), "permission request failed",
                Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e(TAG, "onRequestPermissionsResult: ");
        Toast.makeText(this.getContext(), "has result in fragment",
                Toast.LENGTH_SHORT).show();
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
}
