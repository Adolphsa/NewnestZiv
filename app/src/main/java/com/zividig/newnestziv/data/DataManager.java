package com.zividig.newnestziv.data;

import com.zividig.newnestziv.data.db.DbHelper;
import com.zividig.newnestziv.data.network.ApiHelper;
import com.zividig.newnestziv.data.prefs.PreferencesHelper;

/**
 * Created by adolph
 * on 2017-02-27.
 */

public interface DataManager extends DbHelper,PreferencesHelper,ApiHelper{
}
