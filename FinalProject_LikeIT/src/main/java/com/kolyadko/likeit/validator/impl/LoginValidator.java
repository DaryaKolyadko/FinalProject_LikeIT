package com.kolyadko.likeit.validator.impl;

import com.kolyadko.likeit.validator.Validator;

import java.util.regex.Pattern;

/**
 * Created by DaryaKolyadko on 01.08.2016.
 */

/**
 * Validator for log in action
 */
public class LoginValidator extends Validator {
    private static final Pattern LOGIN_PATTERN = Pattern.compile("^[A-Za-z][_A-Za-z0-9-\\.]{2,24}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[_A-Za-z0-9-\\.]{3,25}$");

    /**
     * Check login format
     *
     * @param login login string
     * @return true - valid<br>false - invalid
     */
    public static boolean isLoginValid(String login) {
        return isMatching(login, LOGIN_PATTERN);
    }

    /**
     * Check password format
     *
     * @param password password string
     * @return true - valid<br>false - invalid
     */
    public static boolean isPasswordValid(String password) {
        return isMatching(password, PASSWORD_PATTERN);
    }
}