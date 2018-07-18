package com.mcgrady.core.di;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <p></p>
 * @author: mcgrady
 * @date: 2018/5/9
 */

@Scope
@Retention(RUNTIME)
public @interface FragmentScope {
}
