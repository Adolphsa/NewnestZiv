package com.zividig.newnestziv.ui.fragment.mycar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.zividig.newnestziv.R;
import com.zividig.newnestziv.data.db.model.DeviceInfo;
import com.zividig.newnestziv.ui.base.BaseFragment;
import com.zividig.newnestziv.ui.fragment.mycar.other.GridAdapter;
import com.zividig.newnestziv.ui.fragment.mycar.other.LocalImageHolderView;
import com.zividig.newnestziv.utils.RxBus;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action1;
import timber.log.Timber;

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

    @BindView(R.id.my_car_device_state)
    TextView mDeviceState;

    private Subscription rxSubscription;
    private String mDevid;

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
        Timber.d("哈哈哈");

        mDevid = mPresenter.getDeviceId();

        return view;
    }

    @Override
    public void initView() {
        mTitle.setText("我的车");
        mGridView.setAdapter(new GridAdapter(getContext()));

        rxSubscription = RxBus.getDefault().toObservable(DeviceInfo.class)
                    .subscribe(new Action1<DeviceInfo>() {
                        @Override
                        public void call(DeviceInfo deviceInfo) {
                            Timber.d(deviceInfo.getDeviceId());
                            String devid = deviceInfo.getDeviceId();
                            String subDevid = devid.substring(devid.length()-4,devid.length());
                            String temp = getString(R.string.my_car_title);
                            mTitle.setText(temp + subDevid);
//                            mPresenter.setDeviceId(devid);
                            mPresenter.getMyCarDeviceState(devid);
                            Timber.d(deviceInfo.getUserName());
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {

                        }
                    });

        if (TextUtils.isEmpty((mDevid))){
            mDeviceState.setText(getString(R.string.my_car_id_is_null));
            Timber.d("啦啦啦1");
        }else {
            mPresenter.getMyCarDeviceState(mDevid);
        }

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
        Timber.d("MyCarFragment---onResume");
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



    @Override
    public void onDestroy() {
        super.onDestroy();
        if(!rxSubscription.isUnsubscribed()) {
            rxSubscription.unsubscribe();
        }
    }

    @Override
    public void setDeviceStateTitle(String msg) {
            mDeviceState.setText(msg);

    }
}
