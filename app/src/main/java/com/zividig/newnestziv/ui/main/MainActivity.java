package com.zividig.newnestziv.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zividig.newnestziv.R;
import com.zividig.newnestziv.ui.base.BaseActivity;
import com.zividig.newnestziv.ui.customview.CustomsViewPager;
import com.zividig.newnestziv.ui.fragment.fragmentAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageBottomTabLayout;

public class MainActivity extends BaseActivity implements MainMvpView {


    @BindView(R.id.main_custom_view_pager)
    CustomsViewPager mViewPager;

    @BindView(R.id.main_tab)
    PageBottomTabLayout mTabLayoutab;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStatusBar();


        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        init();
    }

    private void init(){

        NavigationController navigationController = mTabLayoutab.material()
                .addItem(android.R.drawable.ic_menu_camera, "相机")
                .addItem(android.R.drawable.ic_menu_compass, "位置")
                .addItem(android.R.drawable.ic_menu_search, "搜索")
                .addItem(android.R.drawable.ic_menu_help, "帮助")
                .build();

        mViewPager.setAdapter(new fragmentAdapter(getSupportFragmentManager(),
                navigationController.getItemCount()));

        //自动适配ViewPager页面切换
        mTabLayoutab.setupWithViewPager(mViewPager);
    }

    @Override
    protected void setUp() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }
}
