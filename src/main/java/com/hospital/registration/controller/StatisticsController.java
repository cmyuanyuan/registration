package com.hospital.registration.controller;

import com.hospital.registration.domain.Drug;
import com.hospital.registration.domain.Drugtype;
import com.hospital.registration.service.DrugTypeService;
import com.hospital.registration.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 统计
 *
 * @author SONG
 */
@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('USER')")
public class StatisticsController {
  @Autowired
  private StatisticsService statisticsService;

  @Autowired
  private DrugTypeService drugService;

  /**
   * 医生门诊统计数据
   *
   * @return
   */
  @GetMapping("/statisticsdoctor/{doid}")
  public ResponseEntity<?> patients(@PathVariable("doid") Integer doid) {
    List<Integer> alist = statisticsService.findAllP(doid);
    List<Integer> dlist = statisticsService.findDruP(doid);
    List<Integer> homlist = statisticsService.findHomP(doid);
    List<Integer> hoslist = statisticsService.findHosP(doid);

    Map<String, Object> result = new HashMap<>();
    result.put("alist", alist);
    result.put("dlist", dlist);
    result.put("homlist", homlist);
    result.put("hoslist", hoslist);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  /**
   * 药品销量统计
   */
  @GetMapping("/statisticsdrugsale/{dyid}")
  public ResponseEntity<?> drugSale(@PathVariable("dyid") Integer dyid) {
    List<String> mons = this.getMonths();//半年时间
    Collections.reverse(mons);

    List<Map<String, Object>> dt = new ArrayList<>();
    if (dyid == 0) {
      // 查所有类型
      List<Drugtype> dts = drugService.findAllType();
      for (Drugtype d : dts) {
        Map<String, Object> map = new HashMap<>();
        List<Integer> sum = new ArrayList<>();
        for (String mon : mons) {
          Drugtype dy = statisticsService.statSalDt(d.getDyid(), mon);
          map.put("name", dy.getDyname());
          sum.add(dy.getBy2());//用by2装销量
        }
        map.put("data", sum);
        dt.add(map);
      }
    } else {
      // 某类药销售量统计
      List<Drug> atlist = statisticsService.statDrugs(dyid);
      for (Drug d : atlist) {
        Map<String, Object> map = new HashMap<>();
        List<Integer> sum = new ArrayList<>();
        for (String mon : mons) {
          Drug dr = statisticsService.statSalDr(d.getDrid(), mon);
          map.put("name", dr.getDrname());
          sum.add(dr.getBy2());//用by2装销量
        }
        map.put("data", sum);
        dt.add(map);
      }
    }

    Map<String, Object> result = new HashMap<>();
    result.put("month", mons);
    result.put("dt", dt);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  private List<String> getMonths() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
    Date date = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date); // 设置为当前时间
    List<String> mons = new ArrayList<>();
    calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
    for (int i = 0; i < 6; i++) {
      calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
      date = calendar.getTime();
      String mon = dateFormat.format(date);
      mons.add(mon);
    }
    return mons;
  }

  /**
   * 药品库存统计
   *
   * @param dyid
   * @return
   */
  @GetMapping("statisticsdrugstore/{dyid}")
  public ResponseEntity<?> drugStore(@PathVariable("dyid") Integer dyid) {
    String result = "";
    if (dyid == 0) {
      List<Drugtype> atlist = statisticsService.statTypes();
      Integer sum = 0;
      StringBuffer result1 = new StringBuffer("[");
      for (Drugtype dt : atlist) {
        String s = "[\"" + dt.getDyname() + "\"," + dt.getDystate() + "],";
        result1.append(s);
        sum += dt.getDystate();
      }
      if (atlist.size() > 0)
        result1.deleteCharAt(result1.length() - 1); //去掉最后一个逗号

      result1.append("]");
      result = "{\"atlist\":" + result1 + ",\"sum\":" + sum + "}";
    } else {
      List<Drug> atlist = statisticsService.statDrugs(dyid);
      Integer sum = 0;
      StringBuffer result1 = new StringBuffer("[");
      for (Drug dr : atlist) {
        String s = "[\"" + dr.getDrname() + "\"," + dr.getDrsum() + "],";
        result1.append(s);
        sum += dr.getDrsum();
      }
      if (atlist.size() > 0)
        result1.deleteCharAt(result1.length() - 1); //去掉最后一个逗号

      result1.append("]");
      result = "{\"atlist\":" + result1 + ",\"sum\":" + sum + "}";
    }
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

}


