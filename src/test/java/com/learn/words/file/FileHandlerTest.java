package com.learn.words.file;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {

    static String path;
    static String emptyPath;
    static String incorrectPath;

    FileHandler<XSSFWorkbook> handler;

    @BeforeAll
    static void initStaticResources() {
        path = "/home/roman/Documents/words_list.xlsx";
        emptyPath = null;
        incorrectPath = "/home/no_exists_user/noFile";
    }

    @BeforeEach
    public void init() {
        this.handler = new XSSFWordbookHandler();
    }

    @Test
    void openFile() {
        assertNotNull(handler.openFile(path));
        assertThrows(IllegalArgumentException.class, () -> handler.openFile(emptyPath));
        assertThrows(IllegalStateException.class, () -> handler.openFile(incorrectPath));
    }

    @Test
    void saveAndClose() {
        XSSFWorkbook sheets = handler.openFile(path);

        long before = new File(path).lastModified();
        sheets.setSheetName(0, "after_test_name");
        handler.saveAndClose(sheets, "/home/roman/Documents/n_f.xlsx");
        long after = new File("/home/roman/Documents/n_f.xlsx").lastModified();

        assertTrue(after > before);
        assertThrows(IllegalStateException.class, () -> handler.saveAndClose(null, path));
        assertThrows(IllegalStateException.class, () -> handler.saveAndClose(sheets, emptyPath));
        assertThrows(IllegalStateException.class, () -> handler.saveAndClose(sheets, incorrectPath));
    }

    @Test
    void testSaveAndClose() {
    }

    @AfterEach
    public void destroy() {
        this.handler = null;
    }
}