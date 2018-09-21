package com.hospital.registration.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 医疗卡
 *
 * @author SONG
 */
public class Cards implements Serializable {
  private static final long serialVersionUID = 5402846449513292208L;
  private Integer cid;
  private String pname;
  private String gender;
  private String phone;
  private String idcard;
  private String pwd;

  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
  private Date newdate;
  private Double ramaining;
  private Integer doexist;

  public Cards() {
  }

  public Integer getCid() {
    return cid;
  }

  public void setCid(Integer cid) {
    this.cid = cid;
  }

  public String getPname() {
    return pname;
  }

  public void setPname(String pname) {
    this.pname = pname;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getIdcard() {
    return idcard;
  }

  public void setIdcard(String idcard) {
    this.idcard = idcard;
  }

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

  public Date getNewdate() {
    return newdate;
  }

  public void setNewdate(Date newdate) {
    this.newdate = newdate;
  }

  public Double getRamaining() {
    return ramaining;
  }

  public void setRamaining(Double ramaining) {
    this.ramaining = ramaining;
  }

  public Integer getDoexist() {
    return doexist;
  }

  public void setDoexist(Integer doexist) {
    this.doexist = doexist;
  }
}
