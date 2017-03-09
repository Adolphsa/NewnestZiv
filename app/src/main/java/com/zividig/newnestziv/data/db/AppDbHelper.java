package com.zividig.newnestziv.data.db;


import com.zividig.newnestziv.data.db.model.DaoMaster;
import com.zividig.newnestziv.data.db.model.DaoSession;
import com.zividig.newnestziv.data.db.model.Users;

import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by adolph
 * on 2017-02-27.
 */

@Singleton
public class AppDbHelper implements DbHelper{

    private final DaoSession mDaoSession;

    @Inject
    public AppDbHelper(DbOpenHelper dbOpenHelper) {
        mDaoSession = new DaoMaster(dbOpenHelper.getWritableDb()).newSession();
    }


    @Override
    public Observable<Long> insertUser(final Users user) {
        return Observable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return mDaoSession.getUsersDao().insert(user);
            }
        });
    }
}
