package com.hospital.registration.domain;

import java.util.Date;

/**
 * 病例表
 *
 * @author SONG
 */
public class History implements java.io.Serializable {
  private static final long serialVersionUID = 1L;
  private Integer hiid;
  private Prescripton prescripton;
  private Doctors doctors;
  private Cards cards;
  private Date hidate;
  private String brief;
  private Integer deal;
  private String by1;
  private Integer by2;

  public History() {
  }

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public Integer getHiid() {
    return hiid;
  }

  public void setHiid(Integer hiid) {
    this.hiid = hiid;
  }

  public Prescripton getPrescripton() {
    return prescripton;
  }

  public void setPrescripton(Prescripton prescripton) {
    this.prescripton = prescripton;
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

  public Date getHidate() {
    return hidate;
  }

  public void setHidate(Date hidate) {
    this.hidate = hidate;
  }

  public String getBrief() {
    return brief;
  }

  public void setBrief(String brief) {
    this.brief = brief;
  }

  public Integer getDeal() {
    return deal;
  }

  public void setDeal(Integer deal) {
    this.deal = deal;
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
