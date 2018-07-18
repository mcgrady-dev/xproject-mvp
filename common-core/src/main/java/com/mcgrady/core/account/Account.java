package com.mcgrady.core.account;

public interface Account {

    String name();

    String accessToken();

    String toJson();

    String getUserId();

    String getRelAvatar();

}
