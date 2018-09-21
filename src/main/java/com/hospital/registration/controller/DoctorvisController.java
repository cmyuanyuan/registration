package com.hospital.registration.controller;

import com.hospital.registration.domain.*;
import com.hospital.registration.service.DoctorvisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 医生就诊
 *
 * @author SONG
 */
@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('USER')")
public class DoctorvisController {
  //医生开具处方的药品库存阈值
  private final static int threshold = 20;

  @Autowired
  private DoctorvisService doctorvisService;

  /**
   * 查询某医生的挂号单
   *
   * @param doid
   * @return
   */
  @RequestMapping(value = "/doctorvisindex/{doid}", method = RequestMethod.GET)
  public ResponseEntity<?> index(@PathVariable("doid") Integer doid) {
    List<Registration> list = doctorvisService.find(doid);
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  /**
   * 放弃治疗: 未取号,病人未来,修改订单状态
   *
   * @param rid
   * @return
   */
  @RequestMapping(value = "/doctorvisstateprg/{rid}", method = RequestMethod.GET)
  public ResponseEntity<?> stateprg(@PathVariable("rid") Integer rid) {
    int count = doctorvisService.stateprg(rid, 0);
    return new ResponseEntity<>(new Info(count), HttpStatus.OK);
  }

  /**
   * 查看病人病例
   *
   * @param cid
   * @return
   */
  @RequestMapping(value = "/doctorvishistory/{cid}", method = RequestMethod.GET)
  public ResponseEntity<?> findHistory(@PathVariable("cid") Integer cid,
                                       @RequestParam(value = "beginDate", required = false)
                                       @DateTimeFormat(pattern = "yyyy-MM-dd") Date beginDate,
                                       @RequestParam(value = "endDate", required = false)
                                       @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
    List<History> list = doctorvisService.findHistory(cid, beginDate, endDate);
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  /**
   * 查看某张药方项
   *
   * @param prid
   * @return
   */
  @RequestMapping(value = "/doctorvisdrug/{prid}", method = RequestMethod.GET)
  public ResponseEntity<?> findDrug(@PathVariable("prid") Integer prid) {
    List<Drugandprescripton> list = doctorvisService.findDrug(prid);
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  /**
   * 医疗方案
   *
   * @param cid
   * @param rid
   * @return
   */
  @RequestMapping(value = "/doctorvisgetmedical/{cid}/{rid}", method = RequestMethod.GET)
  public ResponseEntity<?> getMedical(@PathVariable("cid") Integer cid,
                                      @PathVariable("rid") Integer rid) {
    MedicalState medicalState = doctorvisService.getMedical(cid, rid);
    return new ResponseEntity<>(medicalState, HttpStatus.OK);
  }

  /**
   * 药品
   *
   * @param doid
   * @param price1
   * @param price2
   * @return
   */
  @RequestMapping(value = "/doctorvisgetdrug/{doid}", method = RequestMethod.GET)
  public ResponseEntity<?> getDrug(@PathVariable("doid") Integer doid,
                                   @RequestParam(value = "price1", required = false) Double price1,
                                   @RequestParam(value = "price2", required = false) Double price2) {
    List<Druganddeparts> drlist = doctorvisService.getDrug(doid, price1, price2);
    //阈值过滤: 库存少于阈值的不予显示
    drlist = drlist.stream().filter(dr -> dr.getDrug().getDrsum() > threshold).collect(Collectors.toList());
    drlist.forEach(dr -> System.out.println(dr.getDrug().getDrname() + "    " + dr.getDrug().getDrsum()));

    return new ResponseEntity<>(drlist, HttpStatus.OK);
  }

  @RequestMapping(value = "/doctorvisdrandpr", method = RequestMethod.POST)
  public ResponseEntity<?> drandpr(
          @RequestParam(value = "drid") Integer drid,
          @RequestParam(value = "rid") Integer rid,
          @RequestParam(value = "cid") Integer cid,
          @RequestParam(value = "doid") Integer doid,
          @RequestParam(value = "by2") Integer by2) {
    /**
     * 诊疗卡id
     * 页面拿到订单rid
     * 是否有没有处理完的药方单
     * 药品ID
     */
    // 拿到诊疗by2     如果有值就是 今天的 药方ID
    Integer by2state = by2;
    int count = 0;
    // 如果没有 就新增  药方
    if (by2state == null || by2state < 100) {
      // 创建药方添加 药方项
      by2state = doctorvisService.allPrescripton(cid, doid, drid);
      // 修改状态
      count = doctorvisService.updaby2(rid, by2state);
      MedicalState medicalState = doctorvisService.getMedical(cid, rid);
      return new ResponseEntity<>(medicalState, HttpStatus.OK);
    } else {
      Map<Integer, Drugandprescripton> map = doctorvisService.findMap(by2state);
      Drugandprescripton temp = map.get(drid);
      // 没有就创建 新的订单项
      if (temp == null) {
        count = doctorvisService.alldrugpres(drid, by2state, 1);
      } else {
        // 有就把数量+1
        int sum = temp.getDrnum() + 1;
        count = doctorvisService.updatedrug(sum, drid, by2state);
      }
      return new ResponseEntity<>(new Info(count), HttpStatus.OK);
    }
  }

  /**
   * 查看药方项
   */
  @GetMapping("/doctorvisfindprescription/{by2}")
  public ResponseEntity<?> findPrescription(@PathVariable("by2") Integer by2) {
    Map<Integer, Drugandprescripton> map = doctorvisService.findMap(by2);

    /*
    double sum = 0;
    Iterator<Drugandprescripton> it = map.values().iterator();
    // 累加购物项集合每个购物项的小计价格
    while (it.hasNext()) {
      sum += it.next().getSum();
    }
    */
    return new ResponseEntity<>(map, HttpStatus.OK);
  }

  /**
   * 修改药方项
   *
   * @param drid
   * @param prid
   * @param nun
   * @return
   */
  @PostMapping("/doctorvisupdateprescripton")
  public ResponseEntity<?> updatePrescripton(
          @RequestParam("drid") Integer drid,
          @RequestParam("prid") Integer prid,
          @RequestParam("nun") Integer nun) {
    int count = doctorvisService.updatedrug(nun, drid, prid);
    return new ResponseEntity<>(new Info(count), HttpStatus.OK);
  }

  /**
   * 删除药方项
   *
   * @param drid
   * @param prid
   * @return
   */
  @PostMapping("/doctorvisremoveprescripton")
  public ResponseEntity<?> removePrescripton(
          @RequestParam("drid") Integer drid,
          @RequestParam("prid") Integer prid) {
    int count = doctorvisService.deletedrug(drid, prid);
    return new ResponseEntity<>(new Info(count), HttpStatus.OK);
  }

  /**
   * 添加病历
   *
   * @param deal
   * @param brief
   * @param cid
   * @param rid
   * @param doid
   * @param prid  药方id
   * @return
   */
  @PostMapping("/doctorvisaddmedical")
  public ResponseEntity<?> addMedical(
          @RequestParam("deal") Integer deal,
          @RequestParam("brief") String brief,
          @RequestParam("cid") Integer cid,
          @RequestParam("rid") Integer rid,
          @RequestParam("doid") Integer doid,
          @RequestParam("by2") Integer prid) {

    //住院的时候可以不开药
    if (deal == 2) {
      if (prid <= 0) {
        return new ResponseEntity<>(new Info(-1), HttpStatus.OK);
      }
    }

    int result = -1;
    if (deal == 1) {
      //添加病历
      if (0 < doctorvisService.allHistory(cid, doid, 0, brief, deal, rid)) {
        result = 1;
      }
    } else if (deal == 3) {
      if (0 < doctorvisService.allHistory(cid, doid, 0, brief, deal, rid)) {
        result = 2; // 住院
      }
    } else {
      if (0 < doctorvisService.allHistory(cid, doid, prid, brief, deal, rid)) {
        result = 1;
      }
    }
    return new ResponseEntity<>(new Info(result), HttpStatus.OK);
  }

  @PostMapping("/doctorvissavemedical")
  public ResponseEntity<?> baochun(
          @RequestParam(value = "brief", required = false) String brief,
          @RequestParam("rid") Integer rid) {
    if (brief == null) {
      brief = " ";
    }
    int result = doctorvisService.saveMedical(rid, brief);
    return new ResponseEntity<>(new Info(result), HttpStatus.OK);
  }
}
