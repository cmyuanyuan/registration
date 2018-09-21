package com.hospital.registration.service;

import com.hospital.registration.domain.Drugtype;
import com.hospital.registration.mapper.DrugTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class DrugTypeService {

  @Autowired
  private DrugTypeMapper drugTypeMapper;

  //查所有类型
  public List<Drugtype> findAllType() {
    return drugTypeMapper.findAllType();
  }

  /**
   * 可用的药品类型
   *
   * @return
   */
  public List<Drugtype> findDrugtype() {
    return drugTypeMapper.findDrugtype();
  }

  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public int add(String dyname) {
    return drugTypeMapper.add(dyname);
  }

  //类型中的可用药
  public int useDrByTp(Integer dyid) {
    return drugTypeMapper.useDrByTp(dyid);
  }

  //改类型状态
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public int updateTypeState(Integer dyid, Integer dystate) {
    return drugTypeMapper.updateTypeState(dyid, dystate);
  }
}
