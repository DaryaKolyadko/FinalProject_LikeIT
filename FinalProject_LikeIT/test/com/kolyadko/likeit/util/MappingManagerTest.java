package com.kolyadko.likeit.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by DaryaKolyadko on 13.09.2016.
 */
public class MappingManagerTest {
    @Test
    public void testGetInstance() throws Exception {
        int tryNumber = 10;
        MappingManager.setConfigFileName("mapping.properties");
        MappingManager[] mappingManagers = new MappingManager[tryNumber];

        for (int i = 0; i < tryNumber; i++) {
            mappingManagers[i] = MappingManager.getInstance();
        }

        for (int i = 0; i < tryNumber - 1; i++) {
            assertEquals(mappingManagers[i], mappingManagers[i + 1]);
        }
    }

    @Test(expected = RuntimeException.class)
    public void testGetInstanceNegative() throws Exception {
        MappingManager.setConfigFileName("noSuchFile.properties");
        MappingManager.getInstance();
    }

    @Test
    public void testGetProperty() throws Exception {
        MappingManager.setConfigFileName("mapping.properties");
        MappingManager manager = MappingManager.getInstance();
        assertNotNull(manager.getProperty(MappingManager.HOME_PAGE));
        assertNull(manager.getProperty("lalka"));
    }
}