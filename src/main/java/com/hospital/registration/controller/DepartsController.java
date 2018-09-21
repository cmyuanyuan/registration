package com.hospital.registration.controller;

import com.hospital.registration.domain.Departs;
import com.hospital.registration.domain.Info;
import com.hospital.registration.service.DepartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('USER')")
public class DepartsController {
  @Autowired
  private DepartsService departsService;

  @GetMapping("/departs")
  public ResponseEntity<?> getDeparts() {
    List<Departs> departsList = departsService.find();
    return new ResponseEntity<>(departsList, HttpStatus.OK);
  }

  @GetMapping("/departsdeexist")
  public ResponseEntity<?> findDeparts() {
    List<Departs> departsList = departsService.findDeparts();
    return new ResponseEntity<>(departsList, HttpStatus.OK);
  }

  @GetMapping("/departs/{deid}")
  public ResponseEntity<?> getDepart(@PathVariable("deid") Integer deid) {
    Departs departs = departsService.find(deid);
    return new ResponseEntity<>(departs, HttpStatus.OK);
  }

  @RequestMapping(value = "/departs", method = RequestMethod.POST)
  public ResponseEntity<?> addDeparts(@RequestBody Departs departs) {
    int result = departsService.add(departs);
    return new ResponseEntity<>(new Info(result), HttpStatus.OK);
  }

  @RequestMapping(value = "/departs", method = RequestMethod.PUT)
  public ResponseEntity<?> modifyDeparts(@RequestBody Departs departs) {
    int result = departsService.modify(departs);
    return new ResponseEntity<>(new Info(result), HttpStatus.OK);
  }

  @PostMapping("/departschangestate")
  public ResponseEntity<?> changeState(@RequestParam(value = "deid") Integer deid, @RequestParam(value = "deexist") Integer deexist) {
    int result = 0;
    if (deexist == 0) {
      // 是否可以停用该科室(判断该科室下如若有医生则不能停用,没则可以停用)
      if (departsService.findDoctors(deid) > 0)
        return new ResponseEntity<>(new Info(-1), HttpStatus.OK);
    }

    result = departsService.changeState(deid, deexist);
    return new ResponseEntity<>(new Info(result), HttpStatus.OK);
  }
}
