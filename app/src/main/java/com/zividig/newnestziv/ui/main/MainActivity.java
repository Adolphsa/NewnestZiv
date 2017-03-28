package com.zividig.newnestziv.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zividig.newnestziv.R;
import com.zividig.newnestziv.ui.base.BaseActivity;
import com.zividig.newnestziv.ui.customview.CustomsViewPager;
import com.zividig.newnestziv.ui.fragment.FragmentAdapter;
import com.zividig.newnestziv.ui.fragment.mycar.MyCarMvpPresenter;
import com.zividig.newnestziv.ui.fragment.mycar.MyCarMvpView;
import com.zividig.newnestziv.utils.RxBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements MainMvpView {

    @Inject
    MainMvpPresenter<MainMvpView> mPresenter;

    @Inject
    MyCarMvpPresenter<MyCarMvpView> mMyCarPresenter;

    @BindView(R.id.main_custom_view_pager)
    CustomsViewPager mViewPager;

    @BindView(R.id.main_tab)
    PageBottomTabLayout mTabLayoutab;

    FragmentAdapter mFragmentAdapter;

    int currentIndex;

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

        mPresenter.onAttach(this);

        init();
    }

    private void init(){

        NavigationController navigationController = mTabLayoutab.material()
                .addItem(android.R.drawable.ic_menu_camera, "我的车")
                .addItem(android.R.drawable.ic_menu_compass, "消息")
                .addItem(android.R.drawable.ic_menu_search, "设置")
                .addItem(android.R.drawable.ic_menu_help, "我")
                .build();
        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(),
                navigationController.getItemCount());
        mViewPager.setAdapter(mFragmentAdapter);

        //自动适配ViewPager页面切换
        mTabLayoutab.setupWithViewPager(mViewPager);

        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                Timber.d("选中---" + index);
                currentIndex = index;
            }

            @Override
            public void onRepeat(int index) {

            }
        });
    }

    @Override
    public int getIndex() {
        return currentIndex;
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

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        // 移除所有Sticky事件
        RxBus.getDefault().removeAllStickyEvents();
        super.onDestroy();
    }


}
