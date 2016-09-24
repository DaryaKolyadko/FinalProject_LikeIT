package com.kolyadko.likeit.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by DaryaKolyadko on 16.05.2016.
 */

/**
 * Class for hashing data
 */
public class HashUtil {
    /**
     * Hash string
     *
     * @param str string
     * @return hash
     */
    public static String getHash(String str) {
        return DigestUtils.sha512Hex(str);
    }
}