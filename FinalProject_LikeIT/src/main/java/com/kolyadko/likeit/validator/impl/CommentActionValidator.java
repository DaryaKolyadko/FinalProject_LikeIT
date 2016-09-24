package com.kolyadko.likeit.validator.impl;

import com.kolyadko.likeit.validator.Validator;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * Created by DaryaKolyadko on 30.08.2016.
 */

/**
 * Validator for some actions with comment
 */
public class CommentActionValidator extends Validator {
    private static final Pattern COMMENT_TEXT_PATTERN = Pattern.compile("^[\\s\\S]{1,2000}$");

    /**
     * Check comment text format
     *
     * @param text comment text
     * @return true - valid<br>false - invalid
     */
    public static boolean isTextValid(String text) {
        return isMatching(text, COMMENT_TEXT_PATTERN) && !StringUtils.isBlank(text);
    }
}