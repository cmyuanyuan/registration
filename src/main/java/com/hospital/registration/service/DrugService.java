package com.hospital.registration.service;

import com.hospital.registration.domain.Departs;
import com.hospital.registration.domain.Drug;
import com.hospital.registration.mapper.DrugMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class DrugService {

  @Autowired
  private DrugMapper drugMapper;

  //查药
  public List<Drug> find(Double p1, Double p2) {
    return drugMapper.findDrugs(p1, p2);
  }

  public Drug find(Integer drid) {
    return drugMapper.findById(drid);
  }

  public int ckDyState(Integer drid) {
    return drugMapper.ckDyState(drid);
  }

  //新增药品及与科室关系
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public int addDrug(Drug drug, int[] deid) {
    int result = 0;
    drugMapper.add(drug);
    int drid = drugMapper.findDrugId();
    for (int i = 0; i < deid.length; i++) {
      drugMapper.addDrugDe(drid, deid[i]);
      result = 1;
    }
    return result;
  }

  //修改药品及科室关系
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public int modifyDrug(Drug drug, int[] deid) {
    int result = 0;
    drugMapper.modifyDrug(drug);
    Integer drid = drug.getDrid();
    drugMapper.removeDrDe(drid);
    for (int i = 0; i < deid.length; i++) {
      drugMapper.addDrugDe(drid, deid[i]);
      result = 1;
    }
    return result;
  }

  //查药和科室关系
  public List<Departs> findDrDe(Integer drid){
    return drugMapper.findDrDe(drid);
  }

  //改药状态
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public int changeState(Integer drid, Integer drstate) {
    return drugMapper.changeState(drid, drstate);
  }
}
