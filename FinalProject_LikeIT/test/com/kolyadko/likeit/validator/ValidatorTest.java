package com.kolyadko.likeit.validator;

import org.junit.Test;
import java.util.regex.Pattern;

import static org.junit.Assert.*;


/**
 * Created by DaryaKolyadko on 12.09.2016.
 */

/**
 * Test for Validator
 */
public class ValidatorTest {
    @Test
    public void testIsMatching() throws Exception {
        assertTrue(Validator.isMatching("pool", Pattern.compile("[p|t]ool")));
        assertFalse(Validator.isMatching("cool", Pattern.compile("[p|t]ool")));
        assertFalse(Validator.isMatching(null, Pattern.compile("tralala")));
    }

    @Test
    public void testIsNumIdValid() throws Exception {
        assertTrue(Validator.isNumIdValid("1"));
        assertTrue(Validator.isNumIdValid("123"));
        assertFalse(Validator.isNumIdValid("-1"));
        assertFalse(Validator.isNumIdValid("123abc"));
        assertFalse(Validator.isNumIdValid("abc"));
        assertFalse(Validator.isNumIdValid(null));
    }

    @Test
    public void testIsBooleanValid() throws Exception {
        assertTrue(Validator.isBooleanValid("true"));
        assertTrue(Validator.isBooleanValid("false"));
        assertFalse(Validator.isBooleanValid("true "));
        assertFalse(Validator.isBooleanValid(" false"));
        assertFalse(Validator.isBooleanValid("TRUE"));
        assertFalse(Validator.isBooleanValid("FALSE"));
        assertFalse(Validator.isBooleanValid(null));
    }
}