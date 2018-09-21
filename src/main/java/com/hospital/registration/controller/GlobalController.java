package com.hospital.registration.controller;

import com.hospital.registration.exception.HospitalException;
import com.hospital.registration.domain.Admins;
import com.hospital.registration.util.SecurityCode;
import com.hospital.registration.util.SecurityImage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 全局控制器:非 restful
 */
@RestController
@RequestMapping("/api")
public class GlobalController {

  /**
   * 获取用户的会话
   *
   * @param session
   * @return
   */
  @Deprecated
  @RequestMapping(value = "/usersession", method = RequestMethod.GET)
  public ResponseEntity<?> getSession(HttpSession session) {
    Admins admins = (Admins) session.getAttribute("admins");
    return new ResponseEntity<Admins>(admins, HttpStatus.OK);
  }

  @RequestMapping("/imagecode")
  public void getImageCode(HttpSession session, HttpServletResponse response) {
    try {
      // 如果开启Hard模式，可以不区分大小写
      // String securityCode =
      // SecurityCode.getSecurityCode(4,SecurityCodeLevel.Hard,
      // false).toLowerCase();

      // 获取默认难度和长度的验证码
      String securityCode = SecurityCode.getSecurityCode();
      session.setAttribute("securityCode", securityCode);

      response.setContentType("image/jpeg");
      response.setHeader("Pragma", "No-cache");
      response.setHeader("Cache-Control", "no-cache");
      response.setDateHeader("Expires", 0);

      ImageIO.write(SecurityImage.getImage(securityCode), "JPEG", response.getOutputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @RequestMapping("/global_json")
  public String json() throws HospitalException {
    throw new HospitalException("发生错误");
  }
}
