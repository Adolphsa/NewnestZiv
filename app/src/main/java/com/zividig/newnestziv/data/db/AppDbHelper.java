package com.zividig.newnestziv.data.db;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by adolph
 * on 2017-02-27.
 */

@Singleton
public class AppDbHelper implements DbHelper{

//    private final DaoSession mDaoSession;

    @Inject
    public AppDbHelper() {
//        mDaoSession = new DaoMaster(dbOpenHelper.getWritableDb()).newSession();
    }
}
