package com.hospital.registration.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 部门统计
 *
 * @author SONG
 */
public class BooksDepart implements Serializable {
  private static final long serialVersionUID = 1L;
  private List<String> times;
  private List<BooksDepartCount> booksDepartCounts;

  public BooksDepart() {
  }

  public BooksDepart(List<String> times, List<BooksDepartCount> booksDepartCounts) {
    this.times = times;
    this.booksDepartCounts = booksDepartCounts;
  }

  public List<String> getTimes() {
    return times;
  }

  public void setTimes(List<String> times) {
    this.times = times;
  }

  public List<BooksDepartCount> getBooksDepartCounts() {
    return booksDepartCounts;
  }

  public void setBooksDepartCounts(List<BooksDepartCount> booksDepartCounts) {
    this.booksDepartCounts = booksDepartCounts;
  }
}
