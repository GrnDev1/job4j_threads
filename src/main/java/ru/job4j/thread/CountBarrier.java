package ru.job4j.thread;

public class CountBarrier {
    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            System.out.println(Thread.currentThread().getName() + ". Count = " + count);
            notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (count < total) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + ". Готово. Count = " + count);
        }
    }

    public static void main(String[] args) {
        CountBarrier barrier = new CountBarrier(5);
        Thread thread1 = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    for (int i = 1; i <= 5; i++) {
                        barrier.count();
                    }
                }
        );

        Thread thread2 = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    for (int i = 1; i <= 5; i++) {
                        barrier.count();
                    }
                }
        );

        Thread thread3 = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                        barrier.await();
                }
        );
        thread1.start();
        thread2.start();
        thread3.start();
    }
}