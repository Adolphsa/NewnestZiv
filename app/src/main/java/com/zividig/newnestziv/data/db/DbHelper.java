package com.zividig.newnestziv.data.db;

import com.zividig.newnestziv.data.db.model.Users;

import io.reactivex.Observable;

/**
 * Created by adolph
 * on 2017-02-27.
 */

public interface DbHelper {

    Observable<Long> insertUser(final Users user);
}
