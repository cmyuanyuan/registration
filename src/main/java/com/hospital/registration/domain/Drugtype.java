package com.hospital.registration.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 药品类别类
 *
 * @author SONG
 */
public class Drugtype implements Serializable {
  private static final long serialVersionUID = 1L;
  private Integer dyid;
  private String dyname;
  private Integer dystate;
  private String by1;
  private Integer by2;
  private Set drugs = new HashSet(0);

  public Drugtype() {
  }

  public Integer getDyid() {
    return dyid;
  }

  public void setDyid(Integer dyid) {
    this.dyid = dyid;
  }

  public String getDyname() {
    return dyname;
  }

  public void setDyname(String dyname) {
    this.dyname = dyname;
  }

  public Integer getDystate() {
    return dystate;
  }

  public void setDystate(Integer dystate) {
    this.dystate = dystate;
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

  public Set getDrugs() {
    return drugs;
  }

  public void setDrugs(Set drugs) {
    this.drugs = drugs;
  }
}
