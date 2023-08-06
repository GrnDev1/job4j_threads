package ru.job4j.ref;

import java.util.ArrayList;
import java.util.List;

public class ShareNotSafe {
    public static void main(String[] args) throws InterruptedException {
        UserCache cache = new UserCache();
        User user = User.of("main");
        cache.add(user);
        List<User> list = cache.findAll();
        System.out.println("Результат: " + list.get(0).getName());
        list.get(0).setName("new");
        System.out.println("Результат1: " + cache.findAll().get(0).getName());

        Thread first = new Thread(
                () -> {
                    for (int i = 0; i < 100; i++) {
                        user.setName("first");
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    for (int i = 0; i < 100; i++) {
                        user.setName("second");
                    }
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
        System.out.println(cache.findById(1).getName());
    }
}