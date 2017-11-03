package com.example.dovebook;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {

    BottomNavigationBar bottomNavigationBar;

    bookPageFragment bookFragment;
    homePageFragment homeFragment;
    locationPageFragment locationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookFragment = new bookPageFragment();
        homeFragment = new homePageFragment();
        locationFragment = new locationPageFragment();

        /*隐藏底部导航栏*/
        hideBottomUIMenu();


        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC );

        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.icon_bar_book, "Book"))
                .addItem(new BottomNavigationItem(R.mipmap.icon_bar_location, "Location"))
                .addItem(new BottomNavigationItem(R.mipmap.icon_bar_friend, "Info"))
                .setBarBackgroundColor(R.color.white)
                .setActiveColor(R.color.colorForgive)
                .setInActiveColor(R.color.textGrey)
                .setFirstSelectedPosition(1)
                .initialise();

        bottomNavigationBar.setTabSelectedListener(this);

        this.setDefaultFragment();

    }

    @Override
    public void onTabSelected(int position) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        switch (position) {
            case 0:
                if (bookFragment == null) {
                    bookFragment = new bookPageFragment();
                }
                fragmentTransaction.replace(R.id.tb, bookFragment);
                break;
            case 1:
                if (locationFragment == null) {
                    locationFragment = new locationPageFragment();
                }
                fragmentTransaction.replace(R.id.tb, locationFragment);
                break;
            case 2:
                if (homeFragment == null) {
                    homeFragment = new homePageFragment();
                }
                fragmentTransaction.replace(R.id.tb, homeFragment);
                break;
            default:
                if (homeFragment == null) {
                    homeFragment = new homePageFragment();
                }
                fragmentTransaction.replace(R.id.tb, bookFragment);
                break;
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    private void setDefaultFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (locationFragment == null) {
            locationFragment = new locationPageFragment();
        }
        fragmentTransaction.replace(R.id.tb, locationFragment);
        fragmentTransaction.commit();
    }

    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}
