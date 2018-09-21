package com.hospital.registration.service;

import com.hospital.registration.domain.Departs;
import com.hospital.registration.mapper.DepartsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 部门业务逻辑类
 *
 * @author SONG
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class DepartsService {
  @Autowired
  private DepartsMapper departsMapper;

  @Autowired
  private DoctorsService doctorsService;

  public List<Departs> find() {
    return departsMapper.find();
  }

  /**
   * 查询所有可用科室
   *
   * @return
   */
  public List<Departs> findDeparts() {
    return departsMapper.findDeparts();
  }

  public Departs find(Integer deid) {
    return departsMapper.findById(deid);
  }

  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
  public int add(Departs departs) {
    return departsMapper.add(departs);
  }

  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
  public int modify(Departs departs) {
    return departsMapper.modify(departs);
  }

  public int findDoctors(Integer deid) {
    return doctorsService.findDoctorsByDeid(deid);
  }

  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
  public int changeState(Integer deid, Integer deexist) {
    return departsMapper.changeState(deid, deexist);
  }
}

