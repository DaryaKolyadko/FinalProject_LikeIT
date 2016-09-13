package com.kolyadko.likeit.validator.impl;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by DaryaKolyadko on 12.09.2016.
 */
public class CommentActionValidatorTest {
    @Test
    public void testIsTextValid() throws Exception {
        assertTrue(CommentActionValidator.isTextValid("a"));
        assertTrue(CommentActionValidator.isTextValid("0"));
        assertTrue(CommentActionValidator.isTextValid(StringUtils.repeat("a", 2000)));
        assertFalse(CommentActionValidator.isTextValid(StringUtils.repeat("a", 2001)));
        assertFalse(CommentActionValidator.isTextValid(""));
        assertFalse(CommentActionValidator.isTextValid("   "));
        assertFalse(CommentActionValidator.isTextValid(null));
    }
}