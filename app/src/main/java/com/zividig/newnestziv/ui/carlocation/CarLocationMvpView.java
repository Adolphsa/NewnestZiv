package com.zividig.newnestziv.ui.carlocation;

import com.zividig.newnestziv.ui.base.MvpView;

/**
 * Created by adolph
 * on 2017-03-28.
 */

public interface CarLocationMvpView extends MvpView{

    void initMap(double lat,double lon,String maptime);
}
