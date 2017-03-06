package com.zividig.newnestziv.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by adolph
 * on 2017-02-27.
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface PreferenceInfo {
}
