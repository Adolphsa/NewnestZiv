package com.zividig.newnestziv.ui.fragment.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zividig.newnestziv.R;
import com.zividig.newnestziv.ui.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by adolph
 * on 2017-03-06.
 */

public class MyFragment extends BaseFragment implements MyMvpView {

    @Inject
    MyMvpPresenter<MyMvpView> mPresenter;

    @BindView(R.id.normal_tv_title)
    TextView mTitle;

    public static MyFragment newInstance() {
        Bundle args = new Bundle();
        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my,null);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this, view));

        mPresenter.onAttach(this);

        initView();

        Timber.d("哈哈哈3");
        return view;
    }

    @Override
    protected void setUp(View view) {

    }

    @Override
    public void setStatusBar() {

    }

    @Override
    public void initView() {
        mTitle.setText(getString(R.string.my_title));
    }
}
