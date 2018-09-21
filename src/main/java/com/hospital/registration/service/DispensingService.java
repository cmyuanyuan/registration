package com.hospital.registration.service;

import com.hospital.registration.domain.Drug;
import com.hospital.registration.domain.Drugandprescripton;
import com.hospital.registration.domain.Prescripton;
import com.hospital.registration.mapper.DispensingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
public class DispensingService {
  @Autowired
  private DispensingMapper dispensingMapper;

  @Autowired
  private DoctorvisService doctorvisService;

  @Autowired
  private DrugService drugService;

  public List<Prescripton> listPrescripton() {
    List<Prescripton> list = dispensingMapper.listPrescripton();
    return list;
  }

  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
  public int updateCards(Double price, Integer cid) {
    return dispensingMapper.updateCards(price, cid);
  }

  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
  public int updateCards2(Double price, Integer cid) {
    return dispensingMapper.updateCards2(price, cid);
  }

  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
  public int charge(Integer prid, Integer cid, Double price) {
    List<Drugandprescripton> list = doctorvisService.findDrug(prid);// 拿出订单项
    for (Drugandprescripton d : list) {
      Drug drug = drugService.find(d.getDrid());
      //循环减去药品库存数量
      dispensingMapper.updateDrug(drug.getDrsum() - d.getDrnum(), d.getDrid());
    }
    dispensingMapper.updateCards2(price, cid);// 诊疗卡扣费
    //修改药方
    return dispensingMapper.updateprescripton(prid);
  }
}
