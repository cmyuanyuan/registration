package com.hospital.registration.domain;

import java.io.Serializable;

/**
 * 角色
 *
 * @author SONG
 */
public class Authority implements Serializable {
  private static final long serialVersionUID = 4465908907298694845L;
  private Integer id;
  private AuthorityName name;

  public Authority() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public AuthorityName getName() {
    return name;
  }

  public void setName(AuthorityName name) {
    this.name = name;
  }
}
