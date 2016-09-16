package com.kolyadko.likeit.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by DaryaKolyadko on 12.09.2016.
 */

/**
 * Test for HashUtil
 */
public class HashUtilTest {
    @Test
    public void testGetHash() throws Exception {
        assertEquals(HashUtil.getHash("lol"), HashUtil.getHash("lol"));
        assertEquals(HashUtil.getHash(""), HashUtil.getHash(""));
    }
}