package com.mcgrady.xproject.di.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by mcgrady on 2017/8/9.
 */

@Scope
@Retention(RUNTIME)
public @interface ActivityScope {
}
