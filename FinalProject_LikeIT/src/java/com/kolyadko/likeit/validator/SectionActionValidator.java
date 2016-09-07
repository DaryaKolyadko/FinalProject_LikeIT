package com.kolyadko.likeit.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * Created by DaryaKolyadko on 30.08.2016.
 */
public class SectionActionValidator extends Validator {
    private static final Pattern SECTION_NAME_PATTERN = Pattern.compile("^.{1,30}$");
    private static final Pattern HEX_COLOR_PATTERN = Pattern.compile("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");

    public static boolean isSectionNameValid(String sectionName) {
        return isMatching(sectionName, SECTION_NAME_PATTERN) && !StringUtils.isBlank(sectionName);
    }

    public static boolean isHexColorValid(String hexColor) {
        return isMatching(hexColor, HEX_COLOR_PATTERN);
    }
}