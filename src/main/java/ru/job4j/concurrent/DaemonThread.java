package ru.job4j.concurrent;

public class DaemonThread extends Thread {
    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DaemonThread thread = new DaemonThread();
        thread.setName("Daemon thread");
        thread.setDaemon(true);
        System.out.println("Name: " + thread.getName() + ", is Daemon: " + thread.isDaemon());
        thread.start();
    }
}
