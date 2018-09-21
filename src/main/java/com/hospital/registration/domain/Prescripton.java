package com.hospital.registration.domain;

import java.util.*;

/**
 * 药方表
 *
 * @author SONG
 */
public class Prescripton implements java.io.Serializable {
  private static final long serialVersionUID = 1L;
  private Integer prid;
  private Doctors doctors;
  private Cards cards;
  private Date prdate;
  private Double dtotal;
  private Integer drstate;
  private double total; //总价
  private String by1;
  private Integer by2;
  private Set drugandprescriptons = new HashSet(0);
  private Set histories = new HashSet(0);
  //订单药方内药品集合
  private Map<Integer, Drugandprescripton> items = new HashMap<Integer, Drugandprescripton>();

  public Prescripton() {
  }

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public Integer getPrid() {
    return prid;
  }

  public void setPrid(Integer prid) {
    this.prid = prid;
  }

  public Doctors getDoctors() {
    return doctors;
  }

  public void setDoctors(Doctors doctors) {
    this.doctors = doctors;
  }

  public Cards getCards() {
    return cards;
  }

  public void setCards(Cards cards) {
    this.cards = cards;
  }

  public Date getPrdate() {
    return prdate;
  }

  public void setPrdate(Date prdate) {
    this.prdate = prdate;
  }

  public Double getDtotal() {
    return dtotal;
  }

  public void setDtotal(Double dtotal) {
    this.dtotal = dtotal;
  }

  public Integer getDrstate() {
    return drstate;
  }

  public void setDrstate(Integer drstate) {
    this.drstate = drstate;
  }

  public double getTotal() {
    return total;
  }

  public void setTotal(double total) {
    this.total = total;
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

  public Set getHistories() {
    return histories;
  }

  public void setHistories(Set histories) {
    this.histories = histories;
  }

  public Map<Integer, Drugandprescripton> getItems() {
    return items;
  }

  public void setItems(Map<Integer, Drugandprescripton> items) {
    this.items = items;
  }
}
