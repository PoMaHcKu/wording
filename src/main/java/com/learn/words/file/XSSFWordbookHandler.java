package com.learn.words.file;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class XSSFWordbookHandler implements FileHandler<XSSFWorkbook> {

    private static final Logger logger = Logger.getLogger(XSSFWordbookHandler.class);

    @Override
    public XSSFWorkbook openFile(String path) {
        if (StringUtils.isBlank(path)) {
            String message = "Path is empty or null. Path is required property.";
            logger.error(message);
            throw new IllegalArgumentException(message);
        }
        try {
            return new XSSFWorkbook(path);
        } catch (IOException | RuntimeException e) {
            logger.error("Can't open book", e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void saveAndClose(XSSFWorkbook file, String path) {
        if (file == null || StringUtils.isBlank(path)) {
            String message = "File and path can't be empty";
            logger.error(message);
            throw new IllegalStateException(message);
        }
        try (OutputStream out = new FileOutputStream(path)) {
            file.write(out);
        } catch (IOException e) {
            logger.error("Error during saving file", e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void saveAndClose(XSSFWorkbook file) {
        if (file == null) {
            String message = "File can't be null";
            logger.error(message);
            throw new IllegalStateException(message);
        }
        try {
            file.write(file.getPackagePart().getOutputStream());
        } catch (IOException e) {
            logger.error("Error during saving file", e);
            throw new IllegalStateException(e);
        }
    }
}
