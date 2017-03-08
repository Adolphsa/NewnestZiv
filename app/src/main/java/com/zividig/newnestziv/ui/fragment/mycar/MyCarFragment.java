package com.zividig.newnestziv.ui.fragment.mycar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.zividig.newnestziv.R;
import com.zividig.newnestziv.ui.base.BaseFragment;
import com.zividig.newnestziv.ui.fragment.mycar.other.GridAdapter;
import com.zividig.newnestziv.ui.fragment.mycar.other.LocalImageHolderView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by adolph
 * on 2017-03-06.
 */

public class MyCarFragment extends BaseFragment implements MyCarMvpView{

    @Inject
    MyCarMvpPresenter<MyCarMvpView> mPresenter;

    @BindView(R.id.tv_title)
    TextView mTitle;

    @BindView(R.id.my_car_convenientBanner) //广告轮播条
    ConvenientBanner mConvenientBanner;

    @BindView(R.id.my_car_grid)
    GridView mGridView;

    public static MyCarFragment newInstance() {
        Bundle args = new Bundle();
        MyCarFragment fragment = new MyCarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_car,null);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this, view));

        mPresenter.onAttach(this);

        initView();

        return view;
    }

    @Override
    public void initView() {
        mTitle.setText("我的车");
        mGridView.setAdapter(new GridAdapter(getContext()));

        initAd();
    }

    /**
     * 设置广告轮播条
     */
    private void initAd(){
        ArrayList<Integer> localImages = new ArrayList<Integer>();
        localImages.add(R.drawable.ad1);
        localImages.add(R.drawable.ad2);
        localImages.add(R.drawable.ad3);

        mConvenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
    }

    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
        mConvenientBanner.startTurning(3000);
    }

    @Override
    public void onPause() {
        super.onPause();
        mConvenientBanner.stopTurning();
    }

    @Override
    protected void setUp(View view) {

    }

    @Override
    public void setStatusBar() {

    }


}
