package com.learn.words.props.impl;

import com.learn.words.props.PropertiesHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHandlerImpl implements PropertiesHandler {

    private static final String PATH = "source_files.properties";

    private static final Logger logger = Logger.getLogger(PropertiesHandlerImpl.class);

    private final Properties props;

    public PropertiesHandlerImpl() {
        this.props = new Properties();
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(PATH)) {
            this.props.load(in);
        } catch (IOException e) {
            logger.error("Error during load properties", e);
        }
        logger.info(String.format("%s created", this.getClass().getName()));
    }

    @Override
    public String loadProp(String key) {
        if (StringUtils.isBlank(key)) {
            logger.warn("Can't load props by empty key");
            return StringUtils.EMPTY;
        }
        return StringUtils.defaultString(this.props.getProperty(key).trim());
    }
}
