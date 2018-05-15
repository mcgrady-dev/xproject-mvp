package com.mcgrady.core.account;

public interface AccountProvider<T> {

  T provideAccount(String accountJson);
}
