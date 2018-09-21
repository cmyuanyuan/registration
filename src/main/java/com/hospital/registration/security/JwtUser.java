package com.hospital.registration.security;

import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 安全服务的用户
 * 需要实现UserDetails接口,用户实体即为Spring Security所使用的用户
 *
 * @author SONG
 */
public class JwtUser implements UserDetails {

  private final Integer id;
  private final String username;
  private final String password;
  private final String email;
  private final Integer state;
  private final Integer doid;
  private final Collection<? extends GrantedAuthority> authorities;
  private final boolean enabled;
  private final Date lastPasswordResetDate;
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private final Date loginTime;
  private final String by1;

  public JwtUser(
          Integer id,
          String username,
          String email,
          Integer state,
          Integer doid,
          String password, Collection<? extends GrantedAuthority> authorities,
          boolean enabled,
          Date lastPasswordResetDate,
          Date loginTime,
          String by1
  ) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.state = state;
    this.doid = doid;
    this.password = password;
    this.authorities = authorities;
    this.enabled = enabled;
    this.lastPasswordResetDate = lastPasswordResetDate;
    this.loginTime = loginTime;
    this.by1 = by1;
  }

  @JsonIgnore
  public Integer getId() {
    return id;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  public String getEmail() {
    return email;
  }

  @JsonIgnore
  @Override
  public String getPassword() {
    return password;
  }

  public Integer getState() {
    return state;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  @JsonIgnore
  public Date getLastPasswordResetDate() {
    return lastPasswordResetDate;
  }

  public Date getLoginTime() {
    return loginTime;
  }

  public String getBy1() {
    return by1;
  }

  public Integer getDoid() {
    return doid;
  }
}
