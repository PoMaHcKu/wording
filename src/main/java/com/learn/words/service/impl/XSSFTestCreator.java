package com.learn.words.service.impl;

import com.learn.words.service.WordTestCreator;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class XSSFTestCreator implements WordTestCreator {

    static Logger logger = Logger.getLogger(XSSFTestCreator.class);

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
            for (; startRow < lastRowNum; startRow++) {
                XSSFRow row = workSheet.getRow(startRow);
                XSSFCell firstCell = row.getCell(0);
                if (firstCell == null || StringUtils.isBlank(firstCell.getStringCellValue())) {
                    continue;
                }
                rows.add(row);
            }
        }

        logger.info(String.format("Loaded %d rows", rows.size()));
        return rows;
    }

    @Override
    public void removeValuesFromEachRowRandomCell(List<Row> rows) {
        if (rows == null) {
            logger.warn("Argument rows can't be null");
            return;
        }
        rows.forEach(removeRandomValueFromRow());
    }

    private Consumer<Row> removeRandomValueFromRow() {
        return row -> {
            int cellNum = (int) (System.nanoTime() % 2);
            Cell cell = row.getCell(cellNum);
            cell.setCellValue(StringUtils.EMPTY);
        };
    }
}
