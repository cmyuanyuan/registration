package com.hospital.registration.security.service;

import com.hospital.registration.domain.Admins;
import com.hospital.registration.domain.Doctors;
import com.hospital.registration.mapper.AdminsMapper;
import com.hospital.registration.security.JwtUserFactory;
import com.hospital.registration.service.DoctorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * @author SONG
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private AdminsMapper adminsMapper;

  @Autowired
  private DoctorsService doctorsService;

  /**
   * 从用户名可以查到用户
   *
   * @param username
   * @return
   * @throws UsernameNotFoundException
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Admins user = adminsMapper.findByUsername(username);

    if (user == null) {
      throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
    } else {
      // 修改登录日期
      adminsMapper.updateLoginTime(user.getAid());
      user.setLoginTime(new Date());

      // 当医生登录时获取 title
      if (user.getState() == 2) {
        Doctors doctors = doctorsService.find(user.getDoid());

        if (doctors != null)
          user.setBy1(doctors.getTitle());
        else {
          user.setBy1("医生头衔");
          user.setDoid(-1); // 非医生id
        }
      }

      return JwtUserFactory.create(user);
    }
  }
}