package com.example.dovebook.home;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dovebook.Information.InfoManager;
import com.example.dovebook.R;
import com.example.dovebook.base.BaseFragment;
import com.example.dovebook.contact.contactActivity;
import com.example.dovebook.login.LoginActivity;
import com.example.dovebook.login.TimeManager;
import com.example.dovebook.login.UserManager;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFragment extends BaseFragment {

    private static String TAG="HomePageFragment";

    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.home_information)
    RelativeLayout home_information;
    @BindView(R.id.imageView)
    CircleImageView imageView;
    @BindView(R.id.home_friend)
    RelativeLayout home_friend;
    @BindView(R.id.home_logoff)
    RelativeLayout home_logoff;


    private com.example.dovebook.login.UserManager mUserManager;


    public HomePageFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initWidget(View view) {
        super.initWidget(view);
        mUserManager= UserManager.getInstance();
        home_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),InfoManager.class));
            }
        });

        home_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),contactActivity.class));
            }
        });

        home_logoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = new TimeManager(getContext()).pref.edit();
                editor.clear();
                editor.commit();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });
    }
    

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_home_page;
    }



    private void updateUI(){
        Log.d(TAG, "print: "+mUserManager.getUser().getUserAvatarPath());
        Glide.with(getActivity()).load(mUserManager.getUser().getUserAvatarPath()).into(imageView);
        userName.setText(mUserManager.getUser().getUserName());
    }
}
