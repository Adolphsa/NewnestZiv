package com.zividig.newnestziv.ui.track;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zividig.newnestziv.R;
import com.zividig.newnestziv.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 轨迹查询
 * Created by adolph
 * on 2017-04-01.
 */

public class TrackActivity extends BaseActivity implements TrackMvpView{


    @Inject
    TrackMvpPresenter<TrackMvpView> mPresenter;

    @BindView(R.id.normal_back)
    ImageButton mBack;

    @BindView(R.id.normal_tv_title)
    TextView mTitle;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        setStatusBar();
        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        setUp();
    }

    @Override
    protected void setUp() {
        mTitle.setText(getString(R.string.track_title));
        mBack.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.normal_back)
    void onBack(View v){
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDetach();
    }
}
