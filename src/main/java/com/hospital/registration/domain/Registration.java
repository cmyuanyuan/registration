package com.hospital.registration.domain;

/**
 * 挂号单表
 *
 * @author SONG
 */
public class Registration implements java.io.Serializable {
  private static final long serialVersionUID = 1L;
  private Integer rid;
  private Cards cards;
  private Bookable bookable;
  private Integer snum;
  private Integer state;
  private String by1;
  private Integer by2;

  public Registration() {
  }

  public static long getSerialVersionUID() {
    return serialVersionUID;
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

  public Bookable getBookable() {
    return bookable;
  }

  public void setBookable(Bookable bookable) {
    this.bookable = bookable;
  }

  public Integer getSnum() {
    return snum;
  }

  public void setSnum(Integer snum) {
    this.snum = snum;
  }

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
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
