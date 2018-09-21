package com.hospital.registration.controller;

import com.hospital.registration.domain.*;
import com.hospital.registration.service.BookableService;
import com.hospital.registration.service.DoctorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 排班控制器类
 *
 * @author SONG
 */
@RestController
@RequestMapping("/api")
public class BookableController {
  @Autowired
  private BookableService bookableService;

  @Autowired
  private DoctorsService doctorsService;

  /**
   * 查询科室所有医生的周排班情况
   *
   * @param deid
   * @param datetime
   * @return
   */
  @PostMapping("/bookabledepartdoctor")
  public ResponseEntity<?> findBookableByDepart(@RequestParam(value = "deid") Integer deid,
                                                @RequestParam(value = "datetime")
                                                @DateTimeFormat(pattern = "yyyy-MM-dd") Date datetime) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(datetime);
    Calendar calendar2 = Calendar.getInstance();
    calendar2.setTime(datetime);
    List<String> list = printWeekdays(calendar);

    List<WeekBean> weekBeanList = bookableService.findBookable(list, deid); //排班
    List<String> weekList = onlyWeek(calendar2); //日期_列名

    return new ResponseEntity<>(new BookDay(deid, datetime, weekBeanList, weekList), HttpStatus.OK);
  }

  /**
   * 查询科室所有医生的周排班情况
   *
   * @param deid
   * @param datetime
   * @return
   */
  @PostMapping("/bookablealldoctor")
  public ResponseEntity<?> findBookableAllDoctor(@RequestParam(value = "deid") Integer deid,
                                                 @RequestParam(value = "datetime")
                                                 @DateTimeFormat(pattern = "yyyy-MM-dd") Date datetime) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(datetime);
    Calendar calendar2 = Calendar.getInstance();
    calendar2.setTime(datetime);
    List<String> list = printWeekdays(calendar);

    List<WeekBean> weekBeanList = bookableService.findBookable(list, deid); //排班

    int result = 0;//判断是否已排班
    for (WeekBean weekBean : weekBeanList) {
      if (!StringUtils.isEmpty(addReg(weekBean).trim()))
        result = 1;
    }

    return new ResponseEntity<>(new Info(result), HttpStatus.OK);
  }

  /**
   * 增加排班
   *
   * @param deid
   * @param datetime
   * @return
   */
  @RequestMapping("/bookableadd")
  public ResponseEntity<?> add(@RequestParam(value = "deid") Integer deid,
                               @RequestParam(value = "datetime")
                               @DateTimeFormat(pattern = "yyyy-MM-dd") Date datetime) {
    List<Doctors> doctorList = bookableService.findDoctors(deid);
    //日期列表
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(datetime);
    List<String> bklist = printWeekdays(calendar);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    insertBK(doctorList, sdf, bklist);

    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * 删除排班
   *
   * @param doid
   * @param datetime
   * @return
   */
  @PostMapping("/bookabledelete")
  public ResponseEntity<?> delete(@RequestParam(value = "doid") Integer doid,
                                  @RequestParam(value = "datetime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date datetime) {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(datetime);
    List<String> bklist = printWeekdays(calendar);//需删除排班的周日期list
    String afterDate = getDateAfter(7);//当前时间7天后的时间,也就是已经上线的可预约时间

    Doctors doct = doctorsService.find(doid);
    Integer deid = doct.getDeid();//科室id

    int result = 0;
    //如果所需删除时间全部在已上线时间前,禁止删除
    if (afterDate.compareTo(bklist.get(6)) >= 0) {
      result = -1; // before
    }
    //如果所需删除时间都未上线则全部删除
    else if (afterDate.compareTo(bklist.get(0)) < 0) {
      //医生列表
      List<Doctors> dolist = bookableService.findDoctors(deid);
      System.out.println(dolist.size() + "存在的医生个数—————");
      //循环删除所选周内该科室所有医生的排班
      for (int i = 0; i < 7; i++) {
        for (Doctors d : dolist) {
          try {
            Date date1 = sdf.parse(bklist.get(i));
            bookableService.deleteBookable(d.getDoid(), date1);
          } catch (ParseException e) {
            e.printStackTrace();
          }
        }
      }
      result = 1; // after
    }
    //删除周排班中有一部分已上线，则重排未上线部分
    else {
      //可用医生列表
      List<Doctors> dolist = bookableService.findDoctors(deid);

      //判断哪天为可修改日期
      List<String> udlist = new ArrayList<String>();
      for (int i = 0; i < 7; i++) {
        if (afterDate.compareTo(bklist.get(i)) < 0) {
          udlist.add(bklist.get(i));
        }
      }

      //删除未上线日期排班
      for (int i = 0; i < udlist.size(); i++) {

        for (Doctors d : dolist) {
          try {
            Date date1 = sdf.parse(udlist.get(i));
            bookableService.deleteBookable(d.getDoid(), date1);
          } catch (ParseException e) {
            e.printStackTrace();
          }
        }
      }

      insertBK(dolist, sdf, udlist);
      result = 1;// center
    }

    if (result > 0)
      result = deid;

    return new ResponseEntity<>(new Info(result), HttpStatus.OK);
  }

  //设置一周第一天为星期天
  private static final int FIRST_DAY = Calendar.SUNDAY;

  private static void setToFirstDay(Calendar calendar) {
    while (calendar.get(Calendar.DAY_OF_WEEK) != FIRST_DAY) {
      calendar.add(Calendar.DATE, -1);
    }
  }

  private static List<String> printWeekdays(Calendar calendar) {
    List<String> list = new ArrayList<>();
    setToFirstDay(calendar);
    for (int i = 0; i < 7; i++) {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      String s = dateFormat.format(calendar.getTime());
      list.add(s);
      calendar.add(Calendar.DATE, 1);
    }
    return list;
  }

  private static List<String> onlyWeek(Calendar calendar) {
    List<String> list = new ArrayList<>();
    setToFirstDay(calendar);
    for (int i = 0; i < 7; i++) {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  EE");
      String s = dateFormat.format(calendar.getTime());
      list.add(s);
      calendar.add(Calendar.DATE, 1);
    }
    return list;
  }

  //依次插入星期X排班情况
  private void insertBK(List<Doctors> dolist, SimpleDateFormat sdf, List<String> bklist) {
    Collections.reverse(bklist);
    System.out.println("dolist.size()...." + dolist.size());
    System.out.println("bklist.size()...." + bklist.size());
    for (Doctors doc : dolist) {

      int size = bklist.size();

      //判断周六上午是否上班
      if (doc.getSatam() != null && doc.getSatam() == 1 && size > 0) {
        try {
          Date date2 = sdf.parse(bklist.get(0));
          Bookable bk = new Bookable();

          bk.setDoctors(doc); //排班医生
          bk.setBdate(date2); //排班日期
          bk.setBnum(doc.getPcreg() * 4); //网上可预约数
          bk.setYnum(0);//网上已预约人数
          bk.setXcum(doc.getXcreg() * 4);//现场可预约数
          bk.setXcyum(0);//现场已预约数量
          bk.setStarttime(-1); //上午
          bk.setUsed(0);
          bookableService.addBookable(bk);
        } catch (ParseException e) {
          e.printStackTrace();
        }
      }
      //判断周六下午是否上班
      if (doc.getSatpm() != null && doc.getSatpm() == 1 && size > 0) {
        try {
          Date date2 = sdf.parse(bklist.get(0));
          Bookable bk = new Bookable();

          bk.setDoctors(doc); //排班医生
          bk.setBdate(date2); //排班日期
          bk.setBnum(doc.getPcreg() * 3); //网上可预约数
          bk.setYnum(0);//网上已预约人数
          bk.setXcum(doc.getXcreg() * 3);//现场可预约数
          bk.setXcyum(0);//现场已预约数量
          bk.setStarttime(1); //下午
          bk.setUsed(0);
          bookableService.addBookable(bk);
        } catch (ParseException e) {
          e.printStackTrace();
        }
      }

      //判断周五上午是否上班
      if (doc.getFriam() != null && doc.getFriam() == 1 && size > 1) {
        try {
          Date date2 = sdf.parse(bklist.get(1));
          Bookable bk = new Bookable();

          bk.setDoctors(doc); //排班医生
          bk.setBdate(date2); //排班日期
          bk.setBnum(doc.getPcreg() * 4); //网上可预约数
          bk.setYnum(0);//网上已预约人数
          bk.setXcum(doc.getXcreg() * 4);//现场可预约数
          bk.setXcyum(0);//现场已预约数量
          bk.setStarttime(-1); //上午
          bk.setUsed(0);
          bookableService.addBookable(bk);
        } catch (ParseException e) {
          e.printStackTrace();
        }
      }
      //判断周五下午是否上班
      if (doc.getFripm() != null && doc.getFripm() == 1 && size > 1) {
        try {
          Date date2 = sdf.parse(bklist.get(1));
          Bookable bk = new Bookable();

          bk.setDoctors(doc); //排班医生
          bk.setBdate(date2); //排班日期
          bk.setBnum(doc.getPcreg() * 3); //网上可预约数
          bk.setYnum(0);//网上已预约人数
          bk.setXcum(doc.getXcreg() * 3);//现场可预约数
          bk.setXcyum(0);//现场已预约数量
          bk.setStarttime(1); //下午
          bk.setUsed(0);
          bookableService.addBookable(bk);
        } catch (ParseException e) {
          e.printStackTrace();
        }
      }
      //判断周四上午是否上班
      if (doc.getThuam() != null && doc.getThuam() == 1 && size > 2) {
        try {
          Date date2 = sdf.parse(bklist.get(2));
          Bookable bk = new Bookable();

          bk.setDoctors(doc); //排班医生
          bk.setBdate(date2); //排班日期
          bk.setBnum(doc.getPcreg() * 4); //网上可预约数
          bk.setYnum(0);//网上已预约人数
          bk.setXcum(doc.getXcreg() * 4);//现场可预约数
          bk.setXcyum(0);//现场已预约数量
          bk.setStarttime(-1); //上午
          bk.setUsed(0);
          bookableService.addBookable(bk);
        } catch (ParseException e) {
          e.printStackTrace();
        }
      }
      //判断周四下午是否上班
      if (doc.getThupm() != null && doc.getThupm() == 1 && size > 2) {
        try {
          Date date2 = sdf.parse(bklist.get(2));
          Bookable bk = new Bookable();

          bk.setDoctors(doc); //排班医生
          bk.setBdate(date2); //排班日期
          bk.setBnum(doc.getPcreg() * 3); //网上可预约数
          bk.setYnum(0);//网上已预约人数
          bk.setXcum(doc.getXcreg() * 3);//现场可预约数
          bk.setXcyum(0);//现场已预约数量
          bk.setStarttime(1); //下午
          bk.setUsed(0);
          bookableService.addBookable(bk);
        } catch (ParseException e) {
          e.printStackTrace();
        }
      }
      //判断周三上午是否上班
      if (doc.getWedam() != null && doc.getWedam() == 1 && size > 3) {
        try {
          Date date2 = sdf.parse(bklist.get(3));
          Bookable bk = new Bookable();

          bk.setDoctors(doc); //排班医生
          bk.setBdate(date2); //排班日期
          bk.setBnum(doc.getPcreg() * 4); //网上可预约数
          bk.setYnum(0);//网上已预约人数
          bk.setXcum(doc.getXcreg() * 4);//现场可预约数
          bk.setXcyum(0);//现场已预约数量
          bk.setStarttime(-1); //上午
          bk.setUsed(0);
          bookableService.addBookable(bk);
        } catch (ParseException e) {
          e.printStackTrace();
        }
      }
      //判断周三下午是否上班
      if (doc.getWedpm() != null && doc.getWedpm() == 1 && size > 3) {
        try {
          Date date2 = sdf.parse(bklist.get(3));
          Bookable bk = new Bookable();

          bk.setDoctors(doc); //排班医生
          bk.setBdate(date2); //排班日期
          bk.setBnum(doc.getPcreg() * 3); //网上可预约数
          bk.setYnum(0);//网上已预约人数
          bk.setXcum(doc.getXcreg() * 3);//现场可预约数
          bk.setXcyum(0);//现场已预约数量
          bk.setStarttime(1); //下午
          bk.setUsed(0);
          bookableService.addBookable(bk);
        } catch (ParseException e) {
          e.printStackTrace();
        }
      }

      //判断周二上午是否上班
      if (doc.getTueam() != null && doc.getTueam() == 1 && size > 4) {
        try {
          Date date2 = sdf.parse(bklist.get(4));
          Bookable bk = new Bookable();

          bk.setDoctors(doc); //排班医生
          bk.setBdate(date2); //排班日期
          bk.setBnum(doc.getPcreg() * 4); //网上可预约数
          bk.setYnum(0);//网上已预约人数
          bk.setXcum(doc.getXcreg() * 4);//现场可预约数
          bk.setXcyum(0);//现场已预约数量
          bk.setStarttime(-1); //上午
          bk.setUsed(0);
          bookableService.addBookable(bk);
        } catch (ParseException e) {
          e.printStackTrace();
        }
      }
      //判断周二下午是否上班
      if (doc.getTuepm() != null && doc.getTuepm() == 1 && size > 4) {
        try {
          Date date2 = sdf.parse(bklist.get(4));
          Bookable bk = new Bookable();

          bk.setDoctors(doc); //排班医生
          bk.setBdate(date2); //排班日期
          bk.setBnum(doc.getPcreg() * 3); //网上可预约数
          bk.setYnum(0);//网上已预约人数
          bk.setXcum(doc.getXcreg() * 3);//现场可预约数
          bk.setXcyum(0);//现场已预约数量
          bk.setStarttime(1); //下午
          bk.setUsed(0);
          bookableService.addBookable(bk);
        } catch (ParseException e) {
          e.printStackTrace();
        }
      }
      //判断周一上午是否上班
      if (doc.getMonam() != null && doc.getMonam() == 1 && size > 5) {
        try {
          Date date2 = sdf.parse(bklist.get(5));
          Bookable bk = new Bookable();

          bk.setDoctors(doc); //排班医生
          bk.setBdate(date2); //排班日期
          bk.setBnum(doc.getPcreg() * 4); //网上可预约数
          bk.setYnum(0);//网上已预约人数
          bk.setXcum(doc.getXcreg() * 4);//现场可预约数
          bk.setXcyum(0);//现场已预约数量
          bk.setStarttime(-1); //上午
          bk.setUsed(0);
          bookableService.addBookable(bk);
        } catch (ParseException e) {
          e.printStackTrace();
        }
      }
      //判断周一下午是否上班
      if (doc.getMonpm() != null && doc.getMonpm() == 1 && size > 5) {
        try {
          Date date2 = sdf.parse(bklist.get(5));
          Bookable bk = new Bookable();

          bk.setDoctors(doc); //排班医生
          bk.setBdate(date2); //排班日期
          bk.setBnum(doc.getPcreg() * 3); //网上可预约数
          bk.setYnum(0);//网上已预约人数
          bk.setXcum(doc.getXcreg() * 3);//现场可预约数
          bk.setXcyum(0);//现场已预约数量
          bk.setStarttime(1); //下午
          bk.setUsed(0);
          bookableService.addBookable(bk);
        } catch (ParseException e) {
          e.printStackTrace();
        }
      }
      //判断周日上午是否上班
      if (doc.getSunam() != null && doc.getSunam() == 1 && size > 6) {
        try {
          Date date2 = sdf.parse(bklist.get(6));
          Bookable bk = new Bookable();

          bk.setDoctors(doc); //排班医生
          bk.setBdate(date2); //排班日期
          bk.setBnum(doc.getPcreg() * 4); //网上可预约数
          bk.setYnum(0);//网上已预约人数
          bk.setXcum(doc.getXcreg() * 4);//现场可预约数
          bk.setXcyum(0);//现场已预约数量
          bk.setStarttime(-1); //上午
          bk.setUsed(0);
          bookableService.addBookable(bk);
        } catch (ParseException e) {
          e.printStackTrace();
        }
      }

      //判断周日下午是否上班
      if (doc.getSumpm() != null && doc.getSumpm() == 1 && size > 6) {
        try {
          Date date2 = sdf.parse(bklist.get(6));
          Bookable bk = new Bookable();

          bk.setDoctors(doc); //排班医生
          bk.setBdate(date2); //排班日期
          bk.setBnum(doc.getPcreg() * 3); //网上可预约数
          bk.setYnum(0);//网上已预约人数
          bk.setXcum(doc.getXcreg() * 3);//现场可预约数
          bk.setXcyum(0);//现场已预约数量
          bk.setStarttime(1); //下午
          bk.setUsed(0);
          bookableService.addBookable(bk);
        } catch (ParseException e) {
          e.printStackTrace();
        }
      }
    }
  }

  //返回该医生一周是否值班
  private String addReg(WeekBean wk) {
    return wk.getAreg1() + wk.getAreg2() + wk.getAreg3() + wk.getAreg4() + wk.getAreg5()
            + wk.getAreg6() + wk.getAreg7() + wk.getPreg1() + wk.getPreg2() + wk.getPreg3() + wk.getPreg4()
            + wk.getPreg5() + wk.getPreg6() + wk.getPreg7();
  }

  /*
   * 当前日期N天后的时间
   */
  private String getDateAfter(int day) {
    Calendar now = Calendar.getInstance();
    now.setTime(new Date());
    now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String s = dateFormat.format(now.getTime());
    return s;
  }
}
