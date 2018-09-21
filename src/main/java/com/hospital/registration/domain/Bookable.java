package com.hospital.registration.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author SONG
 */
public class Bookable implements Serializable {
  private static final long serialVersionUID = 1L;
  private Integer bid;
  private Integer doid;

  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private Date bdate;
  private Integer xcum;
  private Integer xcyum;
  private Double bcost;
  private String doname;
  private String dename;
  private Integer snum;
  private String pname;
  private Integer medcard;

  private Doctors doctors;
  private Integer starttime;
  private Integer used;
  private Integer bnum;
  private Integer ynum;
  private String by1;
  private Integer by2;
  private Set registrations = new HashSet(0);
  private Set reservations = new HashSet(0);

  public Bookable() {
  }

  public Integer getBid() {
    return bid;
  }

  public void setBid(Integer bid) {
    this.bid = bid;
  }

  public Integer getDoid() {
    return doid;
  }

  public void setDoid(Integer doid) {
    this.doid = doid;
  }

  public Date getBdate() {
    return bdate;
  }

  public void setBdate(Date bdate) {
    this.bdate = bdate;
  }

  public Integer getXcum() {
    return xcum;
  }

  public void setXcum(Integer xcum) {
    this.xcum = xcum;
  }

  public Integer getXcyum() {
    return xcyum;
  }

  public void setXcyum(Integer xcyum) {
    this.xcyum = xcyum;
  }

  public Double getBcost() {
    return bcost;
  }

  public void setBcost(Double bcost) {
    this.bcost = bcost;
  }

  public String getDoname() {
    return doname;
  }

  public void setDoname(String doname) {
    this.doname = doname;
  }

  public String getDename() {
    return dename;
  }

  public void setDename(String dename) {
    this.dename = dename;
  }

  public Integer getSnum() {
    return snum;
  }

  public void setSnum(Integer snum) {
    this.snum = snum;
  }

  public String getPname() {
    return pname;
  }

  public void setPname(String pname) {
    this.pname = pname;
  }

  public Integer getMedcard() {
    return medcard;
  }

  public void setMedcard(Integer medcard) {
    this.medcard = medcard;
  }

  public Doctors getDoctors() {
    return doctors;
  }

  public void setDoctors(Doctors doctors) {
    this.doctors = doctors;
  }

  public Integer getStarttime() {
    return starttime;
  }

  public void setStarttime(Integer starttime) {
    this.starttime = starttime;
  }

  public Integer getUsed() {
    return used;
  }

  public void setUsed(Integer used) {
    this.used = used;
  }

  public Integer getBnum() {
    return bnum;
  }

  public void setBnum(Integer bnum) {
    this.bnum = bnum;
  }

  public Integer getYnum() {
    return ynum;
  }

  public void setYnum(Integer ynum) {
    this.ynum = ynum;
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

  public Set getRegistrations() {
    return registrations;
  }

  public void setRegistrations(Set registrations) {
    this.registrations = registrations;
  }

  public Set getReservations() {
    return reservations;
  }

  public void setReservations(Set reservations) {
    this.reservations = reservations;
  }
}
