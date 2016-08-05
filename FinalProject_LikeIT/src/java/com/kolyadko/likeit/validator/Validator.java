package com.kolyadko.likeit.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DaryaKolyadko on 01.08.2016.
 */
public abstract class Validator {
    protected static boolean isMatching(String str, Pattern pattern) {
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}