package com.kolyadko.likeit.validator.impl;

import com.kolyadko.likeit.validator.Validator;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * Created by DaryaKolyadko on 30.08.2016.
 */

/**
 * Validator for some actions with question
 */
public class QuestionActionValidator extends Validator {
    private static final Pattern QUESTION_TITLE_PATTERN = Pattern.compile("^.{1,65}$");
    private static final Pattern QUESTION_TEXT_PATTERN = Pattern.compile("^[\\s\\S]{1,4000}$");

    /**
     * Check question title format
     *
     * @param title question title
     * @return true - valid<br>false - invalid
     */
    public static boolean isTitleValid(String title) {
        return isMatching(title, QUESTION_TITLE_PATTERN) && !StringUtils.isBlank(title);
    }

    /**
     * Check question text format
     *
     * @param text question text
     * @return true - valid<br>false - invalid
     */
    public static boolean isTextValid(String text) {
        return isMatching(text, QUESTION_TEXT_PATTERN) && !StringUtils.isBlank(text);
    }
}