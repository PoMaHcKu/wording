package com.learn.words.file;

import org.apache.poi.ooxml.POIXMLDocument;

public interface FileHandler<T extends POIXMLDocument> {

    T openFile(String path);

    void saveAndClose(T file, String path);

    void saveAndClose(T file);
}