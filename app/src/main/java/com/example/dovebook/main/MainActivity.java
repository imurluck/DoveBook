package com.example.dovebook.main;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.dovebook.R;
import com.example.dovebook.base.BaseActivity;
import com.example.dovebook.book.BookPageFragment;
import com.example.dovebook.home.HomePageFragment;
import com.example.dovebook.location.LocationPageFragment;
import com.example.dovebook.login.LoginActivity;
import com.example.dovebook.login.TimeManager;
import com.example.dovebook.share.ShareFragment;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    private static final String TAG = "MainActivity";
    BottomNavigationBar bottomNavigationBar;
    BookPageFragment bookFragment;
    HomePageFragment homeFragment;
    LocationPageFragment locationFragment;
    ShareFragment shareFragment;

    /**
     * 初始化内容布局
     * @return
     */
    @Override
    protected View initContentView() {
        Log.d(TAG, "initContentView: this is MainActivity");
        if (!TimeManager.isLoginTime()) {
            Log.d(TAG, "initContentView: LoginTime");
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        Log.d(TAG, "initContentView: not LoginTime");
        View view = getLayoutInflater().inflate(R.layout.activity_main, null);
        return view;
    }

    /**
     * 初始化一些设置
     */
    @Override
    protected void initOptions() {
        super.initOptions();
        bookFragment = new BookPageFragment();
        homeFragment = new HomePageFragment();
        locationFragment = new LocationPageFragment();
        initViews();
        this.setDefaultFragment();
    }


    /**
     * 申请权限，暂时还不严谨此处先略过
     */
    /**private void requestPermissions() {
        PermissionGen
                .with(this)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .addRequestCode(100)
                .request();
    }

    @PermissionSuccess(requestCode = 100)
    private void requestPermissionSuccess() {
        Toast.makeText(this, "permission rquest success",
                Toast.LENGTH_SHORT).show();
    }

    @PermissionFail(requestCode = 100)
    public void requestPermissionFail() {
        Toast.makeText(this, "permission request failed",
                Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }*/

    /**
     *初始化控件
     */
    private void initViews() {
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.icon_bar_book, "我的书籍"))
                .addItem(new BottomNavigationItem(R.mipmap.icon_bar_location, "附近的书"))
                .addItem(new BottomNavigationItem(R.mipmap.icon_share, "分享"))
                .addItem(new BottomNavigationItem(R.mipmap.icon_bar_friend, "社区"))
                .setBarBackgroundColor(R.color.white)
                .setActiveColor(R.color.colorPrimary)
                .setInActiveColor(R.color.secondary_text)
                .setFirstSelectedPosition(0)
                .initialise();

        bottomNavigationBar.setTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(int position) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        switch (position) {
            case 0:
                if (bookFragment == null) {
                    bookFragment = new BookPageFragment();
                }
                fragmentTransaction.replace(R.id.tb, bookFragment);
                break;
            case 1:
                if (locationFragment == null) {
                    locationFragment = new LocationPageFragment();
                }
                fragmentTransaction.replace(R.id.tb, locationFragment);
                break;
            case 2:
                if (shareFragment == null) {
                    shareFragment = new ShareFragment();
                }
                fragmentTransaction.replace(R.id.tb, shareFragment);
                break;
            case 3:
                if (homeFragment == null) {
                    homeFragment = new HomePageFragment();
                }
                fragmentTransaction.replace(R.id.tb, homeFragment);
                break;
            default:
                if (homeFragment == null) {
                    homeFragment = new HomePageFragment();
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
        if (bookFragment == null) {
            bookFragment = new BookPageFragment();
        }
        fragmentTransaction.replace(R.id.tb, bookFragment);
        fragmentTransaction.commit();
    }

}