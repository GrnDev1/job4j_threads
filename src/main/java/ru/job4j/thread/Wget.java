package ru.job4j.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String name;

    public Wget(String url, int speed, String name) {
        this.url = url;
        this.speed = speed;
        this.name = name;
    }

    @Override
    public void run() {
        File file = new File(name);
        try (InputStream in = new URL(url).openStream();
             FileOutputStream out = new FileOutputStream(file)) {
            byte[] dataBuffer = new byte[speed];
            long countBytes = 0;
            int bytesRead;
            long startTime = System.currentTimeMillis();
            long time;
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                countBytes += bytesRead;
                out.write(dataBuffer, 0, bytesRead);
                if (countBytes >= speed) {
                    time = (System.currentTimeMillis() - startTime);
                    if (time < 1000) {
                        try {
                            Thread.sleep(1000 - time);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    countBytes = 0;
                    startTime = System.currentTimeMillis();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void validate(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("Arguments not passed to program: 1. URL address; 2. Speed; 3. File name");
        }
        try {
            new URL(args[0]).toURI();
        } catch (URISyntaxException | MalformedURLException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validate(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String name = args[2];
        Thread wget = new Thread(new Wget(url, speed, name));
        wget.start();
        wget.join();
    }
}