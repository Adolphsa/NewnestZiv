package com.zividig.newnestziv.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;

import com.zividig.newnestziv.R;
import com.zividig.newnestziv.data.db.model.Users;
import com.zividig.newnestziv.ui.base.BaseActivity;
import com.zividig.newnestziv.ui.customview.DropEditText;
import com.zividig.newnestziv.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * 登录
 * Created by adolph
 * on 2017-02-28.
 */

public class LoginActivity extends BaseActivity implements LoginMvpView {

    @Inject
    LoginMvpPresenter<LoginMvpView> mPresenter;

    @BindView(R.id.login_et_user)
    DropEditText mLoginUser;

    @BindView(R.id.login_et_pwd)
    EditText mLoginPassword;

    @BindView(R.id.login_cb_save_user)
    CheckBox mCheckBoxUser;

    @BindView(R.id.login_cb_save_password)
    CheckBox mCheckBoxPassword;

    private List<Users> mUsersList;
    private ArrayAdapter<String> adapter;

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

        initDropEditText();

        //初始化用户名和密码
        initUserAndPassword();

        //初始化checkBox
        initCheckUserAndPassword();
    }


    @OnCheckedChanged(R.id.login_cb_save_user)
    void onCheckedUser(boolean checked){
        Timber.d("---save-user---" + checked);
        mPresenter.setCheckSaveUser(checked);
    }

    @OnCheckedChanged(R.id.login_cb_save_password)
    void onCheckedPassword(boolean checked) {
        Timber.d("---save-password---" + checked);
        mPresenter.setCheckSavePassword(checked);
    }

    //登录
    @OnClick(R.id.login_bt_login)
    void onLoginClick(View v) {
        mPresenter.onLoginClick(mLoginUser.getText().toString(), mLoginPassword.getText().toString());
    }

    @Override
    protected void setUp() {}

    @Override
    public void openMainActivity() {
        Intent intent = MainActivity.getStartIntent(LoginActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public String getUserName() {
        return mLoginUser.getText().toString().trim();
    }

    //初始化下拉列表
    private void initDropEditText(){

        List<String> strings = new ArrayList<>();
        mUsersList = mPresenter.getUsersList();
        if (mUsersList.size() > 0){
            for (Users user : mUsersList){
                strings.add(user.getUsername());
            }
        }

        //从数据库查询数据填充
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strings);
        mLoginUser.setAdapter(adapter);
        mLoginUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("被点击了");
                Users dbUser = mUsersList.get(position);
                mLoginUser.setText(dbUser.getUsername());
                mLoginPassword.setText(dbUser.getPassword());
                mLoginUser.setDismiss();
            }
        });
    }

    private void initUserAndPassword(){

        if (mPresenter.getCheckSaveUser()){
            mLoginUser.setText(mPresenter.getUser());
        }

        if (mPresenter.getCheckSavePassword()){
            mLoginPassword.setText(mPresenter.getPassword());
        }
    }

    public void initCheckUserAndPassword() {
        if (mPresenter.getCheckSaveUser()){
            mCheckBoxUser.setChecked(true);
        }  else {
            mCheckBoxUser.setChecked(false);
        }

        if (mPresenter.getCheckSavePassword()){
            mCheckBoxPassword.setChecked(true);
        }else {
            mCheckBoxPassword.setChecked(false);
        }
    }



    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
}
}
