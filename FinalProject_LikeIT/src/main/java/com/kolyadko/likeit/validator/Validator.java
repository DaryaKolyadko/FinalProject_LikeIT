package com.kolyadko.likeit.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DaryaKolyadko on 01.08.2016.
 */
public abstract class Validator {
    protected static final Pattern ID_PATTERN = Pattern.compile("^\\d+$");
    protected static final Pattern BOOLEAN_PATTERN = Pattern.compile("^true|false$");

    protected static boolean isMatching(String str, Pattern pattern) {
        if (str == null)
            return false;

        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean isNumIdValid(String sectionId) {
        return isMatching(sectionId, ID_PATTERN);
    }

    public static boolean isBooleanValid(String sectionId) {
        return isMatching(sectionId, BOOLEAN_PATTERN);
    }
}