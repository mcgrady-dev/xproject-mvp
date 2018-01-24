package com.mcgrady.xproject.di.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * @author: mcgrady <mogui@weyee.com>
 * @date: 2017/12/26
 * @des:
 */

@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface GoldUrl {
}
