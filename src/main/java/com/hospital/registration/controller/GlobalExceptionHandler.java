package com.hospital.registration.controller;

import com.hospital.registration.domain.ErrorInfo;
import com.hospital.registration.exception.HospitalException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一异常处理类
 *
 * @author SONG
 */
@ControllerAdvice
public class GlobalExceptionHandler {
  public static final String DEFAULT_ERROR_VIEW = "error";

  /**
   * 默认异常处理
   *
   * @param req
   * @param e
   * @return
   * @throws HospitalException
   */
  /*@ExceptionHandler(value = HospitalException.class)
  public ModelAndView defaultErrorHandler(HttpServletRequest req, HospitalException e) throws Exception {
    ModelAndView mav = new ModelAndView();
    mav.addObject("exception", e);
    mav.addObject("url", req.getRequestURL());
    mav.setViewName(DEFAULT_ERROR_VIEW);
    return mav;
  }*/

  /**
   * restful 异常处理
   *
   * @param req
   * @param e
   * @return
   * @throws Exception
   */
  @ExceptionHandler(value = HospitalException.class)
  @ResponseBody
  public ErrorInfo<String> jsonErrorHandler(HttpServletRequest req, HospitalException e) throws Exception {
    ErrorInfo<String> r = new ErrorInfo<>();
    r.setMessage(e.getMessage());
    r.setCode(ErrorInfo.ERROR);
    r.setData("Some Data");
    r.setUrl(req.getRequestURL().toString());
    return r;
  }
}
