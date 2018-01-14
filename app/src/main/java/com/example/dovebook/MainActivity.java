package com.example.dovebook;

import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.dovebook.base.BaseActivity;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    BottomNavigationBar bottomNavigationBar;

    BookPageFragment bookFragment;
    HomePageFragment homeFragment;
    LocationPageFragment locationFragment;

    /**
     * 初始化内容布局
     * @return
     */
    @Override
    protected View initContentView() {
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
     * 初始化Toolbar名称
     * @return
     */
    @Override
    protected String initToolbarTitle() {
        return "附近的书籍";
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

        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.icon_bar_book, "Book"))
                .addItem(new BottomNavigationItem(R.mipmap.icon_bar_location, "Location"))
                .addItem(new BottomNavigationItem(R.mipmap.icon_bar_friend, "Info"))
                .setBarBackgroundColor(R.color.white)
                .setActiveColor(R.color.colorForgive)
                .setInActiveColor(R.color.textGrey)
                .setFirstSelectedPosition(1)
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
                setToolbarTitle("我的书籍");
                break;
            case 1:
                if (locationFragment == null) {
                    locationFragment = new LocationPageFragment();
                }
                fragmentTransaction.replace(R.id.tb, locationFragment);
                setToolbarTitle("附近的书");
                break;
            case 2:
                if (homeFragment == null) {
                    homeFragment = new HomePageFragment();
                }
                fragmentTransaction.replace(R.id.tb, homeFragment);
                setToolbarTitle("社区");
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
        if (locationFragment == null) {
            locationFragment = new LocationPageFragment();
        }
        fragmentTransaction.replace(R.id.tb, locationFragment);
        fragmentTransaction.commit();
    }

}