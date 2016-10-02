package com.kolyadko.likeit.util;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

/**
 * Created by DaryaKolyadko on 13.09.2016.
 */

/**
 * Test for MappingManager
 */
public class MappingManagerTest {
    @Before
    public void beforeEachTest() throws Exception{
        Field field = MappingManager.class.getDeclaredField("initialized");
        field.setAccessible(true);
        field.set(null, new AtomicBoolean(false));
        field.setAccessible(false);
    }

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