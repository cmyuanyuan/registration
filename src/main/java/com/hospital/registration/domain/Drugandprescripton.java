package com.hospital.registration.domain;

/**
 * 药品与药方关系表
 *
 * @author SONG
 */
public class Drugandprescripton implements java.io.Serializable {
  private static final long serialVersionUID = 1L;
  private Integer prid; // 药方id
  private Integer drid; // 药品id
  private Drug drug;  //药品
  private Integer drnum;//数量
  private Double sum;//小计价格
  private String by1;
  private Integer by2;
  private Prescripton prescripton;

  public Drugandprescripton() {
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

  public Integer getDrid() {
    return drid;
  }

  public void setDrid(Integer drid) {
    this.drid = drid;
  }

  public Drug getDrug() {
    return drug;
  }

  public void setDrug(Drug drug) {
    this.drug = drug;
  }

  public Integer getDrnum() {
    return drnum;
  }

  public void setDrnum(Integer drnum) {
    this.drnum = drnum;
  }

  public Double getSum() {
    return sum;
  }

  public void setSum(Double sum) {
    this.sum = sum;
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

  public Prescripton getPrescripton() {
    return prescripton;
  }

  public void setPrescripton(Prescripton prescripton) {
    this.prescripton = prescripton;
  }
}
