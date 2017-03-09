package com.zividig.newnestziv.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.zividig.newnestziv.R;
import com.zividig.newnestziv.ui.base.BaseActivity;
import com.zividig.newnestziv.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录
 * Created by adolph
 * on 2017-02-28.
 */

public class LoginActivity extends BaseActivity implements LoginMvpView{

    @Inject
    LoginMvpPresenter<LoginMvpView> mPresenter;

    @BindView(R.id.login_et_user)
    EditText mLoginUser;

    @BindView(R.id.login_et_pwd)
    EditText mLoginPassword;

    @BindView(R.id.login_cb_save_user)
    CheckBox mSaveUser;

    @BindView(R.id.login_cb_save_password)
    CheckBox mSavePassword;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setStatusBar();

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(LoginActivity.this);
    }

    //登录
    @OnClick(R.id.login_bt_login)
    void onLoginClick(View v){
        mPresenter.onLoginClick(mLoginUser.getText().toString(), mLoginPassword.getText().toString());
    }

    @Override
    protected void setUp() {

    }

    @Override
    public void openMainActivity() {
        Intent intent = MainActivity.getStartIntent(LoginActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDetach();
    }
}
