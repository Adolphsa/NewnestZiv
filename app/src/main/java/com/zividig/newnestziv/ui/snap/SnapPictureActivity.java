package com.zividig.newnestziv.ui.snap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.zividig.newnestziv.R;
import com.zividig.newnestziv.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * 抓图
 * Created by adolph
 * on 2017-03-14.
 */

public class SnapPictureActivity extends BaseActivity implements SnapPictureMvpView {

    @Inject
    SnapPictureMvpPresenter<SnapPictureMvpView> mPresenter;

    @BindView(R.id.normal_back)
    ImageButton mBack;

    @BindView(R.id.normal_tv_title)
    TextView mTitle;

    @BindView(R.id.snap_photo_view)
    PhotoView mPhotoView;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, SnapPictureActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snap_picture);
        setStatusBar();

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        initView();

    }

    @Override
    public void initView() {
        super.initView();
        mTitle.setText(getString(R.string.snap_title));
        mBack.setVisibility(View.VISIBLE);
        mPhotoView.enable();
    }

    @OnClick(R.id.normal_back)
    void onBack(View v){
        finish();
    }

    @Override
    protected void setUp() {

    }

    @OnClick(R.id.snap_bt_refresh)
    void onSnapRefres(View view){
        mPresenter.onSnapClick();
        Timber.d("抓图");
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void showImage(Bitmap bitmap) {
        mPhotoView.setImageBitmap(bitmap);
    }
}
