package com.kolyadko.likeit.validator.impl;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by DaryaKolyadko on 12.09.2016.
 */
public class SectionActionValidatorTest {
    @Test
    public void testIsSectionNameValid() throws Exception {
        assertTrue(SectionActionValidator.isSectionNameValid("h"));
        assertTrue(SectionActionValidator.isSectionNameValid("uyh 875454 jj vfd =-0"));
        assertTrue(SectionActionValidator.isSectionNameValid(StringUtils.repeat("k", 30)));
        assertFalse(SectionActionValidator.isSectionNameValid(StringUtils.repeat("k", 31)));
        assertFalse(SectionActionValidator.isSectionNameValid(""));
        assertFalse(SectionActionValidator.isSectionNameValid("     "));
        assertFalse(SectionActionValidator.isSectionNameValid(null));
    }

    @Test
    public void testIsHexColorValid() throws Exception {
        assertTrue(SectionActionValidator.isHexColorValid("#A23Fcf"));
        assertTrue(SectionActionValidator.isHexColorValid("#111111"));
        assertTrue(SectionActionValidator.isHexColorValid("#ffaabb"));
        assertTrue(SectionActionValidator.isHexColorValid("#aaa"));
        assertTrue(SectionActionValidator.isHexColorValid("#1aa"));
        assertFalse(SectionActionValidator.isHexColorValid("dd11ff"));
        assertFalse(SectionActionValidator.isHexColorValid("12a"));
        assertFalse(SectionActionValidator.isHexColorValid("#ff"));
        assertFalse(SectionActionValidator.isHexColorValid("#ff11"));
        assertFalse(SectionActionValidator.isHexColorValid("#ff141"));
    }
}