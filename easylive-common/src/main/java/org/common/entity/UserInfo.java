package org.common.entity;

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
  private java.sql.Timestamp lastLoginIp;
  private long status;
  private String noticeInfo;
  private long totalCoinCount;
  private long currentCoinCount;
  private long theme;


  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }


  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public long getSex() {
    return sex;
  }

  public void setSex(long sex) {
    this.sex = sex;
  }


  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }


  public String getSchool() {
    return school;
  }

  public void setSchool(String school) {
    this.school = school;
  }


  public String getPersonIntroduction() {
    return personIntroduction;
  }

  public void setPersonIntroduction(String personIntroduction) {
    this.personIntroduction = personIntroduction;
  }


  public java.sql.Timestamp getJoinTime() {
    return joinTime;
  }

  public void setJoinTime(java.sql.Timestamp joinTime) {
    this.joinTime = joinTime;
  }


  public java.sql.Timestamp getLastLoginTime() {
    return lastLoginTime;
  }

  public void setLastLoginTime(java.sql.Timestamp lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
  }


  public java.sql.Timestamp getLastLoginIp() {
    return lastLoginIp;
  }

  public void setLastLoginIp(java.sql.Timestamp lastLoginIp) {
    this.lastLoginIp = lastLoginIp;
  }


  public long getStatus() {
    return status;
  }

  public void setStatus(long status) {
    this.status = status;
  }


  public String getNoticeInfo() {
    return noticeInfo;
  }

  public void setNoticeInfo(String noticeInfo) {
    this.noticeInfo = noticeInfo;
  }


  public long getTotalCoinCount() {
    return totalCoinCount;
  }

  public void setTotalCoinCount(long totalCoinCount) {
    this.totalCoinCount = totalCoinCount;
  }


  public long getCurrentCoinCount() {
    return currentCoinCount;
  }

  public void setCurrentCoinCount(long currentCoinCount) {
    this.currentCoinCount = currentCoinCount;
  }


  public long getTheme() {
    return theme;
  }

  public void setTheme(long theme) {
    this.theme = theme;
  }

}
