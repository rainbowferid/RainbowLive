package org.common.entity.po;

import lombok.Data;

@Data
public class UserInfo {

  private String userId;
  private String nickName;
  private String email;
  private String password;
  private long sex;
  private String birthday;
  private String school;
  private String personIntroduction;
  private java.sql.Timestamp joinTime;
  private java.sql.Timestamp lastLoginTime;
  private String lastLoginIp;
  private long status;
  private String noticeInfo;
  private long totalCoinCount;
  private long currentCoinCount;
  private long theme;

}
