package com.learn.words.props.impl;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesHandlerImplTest {

    final String PATH_TO_PROPS = "src/test/resources/source_files.properties";


    @Test
    void init() {
        assertDoesNotThrow(() -> new PropertiesHandlerImpl(PATH_TO_PROPS));
        assertThrows(IllegalStateException.class, () -> new PropertiesHandlerImpl("invalid path"));
    }

    @Test
    void loadProp() {
        PropertiesHandlerImpl handler = new PropertiesHandlerImpl(PATH_TO_PROPS);
        assertEquals(StringUtils.EMPTY, handler.loadProp(";asldkfj;lasdjf;a"));
        assertEquals(StringUtils.EMPTY, handler.loadProp(StringUtils.EMPTY));
        assertEquals(StringUtils.EMPTY, handler.loadProp(StringUtils.SPACE));
        assertNotEquals(StringUtils.EMPTY, handler.loadProp("source.list.name"));
    }
}