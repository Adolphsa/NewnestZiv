package com.zividig.newnestziv.di.module;

import android.app.Activity;
import android.content.Context;

import com.zividig.newnestziv.di.ActivityContext;
import com.zividig.newnestziv.di.PerActivity;
import com.zividig.newnestziv.ui.carlocation.CarLocationMvpPresenter;
import com.zividig.newnestziv.ui.carlocation.CarLocationMvpView;
import com.zividig.newnestziv.ui.carlocation.CarLocationPresenter;
import com.zividig.newnestziv.ui.fragment.message.MessageMvpPresenter;
import com.zividig.newnestziv.ui.fragment.message.MessageMvpView;
import com.zividig.newnestziv.ui.fragment.message.MessagePresenter;
import com.zividig.newnestziv.ui.fragment.my.MyMvpPresenter;
import com.zividig.newnestziv.ui.fragment.my.MyMvpView;
import com.zividig.newnestziv.ui.fragment.my.MyPresenter;
import com.zividig.newnestziv.ui.fragment.mycar.MyCarMvpPresenter;
import com.zividig.newnestziv.ui.fragment.mycar.MyCarMvpView;
import com.zividig.newnestziv.ui.fragment.mycar.MyCarPresenter;
import com.zividig.newnestziv.ui.fragment.setting.SettingMvpPresenter;
import com.zividig.newnestziv.ui.fragment.setting.SettingMvpView;
import com.zividig.newnestziv.ui.fragment.setting.SettingPresenter;
import com.zividig.newnestziv.ui.login.LoginMvpPresenter;
import com.zividig.newnestziv.ui.login.LoginMvpView;
import com.zividig.newnestziv.ui.login.LoginPresenter;
import com.zividig.newnestziv.ui.main.MainMvpPresenter;
import com.zividig.newnestziv.ui.main.MainMvpView;
import com.zividig.newnestziv.ui.main.MainPresenter;
import com.zividig.newnestziv.ui.snap.SnapPictureMvpPresenter;
import com.zividig.newnestziv.ui.snap.SnapPictureMvpView;
import com.zividig.newnestziv.ui.snap.SnapPicturePresenter;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by adolph
 * on 2017-02-27.
 */

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @PerActivity
    LoginMvpPresenter<LoginMvpView> provideLoginPresenter(LoginPresenter<LoginMvpView> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> provideMainPresenter(MainPresenter<MainMvpView> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    MyCarMvpPresenter<MyCarMvpView> provideMyCarPresenter(MyCarPresenter<MyCarMvpView>
                                                                  presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MessageMvpPresenter<MessageMvpView> provideMessagePresenter(MessagePresenter<MessageMvpView>
                                                                  presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SettingMvpPresenter<SettingMvpView> provideSettingPresenter(SettingPresenter<SettingMvpView>
                                                                        presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MyMvpPresenter<MyMvpView> provideMyPresenter(MyPresenter<MyMvpView>
                                                                  presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SnapPictureMvpPresenter<SnapPictureMvpView> provideSnapPicturePresenter(SnapPicturePresenter<SnapPictureMvpView>
                                                                            presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    CarLocationMvpPresenter<CarLocationMvpView> provideCarLocationPresenter(CarLocationPresenter<CarLocationMvpView>
                                                                            presenter){
        return presenter;
    }
}
