package com.hospital.registration.domain;

import java.io.Serializable;

/**
 * @author SONG
 */
public class MedicalState implements Serializable {
  private String by1;
  private Integer by2;
  private Integer state;
  private Integer rid;
  private Cards cards;

  public MedicalState() {
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

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public Integer getRid() {
    return rid;
  }

  public void setRid(Integer rid) {
    this.rid = rid;
  }

  public Cards getCards() {
    return cards;
  }

  public void setCards(Cards cards) {
    this.cards = cards;
  }
}
