package com.hospital.registration.service;


import com.hospital.registration.domain.Bookable;
import com.hospital.registration.domain.Doctors;
import com.hospital.registration.domain.WeekBean;
import com.hospital.registration.mapper.BookableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class BookableService {
  @Autowired
  private BookableMapper bookableMapper;

  public List<WeekBean> findBookable(List<String> list, Integer deid) {
    return bookableMapper.findBK(list, deid);
  }

  public List<Doctors> findDoctors(Integer deid) {
    return bookableMapper.findAllDoc(deid);
  }

  //新增排班
  @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED,
          rollbackFor = Exception.class)
  public Integer addBookable(Bookable bk) {
    return bookableMapper.addBK(bk);
  }

  //删除排班
  @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED,
          rollbackFor = Exception.class)
  public Integer deleteBookable(Integer doid, Date date1) {
    return bookableMapper.delBK(doid, date1);
  }
}
