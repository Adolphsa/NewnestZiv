package com.zividig.newnestziv.data.db;


import com.zividig.newnestziv.data.db.model.DaoMaster;
import com.zividig.newnestziv.data.db.model.DaoSession;
import com.zividig.newnestziv.data.db.model.DeviceInfo;
import com.zividig.newnestziv.data.db.model.DeviceInfoDao;
import com.zividig.newnestziv.data.db.model.Users;
import com.zividig.newnestziv.data.db.model.UsersDao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

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
    public Long insertUser(final Users user) {
        return mDaoSession.getUsersDao().insert(user);
    }

    @Override
    public Users queryUser(final String user) {
                return mDaoSession.getUsersDao().queryBuilder()
                        .where(UsersDao.Properties.Username.eq(user))
                        .build().unique();

    }

    @Override
    public void updateUser(Users user) {
        mDaoSession.getUsersDao().update(user);
    }

    @Override
    public List<Users> getAllUsers() {
        return mDaoSession.getUsersDao().loadAll();
    }

    @Override
    public Long insertDevice(DeviceInfo deviceInfo) {
        return mDaoSession.getDeviceInfoDao().insert(deviceInfo);
    }

    @Override
    public DeviceInfo queryDevice(String deviceId) {
        return mDaoSession.getDeviceInfoDao().queryBuilder()
                .where(DeviceInfoDao.Properties.DeviceId.eq(deviceId))
                .build().unique();
    }

    @Override
    public void updateDevice(DeviceInfo deviceInfo) {
        mDaoSession.getDeviceInfoDao().update(deviceInfo);
    }

    @Override
    public void deleteDevice(DeviceInfo deviceInfo) {
        mDaoSession.getDeviceInfoDao().delete(deviceInfo);
    }

    @Override
    public List<DeviceInfo> getAllDevice(String user) {
        return mDaoSession.getDeviceInfoDao().queryBuilder()
                .where(DeviceInfoDao.Properties.UserName.eq(user))
                .build().list();

    }


}
