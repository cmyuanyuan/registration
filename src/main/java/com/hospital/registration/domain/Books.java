package com.hospital.registration.domain;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author SONG
 */
public class Books implements Serializable {
  private static final long serialVersionUID = 1L;
  private Integer red;
  private String pname;
  private String idcard;
  private Integer medcard;
  private String dename;
  private String doname;

  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
  private Date bdate;
  private Double bcost;
  private String phone;
  private Integer starttime;
  private Integer snum;
  private Integer bid;

  public Books() {
  }

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public Integer getRed() {
    return red;
  }

  public void setRed(Integer red) {
    this.red = red;
  }

  public String getPname() {
    return pname;
  }

  public void setPname(String pname) {
    this.pname = pname;
  }

  public String getIdcard() {
    return idcard;
  }

  public void setIdcard(String idcard) {
    this.idcard = idcard;
  }

  public Integer getMedcard() {
    return medcard;
  }

  public void setMedcard(Integer medcard) {
    this.medcard = medcard;
  }

  public String getDename() {
    return dename;
  }

  public void setDename(String dename) {
    this.dename = dename;
  }

  public String getDoname() {
    return doname;
  }

  public void setDoname(String doname) {
    this.doname = doname;
  }

  public Date getBdate() {
    return bdate;
  }

  public void setBdate(Date bdate) {
    this.bdate = bdate;
  }

  public Double getBcost() {
    return bcost;
  }

  public void setBcost(Double bcost) {
    this.bcost = bcost;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Integer getStarttime() {
    return starttime;
  }

  public void setStarttime(Integer starttime) {
    this.starttime = starttime;
  }

  public Integer getSnum() {
    return snum;
  }

  public void setSnum(Integer snum) {
    this.snum = snum;
  }

  public Integer getBid() {
    return bid;
  }

  public void setBid(Integer bid) {
    this.bid = bid;
  }
}
