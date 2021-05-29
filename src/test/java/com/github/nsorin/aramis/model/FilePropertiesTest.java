package com.github.nsorin.aramis.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FilePropertiesTest {

    @Test
    void constructFilePropertiesWithPathAndName() {
        String location = "./test.txt";
        String name = "test.txt";

        FileProperties fileProperties = new FileProperties(location, name);

        assertEquals(location, fileProperties.getLocation());
        assertEquals(name, fileProperties.getName());
    }

    @Test
    void constructFilePropertiesWithoutPathNorName() {
        FileProperties fileProperties = new FileProperties();

        assertNull(fileProperties.getLocation());
        assertNull(fileProperties.getName());
    }

    @Test
    void isNewReturnsTrueIfNoLocation() {
        assertTrue(new FileProperties().isNew());
    }

    @Test
    void isNewReturnsFalseIfHasLocation() {
        assertFalse(new FileProperties("somepath", "somename").isNew());
    }
}
