package com.zividig.newnestziv.ui.fragment.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zividig.newnestziv.ui.base.BaseFragment;

/**
 * Created by adolph
 * on 2017-03-06.
 */

public class MyFragment extends BaseFragment implements MyMvpView {

    public static MyFragment newInstance() {
        Bundle args = new Bundle();
        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getContext());
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        textView.setText("My");
        textView.setBackgroundColor(0xFFececec);
        return textView;
    }

    @Override
    protected void setUp(View view) {

    }

    @Override
    public void setStatusBar() {

    }
}
