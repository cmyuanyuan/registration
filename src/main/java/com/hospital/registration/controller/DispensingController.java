package com.hospital.registration.controller;

import com.hospital.registration.domain.Cards;
import com.hospital.registration.domain.Drugandprescripton;
import com.hospital.registration.domain.Info;
import com.hospital.registration.domain.Prescripton;
import com.hospital.registration.service.CardsService;
import com.hospital.registration.service.DispensingService;
import com.hospital.registration.service.DoctorvisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 划价发药员控制器类
 *
 * @author SONG
 */
@RestController
@RequestMapping("/api")
public class DispensingController {

  @Autowired
  private DispensingService dispensingService;

  @Autowired
  private DoctorvisService doctorvisService;

  @Autowired
  private CardsService cardsService;

  @GetMapping("/dispensings")
  public ResponseEntity<?> find() {
    List<Prescripton> list = dispensingService.listPrescripton();
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  /**
   * 查看药方项
   *
   * @param prid
   * @param cid
   * @return
   */
  @RequestMapping("/dispensingsdrugandprescription")
  public ResponseEntity<?> finddrandpr(@RequestParam("prid") Integer prid,
                                       @RequestParam("cid") Integer cid) {
    Cards cards = cardsService.find(cid);
    Map<Integer, Drugandprescripton> item = doctorvisService.findMap(prid);
    Double sum = 0d;
    Iterator<Drugandprescripton> it = item.values().iterator();
    // 累加购物项集合每个购物项的小计价格
    while (it.hasNext()) {
      sum += it.next().getSum();
    }
    BigDecimal bd = new BigDecimal(sum);
    sum = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

    Prescripton prescripton = new Prescripton();
    prescripton.setPrid(prid);
    prescripton.setCards(cards);
    prescripton.setDtotal(sum);
    prescripton.setItems(item);
    return new ResponseEntity<>(prescripton, HttpStatus.OK);
  }


  /**
   * 充值
   *
   * @param cid
   * @param price
   * @return
   */
  @PostMapping("/dispensingaddrecharge")
  public ResponseEntity<?> addRecharge(@RequestParam(value = "cid") Integer cid,
                                       @RequestParam(value = "price") Double price) {
    Cards cards = cardsService.find(cid);
    int result = 0;
    if (cards != null) {
      dispensingService.updateCards(price + cards.getRamaining(), cid);
      result = 1;
    } else {
      result = -1;
    }
    return new ResponseEntity<>(new Info(result), HttpStatus.OK);
  }

  /**
   * 收费
   *
   * @param prid
   * @param cid
   * @param price
   * @return
   */
  @PostMapping("/dispensingcharge")
  public ResponseEntity<?> charge(@RequestParam("prid") Integer prid,
                                  @RequestParam("cid") Integer cid,
                                  @RequestParam("price") Double price) {
    int result = 0;
    Cards cards = cardsService.find(cid);
    if (0 < dispensingService.charge(prid, cid, cards.getRamaining() - price)) {
      result = 1;
    }
    return new ResponseEntity<>(new Info(result), HttpStatus.OK);
  }
}
