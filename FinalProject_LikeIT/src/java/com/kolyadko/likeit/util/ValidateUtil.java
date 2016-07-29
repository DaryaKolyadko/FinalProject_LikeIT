package com.kolyadko.likeit.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DaryaKolyadko on 29.07.2016.
 */
public class ValidateUtil {
    private static final Pattern LOGIN_PATTERN = Pattern.compile("^[A-Za-z][_A-Za-z0-9-\\.]*$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[A-Za-z][_A-Za-z0-9-\\.]*$");
    private static final Pattern STRING_PATTERN = Pattern.compile("^[A-Za-z \\pL{А-я}]+$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z][_A-Za-z0-9-\\.]*@([A-Za-z]+\\.)[A-Za-z]{2,4}$");
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    public static boolean isLoginValid(String login) {
        return isMatching(login, LOGIN_PATTERN);
    }

    public static boolean isPasswordValid(String password) {
        return isMatching(password, PASSWORD_PATTERN);
    }

    public static boolean isStringValid(String str) {
        return isMatching(str, STRING_PATTERN);
    }

    public static boolean isEmailValid(String email) {
        return isMatching(email, EMAIL_PATTERN);
    }

    // TODO evaluate it with different locales
    public static boolean isDateValid(String date) {
        try {
            Date value = DATE_FORMAT.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private static boolean isMatching(String str, Pattern pattern) {
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}