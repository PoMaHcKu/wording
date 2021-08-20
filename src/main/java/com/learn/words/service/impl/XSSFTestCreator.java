package com.learn.words.service.impl;

import com.learn.words.service.WordTestCreator;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.List;

public class XSSFTestCreator implements WordTestCreator {

    @Override
    public List<Row> loadAllRows(XSSFWorkbook workbook) {
        return loadAllRowsFrom(workbook, 0);
    }

    @Override
    public List<Row> loadAllRowsFrom(XSSFWorkbook workbook, int startRow) {
        if (workbook == null) {
            throw new IllegalArgumentException("Workbook is null");
        }

        int firstVisibleTabIndex = workbook.getFirstVisibleTab();
        XSSFSheet workSheet = workbook.getSheetAt(firstVisibleTabIndex);
        int lastRowNum = workSheet.getLastRowNum();
        List<Row> rows = new ArrayList<>(lastRowNum);

        if (lastRowNum > startRow) {
            for (;startRow < lastRowNum; startRow++) {
                XSSFRow row = workSheet.getRow(startRow);
                XSSFCell firstCell = row.getCell(0);
                if (firstCell == null || StringUtils.isBlank(firstCell.getStringCellValue())) {
                    continue;
                }
                rows.add(row);
            }
        }

        return rows;
    }
}
