package com.hospital.registration.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 排班数据
 *
 * @author SONG
 */
public class BookDay implements Serializable {
  private static final long serialVersionUID = -1880589394439750853L;
  private Integer deid;

  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
  private Date datetime;
  private List<WeekBean> weekBeanList;
  private List<String> weekList;

  public BookDay(Integer deid, Date datetime, List<WeekBean> weekBeanList, List<String> weekList) {
    this.deid = deid;
    this.datetime = datetime;
    this.weekBeanList = weekBeanList;
    this.weekList = weekList;
  }

  public Integer getDeid() {
    return deid;
  }

  public void setDeid(Integer deid) {
    this.deid = deid;
  }

  public Date getDatetime() {
    return datetime;
  }

  public void setDatetime(Date datetime) {
    this.datetime = datetime;
  }

  public List<WeekBean> getWeekBeanList() {
    return weekBeanList;
  }

  public void setWeekBeanList(List<WeekBean> weekBeanList) {
    this.weekBeanList = weekBeanList;
  }

  public List<String> getWeekList() {
    return weekList;
  }

  public void setWeekList(List<String> weekList) {
    this.weekList = weekList;
  }
}
