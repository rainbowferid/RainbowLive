package org.common.enums;

public enum UserSexEnum {
    MALE(1,"男"),
    FEMALE(0,"女"),
    SECRECT(2,"保密");

    private Integer sex;
    private String desc;

    private UserSexEnum(Integer sex, String desc) {
        this.sex = sex;
        this.desc = desc;
    }
    public static UserSexEnum getDescBySex(Integer sex) {
        for (UserSexEnum userSexEnum : UserSexEnum.values()) {
            if (userSexEnum.getSex().equals(sex)) {
                return userSexEnum;
            }
        }
        return null;
    }
    public Integer getSex() {
        return sex;
    }
    public String  getDesc() {
        return desc;
    }
}
