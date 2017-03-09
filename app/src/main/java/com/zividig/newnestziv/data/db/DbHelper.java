package com.zividig.newnestziv.data.db;

import com.zividig.newnestziv.data.db.model.Users;

import java.util.List;

/**
 * Created by adolph
 * on 2017-02-27.
 */

public interface DbHelper {

    Long insertUser(final Users user);

    Users queryUser(final String user);

    List<Users> getAllUsers();
}
