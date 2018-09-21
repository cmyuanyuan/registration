package com.hospital.registration.controller;

import com.hospital.registration.domain.Cards;
import com.hospital.registration.domain.Info;
import com.hospital.registration.service.CardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Consumer;

/**
 * 诊疗卡业务控制器类
 *
 * @author SONG
 */
@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('USER')")
public class CardsController {
  @Autowired
  private CardsService cardsService;

  /**
   * 办卡充值
   */
  @RequestMapping(value = "/cards", method = RequestMethod.POST)
  public ResponseEntity<?> addCards(@RequestBody Cards cards) {
    int count = cardsService.add(cards);
    return new ResponseEntity<>(new Info(count), HttpStatus.OK);
  }

  /**
   * 医疗卡查询
   *
   * @return
   */
  @RequestMapping(value = "/cards", method = RequestMethod.GET)
  public ResponseEntity<List<Cards>> find() {
    List<Cards> cardsList = cardsService.find();
    if (cardsList.isEmpty()) {
      return new ResponseEntity(HttpStatus.NO_CONTENT);
      // You many decide to return HttpStatus.NOT_FOUND
    }
    //过滤用户手机号及身份证
    Consumer<Cards> privacy = c -> {
      c.setPhone(replaceAction(c.getPhone(), "(?<=\\d{3})\\d(?=\\d{4})"));
      c.setIdcard(replaceAction(c.getIdcard(), "(?<=\\d{4})\\d(?=\\w{3})"));
    };
    cardsList.forEach(privacy);

    return new ResponseEntity<>(cardsList, HttpStatus.OK);
  }

  /**
   * String的替换,以达到保密效果
   *
   * @param str
   * @param regular
   * @return
   */
  private static String replaceAction(String str, String regular) {
    return str.replaceAll(regular, "*");
  }

  /**
   * 更新医疗卡
   */
  @RequestMapping(value = "/cards/{id}/{data}/{op}", method = RequestMethod.PUT)
  public ResponseEntity<?> modifyCards(@PathVariable("id") Integer cid,
                                       @PathVariable("data") Double data,
                                       @PathVariable("op") String op) {
    int count = 0;
    if ("topup".equalsIgnoreCase(op)) {
      count = cardsService.updateRamaining(cid, data, "topup");
    } else if ("enable".equalsIgnoreCase(op)) {
      count = cardsService.enable(cid, Double.valueOf(data).intValue());
    }
    return new ResponseEntity<>(new Info(count), HttpStatus.OK);
  }

}
