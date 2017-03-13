package com.zividig.newnestziv.utils;

/**
 * Created by adolph
 * on 2016-12-13.
 */

public class SignatureUtils {

    public static final String APP_KEY = "1793584B";
    public static final String APP_SECRET = "9D2AD5F5F1DBE68440E4211AD795E584";

    public static final String SIGNATURE_APP_KEY = "app_key";
    public static final String SIGNATURE_TIMESTAMP = "timestamp";
    public static final String SIGNATURE_NONCESTTR = "noncestr";
    public static final String SIGNATURE_STRING = "signature";
    public static final String SIGNATURE_TOKEN = "token";


    public static String getSinnature(String... args){

        String[] sortStrBefore = StringSortUtils.getUrlParam(args);
        String keyStr = "";
        for (String key : sortStrBefore) {
            keyStr += key;
        }

//        System.out.println("keyStr---" + keyStr);
        String signature = keyStr + APP_SECRET;

//        System.out.println("sing----" + signature);

        return MD5.getMD5(signature);
    }

}
