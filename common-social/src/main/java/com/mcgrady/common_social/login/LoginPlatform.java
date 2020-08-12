package com.mcgrady.common_social.login;


import androidx.annotation.IntDef;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: mcgrady
 * @date: 2019-05-27
 */
public class LoginPlatform {

    @Documented
    @IntDef({QQ, WX, WEIBO})
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.PARAMETER)
    public @interface Platform {

    }

    public static final int QQ = 1;

    public static final int WX = 3;

    public static final int WEIBO = 5;
}
