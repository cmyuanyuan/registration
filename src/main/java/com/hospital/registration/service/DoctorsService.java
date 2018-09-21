package com.hospital.registration.service;

import com.hospital.registration.domain.Admins;
import com.hospital.registration.domain.Doctors;
import com.hospital.registration.mapper.DoctorsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 医生业务逻辑类
 *
 * @author SONG
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class DoctorsService {
  @Autowired
  private DoctorsMapper doctorsMapper;

  @Autowired
  private AdminsService adminsService;


  public Doctors find(Integer doid) {
    return doctorsMapper.findById(doid);
  }

  public List<Doctors> find() {
    return doctorsMapper.find();
  }

  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
  public int updateState(Integer doid, Integer doexist) {
    Integer aid = doctorsMapper.findDocAid(doid);
    if (aid != null)
      doctorsMapper.updateAdState(aid, doexist);
    return doctorsMapper.updateState(doid, doexist);
  }

  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
  public int modify(Doctors doctors) {
    return doctorsMapper.modify(doctors);
  }

  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
  public int add(Doctors doctors, String aname, String pwd) {
    doctorsMapper.add(doctors);
    int doid = doctorsMapper.getDoctorId();
    Admins admins = new Admins();
    admins.setAname(aname);
    admins.setPwd(pwd);
    admins.setDoid(doid);
    admins.setState(2); // 医生
    admins.setBy1(doctors.getDoname());

    return adminsService.add(admins);
  }

  /**
   * 医生启用 | 停用
   *
   * @param doid
   * @param doexist
   * @return
   */
  public int changeState(Integer doid, Integer doexist) {
    int result = 0;
    int countRed = doctorsMapper.selectRed(doid);
    int countRid = doctorsMapper.selectRid(doid);

    if (doexist != 1) {
      Integer state = doctorsMapper.ckState(doid);
      if (state == 0) {
        result = 2; // 存在管理员,非医生 2
        return result;
      }
      if (countRed == 0 && countRid == 0) {
        result = doctorsMapper.updateState(doid, doexist);
      } else {
        result = -1; // 存在预约或挂号单的医生暂未停用
      }
    } else {
      int destate = doctorsMapper.ckDeState(doid);
      if (destate == 0) {
        result = -1; // 科室被停用的医生暂未启用!
        return result;
      }
      result = doctorsMapper.updateState(doid, doexist);
    }
    return result;
  }

  /**
   * 查值班医生
   *
   * @param deid      科室 id
   * @param starttime 上午 0 | 下午 1
   * @return
   */
  public List<Doctors> findByDeId(Integer deid, Integer starttime) {
    return doctorsMapper.findByDeId(deid, starttime);
  }

  public int findDoctorsByDeid(Integer deid) {
    return doctorsMapper.findDoctorsByDeid(deid);
  }

  public Doctors findDoctor(Integer doid) {
    return doctorsMapper.findDoctor(doid);
  }
}
