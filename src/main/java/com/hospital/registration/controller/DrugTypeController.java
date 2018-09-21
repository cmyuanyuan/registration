package com.hospital.registration.controller;

import com.hospital.registration.domain.Drugtype;
import com.hospital.registration.domain.Info;
import com.hospital.registration.service.DrugTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 药品类型业务控制器类
 *
 * @author SONG
 */
@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('USER')")
public class DrugTypeController {
  @Autowired
  private DrugTypeService drugTypeService;

  @GetMapping("/drugtypes")
  public ResponseEntity<?> getDrugs() {
    List<Drugtype> drugtypeList = drugTypeService.findAllType();
    return new ResponseEntity<>(drugtypeList, HttpStatus.OK);
  }

  @GetMapping("/drugtypesdystate")
  public ResponseEntity<?> findDrugtype() {
    List<Drugtype> drugtypeList = drugTypeService.findDrugtype();
    return new ResponseEntity<>(drugtypeList, HttpStatus.OK);
  }

  //新增类型
  @PostMapping("/drugtypesadd")
  public ResponseEntity<?> TypeAdd(@RequestParam(value = "dyname") String dyname) {
    int result = drugTypeService.add(dyname);
    return new ResponseEntity<>(new Info(result), HttpStatus.OK);
  }

  //改类别状态
  @PostMapping("/drugtypeschangestate")
  public ResponseEntity<?> changeState(@RequestParam(value = "dyid") Integer dyid, @RequestParam(value = "dystate") Integer dystate) {
    int drugnum = drugTypeService.useDrByTp(dyid);
    int result = 0;

    //类型中还有可用药品，无法禁用
    if (drugnum > 0 && dystate == 0) {
      return new ResponseEntity<>(new Info(-1), HttpStatus.OK);
    }
    result = drugTypeService.updateTypeState(dyid, dystate);
    return new ResponseEntity<>(new Info(result), HttpStatus.OK);
  }
}
