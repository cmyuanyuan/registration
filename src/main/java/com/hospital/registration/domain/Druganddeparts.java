package com.hospital.registration.domain;

import java.io.Serializable;

/**
 * 药品科室关系类
 *
 * @author SONG
 */
public class Druganddeparts implements Serializable {
  private static final long serialVersionUID = 1L;
  private Integer drid;
  private Integer deid;
  private Drug drug;
  private Departs departs;
  private String by1;
  private Integer by2;

  public Druganddeparts() {
  }

  public Integer getDrid() {
    return drid;
  }

  public void setDrid(Integer drid) {
    this.drid = drid;
  }

  public Integer getDeid() {
    return deid;
  }

  public void setDeid(Integer deid) {
    this.deid = deid;
  }

  public Drug getDrug() {
    return drug;
  }

  public void setDrug(Drug drug) {
    this.drug = drug;
  }

  public Departs getDeparts() {
    return departs;
  }

  public void setDeparts(Departs departs) {
    this.departs = departs;
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
}
