package com.zividig.newnestziv.utils;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;

/**
 * Created by adolph
 * on 2016-10-19.
 */

public class GPSConverterUtils {

    public static LatLng gpsToBaidu(LatLng sourceLatLng){
        CoordinateConverter converter = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.GPS);
        converter.coord(sourceLatLng);
        LatLng desLatLng = converter.convert();
        return desLatLng;
    }
}
