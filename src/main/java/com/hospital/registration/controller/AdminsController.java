package com.hospital.registration.controller;

import com.hospital.registration.domain.Admins;
import com.hospital.registration.domain.Doctors;
import com.hospital.registration.domain.Info;
import com.hospital.registration.security.JwtTokenUtil;
import com.hospital.registration.security.JwtUser;
import com.hospital.registration.service.DoctorsService;
import com.hospital.registration.service.AdminsService;
import com.hospital.registration.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 用户控制器类
 *
 * @author SONG
 */
@RestController
@RequestMapping("/api")
public class AdminsController {
  @Autowired
  private AdminsService adminsService;

  @Autowired
  private DoctorsService doctorsService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Value("${jwt.header}")
  private String tokenHeader;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private UserDetailsService userDetailsService;


  /**
   * 登录
   *
   * @param aname 用户名
   * @param pwd   密码
   * @return
   */
  @Deprecated
  @RequestMapping(value = "/users", method = RequestMethod.GET)
  public ResponseEntity<?> getEmp(@RequestParam("aname") String aname,
                                  @RequestParam("pwd") String pwd,
                                  @RequestParam("verifyCode") String verifyCode,
                                  HttpSession session) {
    String securityCode = (String) session.getAttribute("securityCode");
    if (verifyCode.equals(securityCode)) {
      // 加密
      Admins admins = adminsService.find(aname, MD5.getInstance().getMD5ofStr(pwd));
      if (admins == null) {
        return new ResponseEntity(new CustomErrorType("User with id " + aname + " not found"), HttpStatus.OK);
      }

      // 当医生登录时获取 title
      if (admins.getState() == 2) {
        Doctors doctors = doctorsService.find(admins.getDoid());

        if (doctors != null)
          admins.setBy1(doctors.getTitle());
        else
          admins.setBy1("医生头衔");
      }
      session.setAttribute("admins", admins);
      return new ResponseEntity<Admins>(admins, HttpStatus.OK);
    } else {
      return new ResponseEntity(new CustomErrorType("Verify Code is invalid"), HttpStatus.OK);
    }
  }

  /**
   * 修改密码
   *
   * @param password
   * @return
   */
  @RequestMapping(value = "/changepwd", method = RequestMethod.POST)
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> changePassword(@RequestParam("password") String password,
                                          HttpServletRequest request) {
    String token = request.getHeader(tokenHeader).substring(7);
    String username = jwtTokenUtil.getUsernameFromToken(token);
    JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

    password = passwordEncoder.encode(password);

    adminsService.changePassword(user.getId(), password);
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @RequestMapping(value = "/admins/{state}", method = RequestMethod.GET)
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> getAdmins(@PathVariable("state") Integer state) {
    List<Admins> adminsList = adminsService.getAdmins(state);
    return new ResponseEntity<>(adminsList, HttpStatus.OK);
  }

  @PostMapping("/adminschangestate")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> changeState(
          @RequestParam("aid") Integer aid,
          @RequestParam("aexist") Integer aexist) {
    int result = adminsService.changeState(aexist, aid);
    return new ResponseEntity<>(new Info(result), HttpStatus.OK);
  }

  @RequestMapping(value = "/admins", method = RequestMethod.POST)
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> addAdmin(@RequestBody Admins admins) {
    admins.setPwd(passwordEncoder.encode(admins.getPwd()));
    int result = adminsService.add(admins);
    return new ResponseEntity<>(new Info(result), HttpStatus.OK);
  }

  @Deprecated
  @RequestMapping(value = "/logout", method = RequestMethod.GET)
  public ResponseEntity<?> logout(HttpSession session) {
    if (session.getAttribute("admins") != null) {
      session.removeAttribute("admins");
      session.invalidate();
    }
    return new ResponseEntity<>("ok", HttpStatus.OK);
  }
}
