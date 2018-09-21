package com.hospital.registration.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 药品实体类
 *
 * @author SONG
 */
public class Drug implements Serializable {
  private static final long serialVersionUID = 1L;
  private Integer dyid;
  private Integer drid;
  private Drugtype drugtype;
  private String drname;
  private Integer drsum;
  private Double drprice;
  private Integer drstate;
  private String by1;
  private Integer by2;
  private String dename;
  private Set drugandprescriptons = new HashSet(0);
  private Set druganddepartses = new HashSet(0);

  public Drug() {
  }

  public Drug(Integer drid, String drname, Integer drsum, Double drprice, Integer drstate, Integer dyid) {
    this.drid = drid;
    this.drname = drname;
    this.drsum = drsum;
    this.drprice = drprice;
    this.drstate = drstate;
    this.dyid = dyid;
  }

  public Integer getDyid() {
    return dyid;
  }

  public void setDyid(Integer dyid) {
    this.dyid = dyid;
  }

  public Integer getDrid() {
    return drid;
  }

  public void setDrid(Integer drid) {
    this.drid = drid;
  }

  public Drugtype getDrugtype() {
    return drugtype;
  }

  public void setDrugtype(Drugtype drugtype) {
    this.drugtype = drugtype;
  }

  public String getDrname() {
    return drname;
  }

  public String getDename() {
    return dename;
  }

  public void setDename(String dename) {
    this.dename = dename;
  }

  public void setDrname(String drname) {
    this.drname = drname;
  }

  public Integer getDrsum() {
    return drsum;
  }

  public void setDrsum(Integer drsum) {
    this.drsum = drsum;
  }

  public Double getDrprice() {
    return drprice;
  }

  public void setDrprice(Double drprice) {
    this.drprice = drprice;
  }

  public Integer getDrstate() {
    return drstate;
  }

  public void setDrstate(Integer drstate) {
    this.drstate = drstate;
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

  public Set getDrugandprescriptons() {
    return drugandprescriptons;
  }

  public void setDrugandprescriptons(Set drugandprescriptons) {
    this.drugandprescriptons = drugandprescriptons;
  }

  public Set getDruganddepartses() {
    return druganddepartses;
  }

  public void setDruganddepartses(Set druganddepartses) {
    this.druganddepartses = druganddepartses;
  }
}
