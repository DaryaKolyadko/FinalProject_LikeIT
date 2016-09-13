package com.kolyadko.likeit.validator.impl;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by DaryaKolyadko on 12.09.2016.
 */
public class SignUpValidatorTest {
    @Test
    public void testIsStringValid() throws Exception {
        assertTrue(SignUpValidator.isStringValid("a"));
        assertTrue(SignUpValidator.isStringValid("az"));
        assertTrue(SignUpValidator.isStringValid("a Z"));
        assertTrue(SignUpValidator.isStringValid("шЖп"));
        assertTrue(SignUpValidator.isStringValid("рпО  fFf"));
        assertFalse(SignUpValidator.isStringValid("f J 34"));
        assertFalse(SignUpValidator.isStringValid("frh арП ="));
        assertFalse(SignUpValidator.isStringValid(""));
        assertFalse(SignUpValidator.isStringValid("   "));
        assertFalse(SignUpValidator.isStringValid(null));
    }

    @Test
    public void testIsEmailValid() throws Exception {
        assertTrue(SignUpValidator.isEmailValid("a@b.bb"));
        assertTrue(SignUpValidator.isEmailValid("ab@a.aa"));
        assertTrue(SignUpValidator.isEmailValid("v_@f.aa"));
        assertTrue(SignUpValidator.isEmailValid("d0@f.ff"));
        assertTrue(SignUpValidator.isEmailValid("d0_@rr.cc"));
        assertFalse(SignUpValidator.isEmailValid("@xx.xx"));
        assertFalse(SignUpValidator.isEmailValid("1@a23ddd.aa"));
        assertFalse(SignUpValidator.isEmailValid("_@a.aa"));
        assertFalse(SignUpValidator.isEmailValid("aa@."));
        assertFalse(SignUpValidator.isEmailValid("aa@.ff"));
        assertFalse(SignUpValidator.isEmailValid("aa@a.f"));
        assertFalse(SignUpValidator.isEmailValid("aa@a.f1"));
        assertFalse(SignUpValidator.isEmailValid("aa@a.fffff"));
        assertFalse(SignUpValidator.isEmailValid(""));
        assertFalse(SignUpValidator.isEmailValid("   "));
        assertFalse(SignUpValidator.isEmailValid(null));
    }

    @Test
    public void testIsDateValid() throws Exception {
        assertTrue(SignUpValidator.isDateValid("11/22/1963"));
        assertTrue(SignUpValidator.isDateValid("09/11/2006"));
        assertTrue(SignUpValidator.isDateValid("11/11/1111"));
        assertTrue(SignUpValidator.isDateValid("01/01/9999"));
        assertTrue(SignUpValidator.isDateValid("01/01/30000"));
        assertFalse(SignUpValidator.isDateValid("01/01/0000"));
        assertFalse(SignUpValidator.isDateValid("01/91/2000"));
        assertFalse(SignUpValidator.isDateValid("51/01/2000"));
        assertFalse(SignUpValidator.isDateValid("00/01/2000"));
        assertFalse(SignUpValidator.isDateValid("00/00/2000"));
        assertFalse(SignUpValidator.isDateValid("date"));
        assertFalse(SignUpValidator.isDateValid("22.11.1963"));
        assertFalse(SignUpValidator.isDateValid(null));
    }

    @Test
    public void testIsGenderValid() throws Exception {
        assertTrue(SignUpValidator.isGenderValid("fEmaLe"));
        assertTrue(SignUpValidator.isGenderValid("female"));
        assertTrue(SignUpValidator.isGenderValid("MALE"));
        assertTrue(SignUpValidator.isGenderValid("otheR"));
        assertFalse(SignUpValidator.isGenderValid("man"));
        assertFalse(SignUpValidator.isGenderValid("woman"));
        assertFalse(SignUpValidator.isGenderValid(" male"));
        assertFalse(SignUpValidator.isGenderValid(" female "));
        assertFalse(SignUpValidator.isGenderValid("null"));
    }
}