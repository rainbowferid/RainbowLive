package org.common.constant;


public class Constants {

    public static final Integer ID_LENGTH = 10;
    public static final Integer THEME_DEFAULT = 1;
    public static final Integer COIN_DEFAULT = 5;
    public static final String REGISTER_PASSWORD_REGEX = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";

    public static final Integer REDIS_EXPIRE_TIME_MINUTE = 60000;
    public static final String REDIS_KEY_PREFIX = "rainbowLive:";
    public static final String REDIS_KEY_CHECK_CODE = REDIS_KEY_PREFIX+"checkCode:";



}
