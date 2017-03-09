package com.zividig.newnestziv.ui.login;

import com.zividig.newnestziv.data.db.model.Users;
import com.zividig.newnestziv.di.PerActivity;
import com.zividig.newnestziv.ui.base.MvpPresenter;

import java.util.List;

/**
 * Created by adolph
 * on 2017-02-28.
 */
@PerActivity
public interface LoginMvpPresenter <V extends LoginMvpView> extends MvpPresenter<V> {

    Users getUsers();

    void onLoginClick(String user,String password);

    void setCheckSaveUser(boolean checkSaveUser);

    void setCheckSavePassword(boolean checkSavePassword);

    boolean getCheckSaveUser();

    boolean getCheckSavePassword();

    String getUser();

    String getPassword();

    List<Users> getUsersList();
}
