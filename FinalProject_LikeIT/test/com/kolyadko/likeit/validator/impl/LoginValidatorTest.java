package com.kolyadko.likeit.validator.impl;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by DaryaKolyadko on 12.09.2016.
 */

/**
 * Test for LoginValidator
 */
public class LoginValidatorTest {
    @Test
    public void testIsLoginValid() throws Exception {
        assertTrue(LoginValidator.isLoginValid("azazaza"));
        assertTrue(LoginValidator.isLoginValid("A_z"));
        assertTrue(LoginValidator.isLoginValid("Z_a"));
        assertTrue(LoginValidator.isLoginValid("a_1."));
        assertTrue(LoginValidator.isLoginValid("AA12_12zAZ"));
        assertTrue(LoginValidator.isLoginValid("az-1"));
        assertTrue(LoginValidator.isLoginValid(StringUtils.repeat("a_", 12)));
        assertTrue(LoginValidator.isLoginValid(StringUtils.repeat("r", 25)));
        assertFalse(LoginValidator.isLoginValid(StringUtils.repeat("t", 26)));
        assertFalse(LoginValidator.isLoginValid("a"));
        assertFalse(LoginValidator.isLoginValid("aZ"));
        assertFalse(LoginValidator.isLoginValid("_az"));
        assertFalse(LoginValidator.isLoginValid("1az"));
        assertFalse(LoginValidator.isLoginValid("1"));
        assertFalse(LoginValidator.isLoginValid("  "));
        assertFalse(LoginValidator.isLoginValid(""));
        assertFalse(LoginValidator.isLoginValid(null));
    }

    @Test
    public void testIsPasswordValid() throws Exception {
        assertTrue(LoginValidator.isPasswordValid("azazaza"));
        assertTrue(LoginValidator.isPasswordValid("A_z"));
        assertTrue(LoginValidator.isPasswordValid("Z_a"));
        assertTrue(LoginValidator.isPasswordValid("a_1."));
        assertTrue(LoginValidator.isPasswordValid("AA12_12zAZ"));
        assertTrue(LoginValidator.isPasswordValid("az-1"));
        assertTrue(LoginValidator.isPasswordValid(StringUtils.repeat("3h", 12)));
        assertTrue(LoginValidator.isPasswordValid(StringUtils.repeat("0", 25)));
        assertFalse(LoginValidator.isPasswordValid(StringUtils.repeat("q", 26)));
        assertFalse(LoginValidator.isPasswordValid("a#"));
        assertFalse(LoginValidator.isPasswordValid("11"));
        assertFalse(LoginValidator.isPasswordValid("a"));
        assertFalse(LoginValidator.isPasswordValid("aZ"));
        assertFalse(LoginValidator.isPasswordValid("1"));
        assertFalse(LoginValidator.isPasswordValid("  "));
        assertFalse(LoginValidator.isPasswordValid(""));
        assertFalse(LoginValidator.isPasswordValid(null));
    }
}