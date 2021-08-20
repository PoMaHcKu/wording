package com.learn.words.file;

import com.learn.words.file.wrap.XssfFileWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;


public class XSSFWordbookHandler implements FileHandler<XssfFileWrapper> {

    private static final Logger logger = Logger.getLogger(XSSFWordbookHandler.class);

    private XssfFileWrapper file;

    @Override
    public XssfFileWrapper openFile(String path) {
        if (StringUtils.isBlank(path)) {
            String message = "Path is empty or null. Path is required property.";
            logger.error(message);
            throw new IllegalArgumentException(message);
        }
        try {
            this.file = new XssfFileWrapper(path);
            return this.file;
        } catch (IOException | RuntimeException e) {
            logger.error("Can't open book", e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void saveAndClose(String path) {
        if (file == null || StringUtils.isBlank(path)) {
            String message = "File and path can't be empty";
            logger.error(message);
            throw new IllegalStateException(message);
        }
        try (OutputStream out = new FileOutputStream(path)) {
            file.getWorkbook().write(out);
            this.file = null;
        } catch (IOException e) {
            logger.error("Error during saving file", e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void saveAndClose() {
        if (file == null) {
            String message = "File can't be null";
            logger.error(message);
            throw new IllegalStateException(message);
        }
        String timeStampPath = addTimeStamp(file.getPath());
        try (OutputStream out = new FileOutputStream(timeStampPath)) {
            file.getWorkbook().write(out);
            this.file = null;
        } catch (IOException e) {
            logger.error("Error during saving file", e);
            throw new IllegalStateException(e);
        }
    }

    private String addTimeStamp(String path) {
        int dotIndex = path.indexOf('.');
        String extension = path.substring(dotIndex);
        return path.substring(0, dotIndex) + new Date().getTime() + extension;
    }
}
