package ru.job4j.io;

import java.io.*;

public final class ContentSaver {
    private final File file;

    public ContentSaver(File file) {
        this.file = file;
    }

    public synchronized void saveContent(String content) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(file))) {
            out.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}