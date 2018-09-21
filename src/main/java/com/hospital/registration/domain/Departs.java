package com.hospital.registration.domain;

import java.io.Serializable;
import java.util.*;

/**
 * 科室实体类
 *
 * @author SONG
 */
public class Departs implements Serializable {
  private static final long serialVersionUID = 1L;
  private Integer deid;
  private String dename;
  private String intro;
  private Integer deexist;
  private String by1;
  private Integer by2;
  private Set druganddepartses = new HashSet(0);
  private List<Doctors> doctorsList;
  //科室药方内药品集合
  private Map<Integer, Druganddeparts> items = new HashMap<Integer, Druganddeparts>();

  public Departs() {
  }

  public Integer getDeid() {
    return deid;
  }

  public void setDeid(Integer deid) {
    this.deid = deid;
  }

  public String getDename() {
    return dename;
  }

  public void setDename(String dename) {
    this.dename = dename;
  }

  public String getIntro() {
    return intro;
  }

  public void setIntro(String intro) {
    this.intro = intro;
  }

  public Integer getDeexist() {
    return deexist;
  }

  public void setDeexist(Integer deexist) {
    this.deexist = deexist;
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

  public Set getDruganddepartses() {
    return druganddepartses;
  }

  public void setDruganddepartses(Set druganddepartses) {
    this.druganddepartses = druganddepartses;
  }

  public List<Doctors> getDoctorsList() {
    return doctorsList;
  }

  public void setDoctorsList(List<Doctors> doctorsList) {
    this.doctorsList = doctorsList;
  }

  public Map<Integer, Druganddeparts> getItems() {
    return items;
  }

  public void setItems(Map<Integer, Druganddeparts> items) {
    this.items = items;
  }
}
