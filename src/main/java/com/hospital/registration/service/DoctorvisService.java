package com.hospital.registration.service;

import com.hospital.registration.domain.*;
import com.hospital.registration.mapper.DoctorvisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 医生就诊业务逻辑类
 *
 * @author SONG
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class DoctorvisService {
  @Autowired
  private DoctorvisMapper doctorvisMapper;

  @Autowired
  private CardsService cardsService;

  @Autowired
  private DoctorsService doctorsService;

  /**
   * 查询某医生的挂号单
   *
   * @param doid
   * @return
   */
  public List<Registration> find(Integer doid) {
    return doctorvisMapper.findRegistrationByDoic(doid);
  }

  /**
   * 修改订单状态
   */
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public int stateprg(Integer rid, Integer state) {
    return doctorvisMapper.stateprg(rid, state);
  }

  /**
   * 查看病人病例
   */
  public List<History> findHistory(Integer cid, Date beginDate, Date endDate) {
    List<History> list = doctorvisMapper.findHistory(cid, beginDate, endDate);
    return list;
  }

  /**
   * 查看药方项
   */
  public List<Drugandprescripton> findDrug(Integer prid) {
    List<Drugandprescripton> list = doctorvisMapper.findDrug(prid);
    return list;
  }

  /**
   * 查询订单状态
   */
  public MedicalState getMedical(Integer cid, Integer rid) {
    MedicalState medicalState = doctorvisMapper.getMedical(rid);
    Cards cards = cardsService.find(cid);
    medicalState.setCards(cards);
    medicalState.setRid(rid);
    return medicalState;
  }

  /**
   * 查询药品 部门和数量
   *
   * @return
   */
  public List<Druganddeparts> getDrug(Integer doid, Double price1, Double price2) {
    Doctors doctors = doctorsService.find(doid);
    return doctorvisMapper.getDrug(doctors.getDeid(), price1, price2);
  }

  /**
   * 点击药方 创建 药方单 并拿到 药方id ,初始化未结账 1
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
  public int allPrescripton(Integer cid, Integer doid, Integer drid) {
    int count = doctorvisMapper.allPrescripton(cid, doid);

    if (count > 0) {
      //拿到药方ID
      int prid = doctorvisMapper.findprid();
      count = prid;
      //添加一个药方项
      doctorvisMapper.alldrugpres(drid, prid, 1);
    }
    return count;
  }

  /**
   * 修改备用状态2
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
  public int updaby2(Integer rid, Integer by2) {
    return doctorvisMapper.updaby2(rid, by2);
  }

  /**
   * 点击 查看药方项
   */
  public Map<Integer, Drugandprescripton> findMap(Integer prid) {
    Map<Integer, Drugandprescripton> map = new HashMap<>();
    List<Drugandprescripton> list = doctorvisMapper.findDrug(prid);
    // 拿出每个 订单项
    for (Drugandprescripton d : list) {
      Drug drug = new Drug();
      drug.setDrid(d.getDrid());
      drug.setDrname(d.getDrug().getDrname());
      drug.setDrprice(d.getDrug().getDrprice());
      d.setSum(d.getDrug().getDrprice() * d.getDrnum());//小计价格
      d.setDrug(drug);//一个药品信息
      map.put(d.getDrid(), d);
    }
    return map;
  }

  /**
   * 添加药方项 药方ID 药品ID 数量
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
  public int alldrugpres(Integer drid, Integer prid, Integer drnum) {
    int count = doctorvisMapper.alldrugpres(drid, prid, drnum);
    return count;
  }

  /**
   * 修改药方项 药方ID 药品ID 数量
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
  public int updatedrug(Integer drnum, Integer drid, Integer prid) {
    int count = doctorvisMapper.updatedrug(drnum, drid, prid);
    return count;
  }

  /**
   * 删除药方项 药方ID 药品ID
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
  public int deletedrug(Integer drid, Integer prid) {
    int count = doctorvisMapper.deletedrug(drid, prid);
    return count;
  }

  /**
   * 给病人添加病例  诊疗卡ID 医生ID 药方 信息  -诊断结果  方案
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
  public int allHistory(Integer cid, Integer doid, Integer prid, String brief,
                        Integer deal, Integer rid) {
    int count = 0;
    if (brief == null || brief.length() < 1) {
      brief = "这个医生很懒,什么都没留下1";
    }
    if (prid > 1) {
      doctorvisMapper.allHistory(cid, doid, prid, brief, deal);
    } else {
      doctorvisMapper.allHistory(cid, doid, null, brief, deal);
    }
    doctorvisMapper.updaby2(rid, 0);
    count = doctorvisMapper.updarig(rid, 0);
    return count;
  }

  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
  public int saveMedical(Integer rid, String brief) {
    return doctorvisMapper.saveMedical(rid, brief);
  }
}
