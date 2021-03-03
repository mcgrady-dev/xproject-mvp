package com.mcgrady.module_test.thread;

import java.lang.ref.WeakReference;

public class TestThreadLocal {

    public static void main(String[] args) {
        WeakReference<Object> weakReference = new WeakReference<>(new Object());

        System.out.println("GC befow " + weakReference.get());

        System.gc();
        System.out.println("GC after " + weakReference.get());
    }
}