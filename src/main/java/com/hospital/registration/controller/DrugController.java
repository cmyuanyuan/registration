package com.hospital.registration.controller;

import com.hospital.registration.domain.Departs;
import com.hospital.registration.domain.Drug;
import com.hospital.registration.domain.Info;
import com.hospital.registration.service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 药品业务控制器类
 *
 * @author SONG
 */
@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('USER')")
public class DrugController {
  @Autowired
  private DrugService drugService;

  /**
   * 查药
   *
   * @param price1
   * @param price2
   * @return
   */
  @RequestMapping("/drugs")
  public ResponseEntity<?> findDrugs(
          @RequestParam(value = "price1", required = false) Double price1,
          @RequestParam(value = "price2", required = false) Double price2) {
    List<Drug> list = drugService.find(price1, price2);
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @RequestMapping("/drugs/{drid}")
  public ResponseEntity<?> findDrug(@PathVariable("drid") Integer drid) {
    Drug drug = drugService.find(drid);
    return new ResponseEntity<>(drug, HttpStatus.OK);
  }

  @PostMapping("/drugsave")
  public ResponseEntity<?> editOver(@RequestParam(value = "drid") Integer drid,
                                    @RequestParam(value = "drstate") Integer drstate,
                                    @RequestParam(value = "drname") String drname,
                                    @RequestParam(value = "drsum") Integer drsum,
                                    @RequestParam(value = "drprice") Double drprice,
                                    @RequestParam(value = "dyid") Integer dyid,
                                    @RequestParam(value = "deid") int[] deid) {
    Drug drug = new Drug(drid, drname, drsum, drprice, drstate, dyid);

    int result = 0;
    if (drug.getDrid() == 0) {
      result = drugService.addDrug(drug, deid);
    } else {
      //如果是修改，则要先删掉药品科室关系再添加
      result = drugService.modifyDrug(drug, deid);
    }
    return new ResponseEntity<>(new Info(result), HttpStatus.OK);
  }

  @RequestMapping("/drugdepart/{drid}")
  public ResponseEntity<?> findDrDe(@PathVariable("drid") Integer drid) {
    List<Departs> departsList = drugService.findDrDe(drid);
    return new ResponseEntity<>(departsList, HttpStatus.OK);
  }

  /**
   * 改药品状态
   *
   * @param drid
   * @param drstate
   * @return
   */
  @PostMapping("/drugschangestate")
  public ResponseEntity<?> changeState(@RequestParam(value = "drid") Integer drid, @RequestParam(value = "drstate") Integer drstate) {
    int dystate = drugService.ckDyState(drid);
    if (dystate == 0 && drstate == 1) {
      return new ResponseEntity<>(new Info(-1), HttpStatus.OK);
    }
    int result = drugService.changeState(drid, drstate);
    return new ResponseEntity<>(new Info(result), HttpStatus.OK);
  }
}
