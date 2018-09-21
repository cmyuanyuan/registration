package com.hospital.registration.controller;

import com.hospital.registration.domain.Doctors;
import com.hospital.registration.domain.Info;
import com.hospital.registration.service.DoctorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 医生控制器类
 *
 * @author SONG
 */
@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('USER')")
public class DoctorsController {

  @Autowired
  private DoctorsService doctorsService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @GetMapping("/doctors")
  public ResponseEntity<?> getgetDoctors() {
    List<Doctors> departsList = doctorsService.find();
    return new ResponseEntity<>(departsList, HttpStatus.OK);
  }

  @GetMapping("/doctors/{doid}")
  public ResponseEntity<?> getDoctor(@PathVariable("doid") Integer doid) {
    Doctors doctor = doctorsService.find(doid);
    return new ResponseEntity<>(doctor, HttpStatus.OK);
  }

  @RequestMapping(value = "/doctors", method = RequestMethod.POST)
  public ResponseEntity<?> addDeparts(@RequestBody Doctors doctor) {
    int result = doctorsService.add(doctor, doctor.getAname(), passwordEncoder.encode(doctor.getPwd()));
    return new ResponseEntity<>(new Info(result), HttpStatus.OK);
  }

  @RequestMapping(value = "/doctors", method = RequestMethod.PUT)
  public ResponseEntity<?> modifyDeparts(@RequestBody Doctors doctor) {
    int result = doctorsService.modify(doctor);
    return new ResponseEntity<>(new Info(result), HttpStatus.OK);
  }

  /**
   * 医生启用 | 停用
   *
   * @param doid
   * @param doexist
   * @return
   */
  @PostMapping("/doctorschangestate")
  public ResponseEntity<?> changeState(@RequestParam(value = "doid") Integer doid, @RequestParam(value = "doexist") Integer doexist) {
    int result = doctorsService.changeState(doid, doexist);
    return new ResponseEntity<>(new Info(result), HttpStatus.OK);
  }
}
