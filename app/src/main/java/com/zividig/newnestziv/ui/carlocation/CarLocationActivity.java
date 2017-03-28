package com.zividig.newnestziv.ui.carlocation;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.zividig.newnestziv.R;
import com.zividig.newnestziv.ui.base.BaseActivity;
import com.zividig.newnestziv.utils.GPSConverterUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 车辆信息
 * Created by adolph
 * on 2017-03-28.
 */

public class CarLocationActivity extends BaseActivity implements CarLocationMvpView{

    @Inject
    CarLocationMvpPresenter<CarLocationMvpView> mPresenter;

    @BindView(R.id.normal_back)
    ImageButton mBack;

    @BindView(R.id.normal_tv_title)
    TextView mTitle;

    @BindView(R.id.car_location_map)
    MapView mMapView;

    private View timeView;

    TextView mapTime;

    private boolean isFirst = true;
    private BaiduMap mBaiduMap;
    private MapStatus.Builder mBuilder;
    protected static OverlayOptions overlay;  // 覆盖物
    BitmapDescriptor carIcon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_location);
        setStatusBar();

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(CarLocationActivity.this);

        setUp();

        mPresenter.getCarLocation();
    }

    @Override
    protected void setUp() {
        mTitle.setText(getString(R.string.car_location_title));
        mBack.setVisibility(View.VISIBLE);

        mBaiduMap = mMapView.getMap();
        mBuilder = new MapStatus.Builder();

        timeView = View.inflate(this,R.layout.layout_map_lable,null);
        mapTime = (TextView) timeView.findViewById(R.id.car_location_text);

        mBuilder.target(GPSConverterUtils.gpsToBaidu(new LatLng(22.549467,113.920565)))
                .zoom(16.0f);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(mBuilder.build()));
    }

    @OnClick(R.id.normal_back)
    void onBack(View v){
        finish();
    }

    @Override
    public void initMap(double lat, double lon, String maptime) {

        mBaiduMap.clear();
        LatLng sourceLatLng = new LatLng(lat,lon);
        //坐标转换
        LatLng desLatLng = GPSConverterUtils.gpsToBaidu(sourceLatLng);
        System.out.println("转换后的经纬度---" + "lat---" + desLatLng.latitude + "lon---" + desLatLng.longitude);

        mapTime.setText(maptime);
        carIcon = BitmapDescriptorFactory
                .fromView(timeView);
        //标注
        overlay = new MarkerOptions().position(desLatLng).icon(carIcon).zIndex(9).draggable(true);

        if (isFirst){
            isFirst = false;
            mBuilder = new MapStatus.Builder();
            mBuilder.target(desLatLng).zoom(18.0f);
            mBaiduMap.addOverlay(overlay);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(mBuilder.build()));

        }else {
            System.out.println("增加点");
            mBaiduMap.addOverlay(overlay);

            Point pt= mBaiduMap.getMapStatus().targetScreen;
            Point point= mBaiduMap.getProjection().toScreenLocation(desLatLng);
            if(point.x < pt.x*0.4 || point.x > pt.x*1.6 || point.y < pt.y*0.4 || point.y > pt.y*1.6)
            {
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(desLatLng));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMapView != null){
            mMapView.onDestroy();
        }
        mPresenter.unsubscribe();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }


}
