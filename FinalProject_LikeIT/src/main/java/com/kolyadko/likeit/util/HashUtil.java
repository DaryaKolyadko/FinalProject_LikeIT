package com.kolyadko.likeit.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by DaryaKolyadko on 16.05.2016.
 */
public class HashUtil {
    public static String getHash(String str) {
        return DigestUtils.sha512Hex(str);
    }
}