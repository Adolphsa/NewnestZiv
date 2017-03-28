package com.zividig.newnestziv.ui.fragment.mycar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.zividig.newnestziv.R;
import com.zividig.newnestziv.data.db.model.DeviceInfo;
import com.zividig.newnestziv.ui.base.BaseFragment;
import com.zividig.newnestziv.ui.carlocation.CarLocationActivity;
import com.zividig.newnestziv.ui.fragment.mycar.other.GridAdapter;
import com.zividig.newnestziv.ui.fragment.mycar.other.LocalImageHolderView;
import com.zividig.newnestziv.ui.main.MainActivity;
import com.zividig.newnestziv.ui.snap.SnapPictureActivity;
import com.zividig.newnestziv.utils.RxBus;
import com.zividig.newnestziv.utils.RxBusSubscriber;
import com.zividig.newnestziv.utils.RxSubscriptions;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
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

    @BindView(R.id.tv_device_state)
    TextView mDeviceState;

    private Subscription rxSubscription;
    private String mDevid;

    public static MyCarFragment newInstance() {
        Bundle args = new Bundle();
        MyCarFragment fragment = new MyCarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_car,null);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this, view));

        mPresenter.onAttach(this);

        Timber.d("哈哈哈");
        initView();
        initAd();
        initFunctionButton();

        mDevid = mPresenter.getDeviceId();

        return view;
    }

    @Override
    public void initView() {

        rxSubscription = RxBus.getDefault().toObservableSticky(DeviceInfo.class)
                    .subscribe(new RxBusSubscriber<DeviceInfo>() {
                        @Override
                        protected void onEvent(DeviceInfo deviceInfo) {
                            Timber.d(deviceInfo.getDeviceId());
                            String devid = deviceInfo.getDeviceId();
                            Timber.d("rxbus中收到的devid---" + devid);
                            String subDevid = devid.substring(devid.length()-4,devid.length());
                            String temp = getString(R.string.my_car_title);
                            mTitle.setText(temp + subDevid);
                            mPresenter.loopGetDeviceState();
                            Timber.d("rxbus中收到的usename---" + deviceInfo.getUserName());
                        }
                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                        }
                    });

        RxSubscriptions.add(rxSubscription);

//        if (TextUtils.isEmpty((mDevid))){
//            Timber.d("devid为空");
//            mTitle.setText("我的车");
//            mDeviceState.setText(getString(R.string.my_car_id_is_null));
//        }else {
//            mPresenter.getMyCarDeviceState(mDevid);
//            String subDevid = mDevid.substring(mDevid.length()-4,mDevid.length());
//            String temp = getString(R.string.my_car_title);
//            mTitle.setText(temp + subDevid);
////            mDeviceState.setText("啦啦啦");
//            Timber.d("啦啦啦");
//        }

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

    private void initFunctionButton(){
        mGridView.setAdapter(new GridAdapter(getContext()));
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Timber.d("图片抓拍");
                        startActivity(new Intent(getContext(), SnapPictureActivity.class));
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        Timber.d("车辆定位");
                        startActivity(new Intent(getContext(), CarLocationActivity.class));
                        break;
                    case 4:
                        break;
                }
            }
        });
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
    public void onDestroyView() {
        Timber.d("MyCarFragment---onDestroyView");
//        mPresenter.onDetach();
        super.onDestroyView();

    }

    @Override
    protected void setUp(View view) {}

    @Override
    public void setStatusBar() {}

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxSubscriptions.remove(rxSubscription);
    }

    @Override
    public void setDeviceStateTitle(String msg) {
            mDeviceState.setText(msg);
    }

    @Override
    public int getIndex() {
        MainActivity mainActivity = (MainActivity) getActivity();
        int index = mainActivity.getIndex();
        Timber.d("index---" + index);
        return index;
    }
}
