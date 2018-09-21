package com.hospital.registration.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 管理员实体类
 *
 * @author SONG
 */
public class Admins implements Serializable {
  private static final long serialVersionUID = 1L;
  private Integer aid;
  private Doctors doctors;
  private String aname;
  private String pwd;
  private Integer state;
  private Integer aexist;
  private Integer doid;

  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private Date loginTime;
  private String by1;
  private Integer by2;

  private String email;
  private Date lastPasswordResetDate;
  private List<Authority> authorities;

  public Admins() {
  }

  public Integer getAid() {
    return aid;
  }

  public void setAid(Integer aid) {
    this.aid = aid;
  }

  public Doctors getDoctors() {
    return doctors;
  }

  public void setDoctors(Doctors doctors) {
    this.doctors = doctors;
  }

  public String getAname() {
    return aname;
  }

  public void setAname(String aname) {
    this.aname = aname;
  }

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public Integer getAexist() {
    return aexist;
  }

  public void setAexist(Integer aexist) {
    this.aexist = aexist;
  }

  public Integer getDoid() {
    return doid;
  }

  public void setDoid(Integer doid) {
    this.doid = doid;
  }

  public Date getLoginTime() {
    return loginTime;
  }

  public void setLoginTime(Date loginTime) {
    this.loginTime = loginTime;
  }

  public String getBy1() {
    return by1;
  }

  public void setBy1(String by1) {
    this.by1 = by1;
  }

  public Integer getBy2() {
    return by2;
  }

  public void setBy2(Integer by2) {
    this.by2 = by2;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getLastPasswordResetDate() {
    return lastPasswordResetDate;
  }

  public void setLastPasswordResetDate(Date lastPasswordResetDate) {
    this.lastPasswordResetDate = lastPasswordResetDate;
  }

  public List<Authority> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(List<Authority> authorities) {
    this.authorities = authorities;
  }
}
