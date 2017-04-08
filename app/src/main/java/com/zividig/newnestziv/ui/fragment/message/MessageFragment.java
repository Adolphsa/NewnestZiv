package com.zividig.newnestziv.ui.fragment.message;

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

public class MessageFragment extends BaseFragment implements MessageMvpView {

    @Inject
    MessageMvpPresenter<MessageMvpView> mPresenter;

    @BindView(R.id.normal_tv_title)
    TextView mTitle;

    public static MessageFragment newInstance() {
        Bundle args = new Bundle();
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_message,null);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this, view));

        mPresenter.onAttach(this);

        initView();
        Timber.d("哈哈哈1");
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
        mTitle.setText(getString(R.string.message_title));
    }
}
