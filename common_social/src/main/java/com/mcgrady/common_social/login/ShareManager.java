package com.mcgrady.common_social.login;

/**
 * @author: mcgrady
 * @date: 2019-05-27
 */
public class ShareManager {

    private static boolean isInit = false;

    public static ShareConfig CONFIG;

    public static void init(ShareConfig config) {
        isInit = true;
        CONFIG = config;
    }
}
