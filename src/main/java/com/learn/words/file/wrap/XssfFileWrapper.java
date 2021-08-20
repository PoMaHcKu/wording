package com.learn.words.file.wrap;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

public class XssfFileWrapper {

    private XSSFWorkbook workbook;
    private String path;

    public XssfFileWrapper(String path) throws IOException {
        this.workbook = new XSSFWorkbook(path);
        this.path = path;
    }

    public XSSFWorkbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(XSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}