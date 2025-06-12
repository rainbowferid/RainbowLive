package org.common.utils;


import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.DigestUtils;

import java.util.Random;

public class StringTools {

    public static final String encodeByMd5(String str){
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }
    public static final String getRandomNumber(Integer length) {
        return RandomStringUtils.random(length, false, true);
    }
}
