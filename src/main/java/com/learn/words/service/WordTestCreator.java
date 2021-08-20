package com.learn.words.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

public interface WordTestCreator {

    List<Row> loadAllRows(XSSFWorkbook workbook);

    List<Row> loadAllRowsFrom(XSSFWorkbook workbook, int startRow);

    void removeValuesFromEachRowRandomCell(List<Row> rows);

}