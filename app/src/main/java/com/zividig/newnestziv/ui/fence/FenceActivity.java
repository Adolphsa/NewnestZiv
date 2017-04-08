package com.zividig.newnestziv.ui.fence;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.zividig.newnestziv.R;
import com.zividig.newnestziv.ui.base.BaseActivity;
import com.zividig.newnestziv.utils.GPSConverterUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by adolph
 * on 2017-04-01.
 */

public class FenceActivity extends BaseActivity implements FenceMvpView{

    @Inject
    FenceMvpPresenter<FenceMvpView> mPresenter;

    @BindView(R.id.normal_back)
    ImageButton mBack;

    @BindView(R.id.normal_tv_title)
    TextView mTitle;

    @BindView(R.id.fence_map)
    MapView mMapView;

    private BaiduMap mBaiduMap;
    private MapStatus.Builder mBuilder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fence);

        setStatusBar();
        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        setUp();
    }

    @Override
    protected void setUp() {
        mTitle.setText(getString(R.string.fence_title));
        mBack.setVisibility(View.VISIBLE);

        mBaiduMap = mMapView.getMap();
        mBuilder = new MapStatus.Builder();

        mBuilder.target(GPSConverterUtils.gpsToBaidu(new LatLng(22.549467,113.920565)))
                .zoom(16.0f);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(mBuilder.build()));
    }

    @OnClick(R.id.normal_back)
    void onBack(View v){
        finish();
    }

    @OnClick(R.id.fence_bt)
    void settingFence(View v){
        Timber.d("设置围栏");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDetach();
    }
}
