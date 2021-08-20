package com.learn.words.file;

import com.learn.words.file.wrap.XssfFileWrapper;
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

    FileHandler<XssfFileWrapper> handler;

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
        XssfFileWrapper file = handler.openFile(path);
        XSSFWorkbook workbook = file.getWorkbook();

        long before = new File(path).lastModified();
        workbook.setSheetName(0, "after_test_name");
        handler.saveAndClose("/home/roman/Documents/n_f.xlsx");
        long after = new File("/home/roman/Documents/n_f.xlsx").lastModified();

        assertTrue(after > before);
        assertThrows(IllegalStateException.class, () -> handler.saveAndClose(path));
        handler.openFile(path);
        assertThrows(IllegalStateException.class, () -> handler.saveAndClose(emptyPath));
        handler.openFile(path);
        assertThrows(IllegalStateException.class, () -> handler.saveAndClose(incorrectPath));
    }

    @Test
    void testSaveWithoutPath() {
        XssfFileWrapper file = handler.openFile(path);

        XSSFWorkbook workbook = file.getWorkbook();
        workbook.setSheetName(0, "after_test_name");

        assertDoesNotThrow(() -> handler.saveAndClose());
        assertThrows(IllegalStateException.class, () -> handler.saveAndClose());
    }

    @AfterEach
    public void destroy() {
        this.handler = null;
    }
}