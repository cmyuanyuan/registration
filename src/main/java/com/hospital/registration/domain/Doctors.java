package com.hospital.registration.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 医生实体类
 *
 * @author SONG
 */
public class Doctors implements Serializable {
  private static final long serialVersionUID = 1L;
  private Integer doid;
  private Departs departs;
  private String doname;
  private String aname;
  private String pwd;
  private String title;
  private String photo;
  private String info;
  private Integer monam;
  private Integer monpm;
  private Integer tueam;
  private Integer tuepm;
  private Integer wedam;
  private Integer wedpm;
  private Integer thuam;
  private Integer thupm;
  private Integer friam;
  private Integer fripm;
  private Integer satam;
  private Integer satpm;
  private Integer sunam;
  private Integer sumpm;
  private Integer pcreg;
  private Integer xcreg;
  private Integer doexist;
  private Double bcost;
  private String by1;
  private Integer by2;
  private Set prescriptons = new HashSet(0);
  private Set adminses = new HashSet(0);
  private Set histories = new HashSet(0);
  private Set bookables = new HashSet(0);
  private Integer deid;

  public Doctors() {
  }

  public Integer getDoid() {
    return doid;
  }

  public void setDoid(Integer doid) {
    this.doid = doid;
  }

  public Departs getDeparts() {
    return departs;
  }

  public void setDeparts(Departs departs) {
    this.departs = departs;
  }

  public String getDoname() {
    return doname;
  }

  public void setDoname(String doname) {
    this.doname = doname;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public Integer getMonam() {
    return monam;
  }

  public void setMonam(Integer monam) {
    this.monam = monam;
  }

  public Integer getMonpm() {
    return monpm;
  }

  public void setMonpm(Integer monpm) {
    this.monpm = monpm;
  }

  public Integer getTueam() {
    return tueam;
  }

  public void setTueam(Integer tueam) {
    this.tueam = tueam;
  }

  public Integer getTuepm() {
    return tuepm;
  }

  public void setTuepm(Integer tuepm) {
    this.tuepm = tuepm;
  }

  public Integer getWedam() {
    return wedam;
  }

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

  public void setWedam(Integer wedam) {
    this.wedam = wedam;
  }

  public Integer getWedpm() {
    return wedpm;
  }

  public void setWedpm(Integer wedpm) {
    this.wedpm = wedpm;
  }

  public Integer getThuam() {
    return thuam;
  }

  public void setThuam(Integer thuam) {
    this.thuam = thuam;
  }

  public Integer getThupm() {
    return thupm;
  }

  public void setThupm(Integer thupm) {
    this.thupm = thupm;
  }

  public Integer getFriam() {
    return friam;
  }

  public void setFriam(Integer friam) {
    this.friam = friam;
  }

  public Integer getFripm() {
    return fripm;
  }

  public void setFripm(Integer fripm) {
    this.fripm = fripm;
  }

  public Integer getSatam() {
    return satam;
  }

  public void setSatam(Integer satam) {
    this.satam = satam;
  }

  public Integer getSatpm() {
    return satpm;
  }

  public void setSatpm(Integer satpm) {
    this.satpm = satpm;
  }

  public Integer getSunam() {
    return sunam;
  }

  public void setSunam(Integer sunam) {
    this.sunam = sunam;
  }

  public Integer getSumpm() {
    return sumpm;
  }

  public void setSumpm(Integer sumpm) {
    this.sumpm = sumpm;
  }

  public Integer getPcreg() {
    return pcreg;
  }

  public void setPcreg(Integer pcreg) {
    this.pcreg = pcreg;
  }

  public Integer getXcreg() {
    return xcreg;
  }

  public void setXcreg(Integer xcreg) {
    this.xcreg = xcreg;
  }

  public Integer getDoexist() {
    return doexist;
  }

  public void setDoexist(Integer doexist) {
    this.doexist = doexist;
  }

  public Double getBcost() {
    return bcost;
  }

  public void setBcost(Double bcost) {
    this.bcost = bcost;
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

  public Set getPrescriptons() {
    return prescriptons;
  }

  public void setPrescriptons(Set prescriptons) {
    this.prescriptons = prescriptons;
  }

  public Set getAdminses() {
    return adminses;
  }

  public void setAdminses(Set adminses) {
    this.adminses = adminses;
  }

  public Set getHistories() {
    return histories;
  }

  public void setHistories(Set histories) {
    this.histories = histories;
  }

  public Set getBookables() {
    return bookables;
  }

  public void setBookables(Set bookables) {
    this.bookables = bookables;
  }

  public Integer getDeid() {
    return deid;
  }

  public void setDeid(Integer deid) {
    this.deid = deid;
  }

  public String getAname() {
    return aname;
  }

  public void setAname(String aname) {
    this.aname = aname;
  }
}
