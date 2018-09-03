package com.mcgrady.main.event;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2018/9/3
 */

public class LoadMainBottomNavigationEvent {

    private String name;
    private int age;

    public LoadMainBottomNavigationEvent(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
