package com.hospital.registration.service;

import com.hospital.registration.domain.Cards;
import com.hospital.registration.mapper.CardsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 医疗卡业务逻辑类
 *
 * @author SONG
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class CardsService {

  @Autowired
  private CardsMapper cardsMapper;

  /**
   * 注册医疗卡
   *
   * @param cards
   * @return
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
  public int add(Cards cards) {
    return cardsMapper.add(cards);
  }

  /**
   * 充值 topup | 收费 bcost
   *
   * @param cid
   * @param money
   * @param type
   * @return
   */
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public int updateRamaining(Integer cid, Double money, String type) {
    Cards cards = cardsMapper.findByIdcard(cid);
    if ("topup".equalsIgnoreCase(type)) {
      cards.setRamaining(cards.getRamaining() + money);
    } else {
      cards.setRamaining(cards.getRamaining() - money);
    }

    return cardsMapper.updateRamaining(cid, cards.getRamaining());
  }

  /**
   * 医疗卡启用 1 | 停用 0
   *
   * @param cid
   * @param doexist
   * @return
   */
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public int enable(Integer cid, Integer doexist) {
    return cardsMapper.enable(cid, doexist);
  }

  /**
   * 余额查询
   *
   * @param cid
   * @return
   */
  public Cards find(Integer cid) {
    return cardsMapper.findByIdcard(cid);
  }

  /**
   * 医疗卡查询
   */
  public List<Cards> find() {
    return cardsMapper.find();
  }
}
