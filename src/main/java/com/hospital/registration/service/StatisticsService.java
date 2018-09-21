package com.hospital.registration.service;

import com.hospital.registration.domain.Drug;
import com.hospital.registration.domain.Drugtype;
import com.hospital.registration.mapper.StatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class StatisticsService {

  @Autowired
  private StatisticsMapper statisticsMapper;

  //总病人列表
  public List<Integer> findAllP(Integer doid) {
    List<Integer> list = new ArrayList<>();
    for (int i = 1; i <= 12; i++) {
      Integer num = statisticsMapper.findAllP(doid, i);
      list.add(num);
    }
    return list;
  }

  //开药病人列表
  public List<Integer> findDruP(Integer doid) {
    List<Integer> list = new ArrayList<>();
    for (int i = 1; i <= 12; i++) {
      Integer num = statisticsMapper.findDrugP(doid, i);
      list.add(num);
    }
    return list;
  }

  //回家病人列表
  public List<Integer> findHomP(Integer doid) {
    List<Integer> list = new ArrayList<>();
    for (int i = 1; i <= 12; i++) {
      Integer num = statisticsMapper.findHomeP(doid, i);
      list.add(num);
    }
    return list;
  }

  //住院病人列表
  public List<Integer> findHosP(Integer doid) {
    List<Integer> list = new ArrayList<>();
    for (int i = 1; i <= 12; i++) {
      Integer num = statisticsMapper.findHosP(doid, i);
      list.add(num);
    }
    return list;
  }

  public List<Drug> statDrugs(Integer dyid){
    return statisticsMapper.statDrugs(dyid);
  }

  public Drugtype statSalDt(Integer dyid, String mon) {
    return statisticsMapper.statSalDt(dyid,mon);
  }

  public Drug statSalDr(Integer drid, String mon) {
    return statisticsMapper.statSalDr(drid,mon);
  }

  public List<Drugtype> statTypes() {
    return statisticsMapper.statTypes();
  }
}
