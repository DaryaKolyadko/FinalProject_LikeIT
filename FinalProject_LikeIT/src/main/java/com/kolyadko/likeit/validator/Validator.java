package com.kolyadko.likeit.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DaryaKolyadko on 01.08.2016.
 */

/**
 * Abstract validator with general methods
 */
public abstract class Validator {
    /**
     * Pattern for number id
     */
    protected static final Pattern ID_PATTERN = Pattern.compile("^\\d+$");
    /**
     * Pattern for boolean value
     */
    protected static final Pattern BOOLEAN_PATTERN = Pattern.compile("^true|false$");

    /**
     * Check if string matches regex pattern
     *
     * @param str     string to check
     * @param pattern regex pattern
     * @return true - ok<br>false - not ok
     */
    protected static boolean isMatching(String str, Pattern pattern) {
        if (str == null)
            return false;

        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * Check number id format
     *
     * @param sectionId section id
     * @return true - ok<br>false - not ok
     */
    public static boolean isNumIdValid(String sectionId) {
        return isMatching(sectionId, ID_PATTERN);
    }

    /**
     * Check boolean value format
     *
     * @param sectionId section id
     * @return true - ok<br>false - not ok
     */
    public static boolean isBooleanValid(String sectionId) {
        return isMatching(sectionId, BOOLEAN_PATTERN);
    }
}