package com.kolyadko.likeit.validator;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/**
 * Created by DaryaKolyadko on 01.08.2016.
 */
public class SignUpValidator extends Validator {
    private static final Pattern STRING_PATTERN = Pattern.compile("^[A-Za-z \\pL{А-я}]+$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z][_A-Za-z0-9-\\.]*@([A-Za-z]+\\.)[A-Za-z]{2,4}$");
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    public static boolean isStringValid(String str) {
        return isMatching(str, STRING_PATTERN) && !StringUtils.isBlank(str);
    }

    public static boolean isEmailValid(String email) {
        return isMatching(email, EMAIL_PATTERN);
    }

    public static boolean isDateValid(String date) {
        try {
            DATE_FORMAT.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isGenderValid(String gender) {
        return gender != null && !StringUtils.isBlank(gender);
    }
}