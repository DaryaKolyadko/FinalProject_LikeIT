package com.kolyadko.likeit.validator.impl;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by DaryaKolyadko on 12.09.2016.
 */

/**
 * Test for QuestionActionValidator
 */
public class QuestionActionValidatorTest {
    @Test
    public void testIsTitleValid() throws Exception {
        assertTrue(QuestionActionValidator.isTitleValid("a"));
        assertTrue(QuestionActionValidator.isTitleValid("ab cb  - = uuif &&"));
        assertTrue(QuestionActionValidator.isTitleValid(StringUtils.repeat("b", 65)));
        assertFalse(QuestionActionValidator.isTitleValid(StringUtils.repeat("b", 66)));
        assertFalse(QuestionActionValidator.isTitleValid(""));
        assertFalse(QuestionActionValidator.isTitleValid("     "));
        assertFalse(QuestionActionValidator.isTitleValid(null));
    }

    @Test
    public void testIsTextValid() throws Exception {
        assertTrue(QuestionActionValidator.isTextValid("b"));
        assertTrue(QuestionActionValidator.isTextValid("hvfh 788 e =-  &** 77fv"));
        assertTrue(QuestionActionValidator.isTextValid(StringUtils.repeat("b", 4000)));
        assertFalse(QuestionActionValidator.isTextValid(StringUtils.repeat("b", 4001)));
        assertFalse(QuestionActionValidator.isTextValid(""));
        assertFalse(QuestionActionValidator.isTextValid("     "));
        assertFalse(QuestionActionValidator.isTextValid(null));
    }
}