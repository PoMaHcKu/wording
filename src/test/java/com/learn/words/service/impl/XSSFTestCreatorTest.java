package com.learn.words.service.impl;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class XSSFTestCreatorTest {

    static XSSFWorkbook workbook;
    private final static String sourcePath = "src/test/resources/words_list.xlsx";
    private final static String resultPath = "src/test/resources/remove_random_cells_result.xlsx";

    @BeforeAll
    static void init() throws IOException, InvalidFormatException {
        workbook = new XSSFWorkbook(new File(sourcePath));
    }

    @Test
    void loadAllRows() {
        XSSFTestCreator creator = new XSSFTestCreator();
        assertDoesNotThrow(() -> creator.loadAllRows(workbook));
        assertThrows(IllegalArgumentException.class, () -> creator.loadAllRows(null));
    }

    @Test
    void loadAllRowsFrom() {
        XSSFTestCreator creator = new XSSFTestCreator();
        List<Row> rows = creator.loadAllRowsFrom(workbook, 0);
        int size = rows.size();
        assertNotEquals(0, size);
        rows = creator.loadAllRowsFrom(workbook, 1);
        if (size > 0) {
            assertNotEquals(size, rows.size());
        }
        rows = creator.loadAllRowsFrom(workbook, 500000);
        assertEquals(0, rows.size());
    }

    @Test
    void setRandomValueFromRows() throws IOException {
        XSSFTestCreator creator = new XSSFTestCreator();
        List<Row> rows = creator.loadAllRowsFrom(workbook, 1);
        assertDoesNotThrow(() -> creator.removeValuesFromEachRowRandomCell(null));
        assertDoesNotThrow(() -> creator.removeValuesFromEachRowRandomCell(rows));
        try (FileOutputStream out = new FileOutputStream(resultPath)) {
            assertDoesNotThrow(() -> workbook.write(out));
        }
    }
}