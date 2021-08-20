package com.learn.words.file;

import com.learn.words.file.wrap.XssfFileWrapper;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {

    static String pathToSources;
    static String path;
    static String emptyPath;
    static String incorrectPath;

    static List<File> trash = new ArrayList<>();

    FileHandler<XssfFileWrapper> handler;

    @BeforeAll
    static void initStaticResources() {
        pathToSources = "src/test/resources/";
        path = pathToSources + "/words_list.xlsx";
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
        handler.saveAndClose(pathToSources + "n_f.xlsx");
        File trashAfter = new File(pathToSources + "n_f.xlsx");
        trash.add(trashAfter);
        long after = trashAfter.lastModified();

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

        assertDoesNotThrow(() -> {
            File saved = new File(handler.saveAndClose());
            trash.add(saved);
        });
        assertThrows(IllegalStateException.class, () -> handler.saveAndClose());
    }

    @AfterEach
    void destroy() {
        this.handler = null;
    }

    @AfterAll
    static void destroyAll() {
        trash.forEach(File::delete);
    }
}