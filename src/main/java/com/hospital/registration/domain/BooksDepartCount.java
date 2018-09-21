package com.hospital.registration.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 部门统计: 今天 昨天 本周 本月 本季度
 *
 * @author SONG
 */
public class BooksDepartCount implements Serializable {
  private static final long serialVersionUID = 1L;
  private String dename;
  private List<Integer> countList;

  public BooksDepartCount() {
  }

  public BooksDepartCount(String dename, List<Integer> countList) {
    this.dename = dename;
    this.countList = countList;
  }

  public String getDename() {
    return dename;
  }

  public void setDename(String dename) {
    this.dename = dename;
  }

  public List<Integer> getCountList() {
    return countList;
  }

  public void setCountList(List<Integer> countList) {
    this.countList = countList;
  }
}
