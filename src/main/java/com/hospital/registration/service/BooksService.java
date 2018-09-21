package com.hospital.registration.service;

import com.hospital.registration.domain.Bookable;
import com.hospital.registration.domain.Books;
import com.hospital.registration.domain.Departs;
import com.hospital.registration.domain.Doctors;
import com.hospital.registration.mapper.BooksMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 预约排班业务逻辑类
 *
 * @author SONG
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class BooksService {
  @Autowired
  private BooksMapper booksMapper;

  @Autowired
  private DepartsService departsService;

  @Autowired
  private DoctorsService doctorsService;

  public int getToday(Integer deid) {
    return booksMapper.getToday(deid);
  }

  public int getYesterday(Integer deid) {
    return booksMapper.getYesterday(deid);
  }

  public int getWeek(Integer deid) {
    return booksMapper.getWeek(deid);
  }

  public int getMonth(Integer deid) {
    return booksMapper.getMonth(deid);
  }

  public int getQuarter(Integer deid) {
    return booksMapper.getQuarter(deid);
  }

  /**
   * 取预约号
   *
   * @param starttime
   * @param idcard
   * @return
   */
  public List<Books> findAll(Integer starttime, String idcard) {
    //已挂号状态为 1
    List<Books> list = booksMapper.findAllBook(starttime, 1, idcard);
    return list;
  }

  /**
   * 是否绑卡
   *
   * @param red
   * @param card
   * @return
   */
  public Integer bdCard(Integer red, Integer card) {
    return booksMapper.bdCard(red, card);
  }

  public Books findById(Integer red) {
    return booksMapper.findBooksById(red);
  }

  /**
   * 排班表
   *
   * @param red
   * @return
   */
  public Integer findbid(Integer red) {
    return booksMapper.findbid(red);
  }

  /**
   * 获得票号
   *
   * @param starttime
   * @param bid
   * @return
   */
  public int getSnum(Integer starttime, Integer bid) {
    return booksMapper.getSnum(starttime, bid);
  }

  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public Integer addticket(Integer medcard, Integer bid, Integer snum, Integer red) {
    int count = booksMapper.addticket(medcard, bid, snum);
    count = booksMapper.updatebookable(bid, booksMapper.findByBid(bid) + 1);

    if (red != -1) {
      count = booksMapper.updateRestate(red);
    }
    return count;
  }

  /**
   * 现场挂号
   *
   * @return
   */
  public List<Departs> findBuyTicket(Integer starttime) {
    List<Departs> departsList = new ArrayList<>();
    List<Departs> deidlist = departsService.find();
    for (Departs depart : deidlist) {
      List<Doctors> doctorsList = doctorsService.findByDeId(depart.getDeid(), starttime);
      if (!doctorsList.isEmpty()) {
        depart.setDoctorsList(doctorsList);
        departsList.add(depart);
      }
    }
    return departsList;
  }

  /**
   * 某医生挂号具体信息
   *
   * @param doid
   * @param starttime
   * @return
   */
  public Bookable findBookable(Integer doid, Integer starttime) {
    Bookable bookable = booksMapper.findByDoid(doid, starttime);
    Doctors doctor = doctorsService.findDoctor(doid);
    bookable.setDename(doctor.getBy1()); // 科室
    bookable.setBcost(doctor.getBcost());
    bookable.setDoname(doctor.getDoname());
    bookable.setDoid(doctor.getDoid());

    return bookable;
  }
}
