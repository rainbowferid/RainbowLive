package org.common.utils;


import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class StringTools {
    public static final String getRandomNumber(Integer length) {
        return RandomStringUtils.random(length, false, true);
    }
}
