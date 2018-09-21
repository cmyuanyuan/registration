package com.hospital.registration.security;

import java.util.List;
import java.util.stream.Collectors;

import com.hospital.registration.domain.Admins;
import com.hospital.registration.domain.Authority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * @author SONG
 */
public final class JwtUserFactory {

  private JwtUserFactory() {
  }

  public static JwtUser create(Admins user) {
    return new JwtUser(
            user.getAid(),
            user.getAname(),
            user.getEmail(),
            user.getState(),
            user.getDoid(),
            user.getPwd(),
            mapToGrantedAuthorities(user.getAuthorities()),
            user.getAexist() == 1 ? true : false,
            user.getLastPasswordResetDate(),
            user.getLoginTime(),
            user.getBy1()
    );
  }

  private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
    return authorities.stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
            .collect(Collectors.toList());
  }
}
