package com.kolyadko.likeit.pool;

import com.kolyadko.likeit.exception.ConnectionPoolException;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Queue;

import static org.junit.Assert.*;

/**
 * Created by DaryaKolyadko on 12.09.2016.
 */
public class ConnectionPoolTest {
    @Test
    public void testGetInstance() throws Exception {
        int tryNumber = 10;
        ConnectionPool[] poolList = new ConnectionPool[tryNumber];

        for (int i = 0; i < tryNumber; i++) {
            poolList[i] = ConnectionPool.getInstance();
        }

        for (int i = 0; i < tryNumber - 1; i++) {
            assertEquals(poolList[i], poolList[i + 1]);
        }
    }

    @Test(timeout = 2000)
    public void testGetConnection() throws Exception {
        ConnectionProxy proxy = ConnectionPool.getInstance().getConnection();
        assertNotNull(proxy);
    }

//    @Test(expected = ConnectionPoolException.class)
//    public void testGetConnectionNegative() throws Exception {
//        DatabaseConnector.setConfigFileName("noSuchFile.properties");
//        ConnectionPool.getInstance();
//    }

    @Test
    public void testCloseConnection() throws Exception {
        ConnectionPool pool = ConnectionPool.getInstance();
        ConnectionProxy proxy = pool.getConnection();
        Class clazz = pool.getClass();
        Field poolSize = clazz.getDeclaredField("POOL_SIZE");
        poolSize.setAccessible(true);
        int size = poolSize.getInt(pool);
        Field connections = clazz.getDeclaredField("connectionsAvailable");
        connections.setAccessible(true);
        int availableCount = ((Queue) connections.get(pool)).size();
        assertEquals(size, availableCount + 1);
        pool.closeConnection(proxy);
        availableCount = ((Queue) connections.get(pool)).size();
        assertEquals(size, availableCount);
    }

//    @Test
//    public void testCloseAll() throws Exception {
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Class clazz = pool.getClass();
//        Field poolSize = clazz.getDeclaredField("POOL_SIZE");
//        poolSize.setAccessible(true);
//        int size = poolSize.getInt(pool);
//        Field connections = clazz.getDeclaredField("connectionsAvailable");
//        connections.setAccessible(true);
//        int availableCount = ((Queue) connections.get(pool)).size();
//        assertEquals(size, availableCount);
//        pool.closeAll();
//        availableCount = ((Queue) connections.get(pool)).size();
//        assertEquals(0, availableCount);
//    }
}