package com.learn.words.file;

public interface FileHandler<T> {

    T openFile(String path);

    void saveAndClose(String path);

    String saveAndClose();
}