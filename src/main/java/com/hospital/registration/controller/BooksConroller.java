package com.hospital.registration.controller;

import com.hospital.registration.domain.*;
import com.hospital.registration.service.BooksService;
import com.hospital.registration.service.CardsService;
import com.hospital.registration.service.DepartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 挂号业务控制器类
 *
 * @author SONG
 */
@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('USER')")
public class BooksConroller {
  @Autowired
  private BooksService booksService;

  @Autowired
  private DepartsService departsService;

  @Autowired
  private CardsService cardsService;

  /**
   * 获得所有部门及部门所有挂号人数信息
   *
   * @return
   */
  @RequestMapping(value = "/booksdepart", method = RequestMethod.GET)
  public ResponseEntity<?> getDeparts() {
    List<String> times = new ArrayList<>();
    times.add("今天");
    times.add("昨天");
    times.add("本周");
    times.add("本月");
    times.add("本季度");

    List<BooksDepartCount> booksDepartCounts = new ArrayList<>();

    List<Departs> departsList = departsService.find();
    for (Departs depart : departsList) {
      int today = booksService.getToday(depart.getDeid());
      int yesterday = booksService.getYesterday(depart.getDeid());
      int week = booksService.getWeek(depart.getDeid());
      int month = booksService.getMonth(depart.getDeid());
      int quarter = booksService.getQuarter(depart.getDeid());

      List<Integer> countList = new ArrayList<>();
      countList.add(today);
      countList.add(yesterday);
      countList.add(week);
      countList.add(month);
      countList.add(quarter);

      BooksDepartCount booksDepartCount = new BooksDepartCount(depart.getDename(), countList);
      booksDepartCounts.add(booksDepartCount);
    }

    BooksDepart booksDepart = new BooksDepart(times, booksDepartCounts);

    return new ResponseEntity<>(booksDepart, HttpStatus.OK);
  }

  /**
   * 统计科室挂号
   *
   * @return
   */
  @RequestMapping(value = "/drawDept", method = RequestMethod.GET)
  // @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> drawDept() {
    List<Map<String, Object>> sdatalist = new ArrayList<>();//存放sdata
    List<Map<String, Object>> serList = new ArrayList<>();//存放series

    List<Departs> departsList = departsService.find();
    for (Departs depart : departsList) {

      Map<String, Object> sdata = new HashMap<>();//name: '外科',y: 56,drilldown: '外科'

      List<List<Object>> ddatalist = new ArrayList<>();//存放ddata
      Map<String, Object> series = new HashMap<>();//name: '外科',  id: '外科', data: ddatalist

      int[] counts = new int[5];
      counts[0] = booksService.getQuarter(depart.getDeid());//季
      counts[1] = booksService.getMonth(depart.getDeid());//月
      counts[2] = booksService.getWeek(depart.getDeid());//周
      counts[3] = booksService.getYesterday(depart.getDeid());//昨
      counts[4] = booksService.getToday(depart.getDeid());//今

      sdata.put("name", depart.getDename());
      sdata.put("y", counts[0]);
      sdata.put("drilldown", depart.getDename());
      sdatalist.add(sdata);//总柱图

      String[] date = {"本季度", "本月", "本周", "昨天", "今天"};
      for (int i = 0; i < date.length; i++) {
        List<Object> ddata = new ArrayList<>();//'本季度',56 or '今天',1
        ddata.add(date[i]);
        ddata.add(counts[i]);
        ddatalist.add(ddata);
      }

      series.put("name", depart.getDename());
      series.put("id", depart.getDeid());
      series.put("data", ddatalist);
      serList.add(series);//子柱图
    }

    Map<String, Object> result = new HashMap<>();
    result.put("seriesList", serList);
    result.put("dataList", sdatalist);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  /**
   * 取预约号
   *
   * @param idcard
   * @return
   */
  @RequestMapping(value = "/books", method = RequestMethod.GET)
  public ResponseEntity<List<Books>> find(@RequestParam(value = "idcard", required = false) String idcard) {
    // 0 是上午;1 是下午
    GregorianCalendar ca = new GregorianCalendar();
    int ampm = ca.get(GregorianCalendar.AM_PM);

    List<Books> booksList = booksService.findAll(ampm, idcard);

    if (booksList.isEmpty()) {
      return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(booksList, HttpStatus.OK);
  }

  /**
   * 是否绑卡
   *
   * @param red
   * @param card
   * @return
   */
  @RequestMapping(value = "/booksviewticket", method = RequestMethod.POST)
  public ResponseEntity<?> viewticket(@RequestParam(value = "red") Integer red,
                                      @RequestParam(value = "card") Integer card) {
    int count = booksService.bdCard(red, card);
    return new ResponseEntity<>(new Info(count), HttpStatus.OK);
  }

  @RequestMapping(value = "/booksviewticket2", method = RequestMethod.POST)
  public ResponseEntity<?> viewticket2(@RequestParam(value = "red") Integer red,
                                       @RequestParam(value = "card") Integer card) {
    Books books = booksService.findById(red);
    Integer bid = booksService.findbid(red);//找排班表

    // 0 是上午;1 是下午
    GregorianCalendar ca = new GregorianCalendar();
    int ampm = ca.get(GregorianCalendar.AM_PM);

    //获得票号
    int snum = booksService.getSnum(ampm, bid);
    books.setSnum(snum + 1);
    //绑定诊疗卡号
    books.setMedcard(card);
    return new ResponseEntity<>(books, HttpStatus.OK);
  }

  /**
   * 网上预约取号
   *
   * @param books
   * @return
   */
  @RequestMapping(value = "/booksgetticket", method = RequestMethod.POST)
  public ResponseEntity<?> getTicket(@RequestBody Books books) {
    //是否诊疗卡付款 用starttime装付款方式
    if (books.getStarttime() != 0) {
      //诊疗卡扣费
      Cards card = cardsService.find(books.getMedcard());
      if (card.getRamaining() < books.getBcost()) {
        // 余额不足请提醒充值！
        return new ResponseEntity<>(new Info(-1), HttpStatus.OK);
      }
      //扣费取号其实应该写在一个事务里,遵循事务的原子性
      cardsService.updateRamaining(books.getMedcard(), books.getBcost(), "bcost");
    }
    //取票，修改状态，取票后该记录不再显示
    int count = booksService.addticket(books.getMedcard(), books.getBid(), books.getSnum(), books.getRed());

    return new ResponseEntity<>(new Info(count), HttpStatus.OK);
  }

  /**
   * 现场挂号
   *
   * @return
   */
  @RequestMapping(value = "/booksbuyticket", method = RequestMethod.GET)
  public ResponseEntity<?> buyTicket() {
    // 0 是上午;1 是下午
    GregorianCalendar ca = new GregorianCalendar();
    int ampm = ca.get(GregorianCalendar.AM_PM);

    List<Departs> departsList = booksService.findBuyTicket(ampm);
    return new ResponseEntity<>(departsList, HttpStatus.OK);
  }

  /**
   * 某医生挂号具体信息
   *
   * @param doid
   * @return
   */
  @RequestMapping(value = "/booksbuyticketdoctor", method = RequestMethod.POST)
  public ResponseEntity<?> buyticketDoctor(@RequestParam(value = "doid") Integer doid) {
    // 0 是上午;1 是下午
    GregorianCalendar ca = new GregorianCalendar();
    int ampm = ca.get(GregorianCalendar.AM_PM);

    Bookable bookable = booksService.findBookable(doid, ampm);
    return new ResponseEntity<>(bookable, HttpStatus.OK);
  }

  @RequestMapping(value = "/bookscheckmedcard", method = RequestMethod.POST)
  public ResponseEntity<?> checkMedcard(@RequestBody Bookable bookable) {
    Cards card = cardsService.find(bookable.getMedcard());
    if (card != null) {
      // 0 是上午;1 是下午
      GregorianCalendar ca = new GregorianCalendar();
      int ampm = ca.get(GregorianCalendar.AM_PM);

      //获得票号
      int snum = booksService.getSnum(ampm, bookable.getBid());

      Bookable resultBookable = new Bookable();
      resultBookable.setPname(card.getPname());
      resultBookable.setSnum(snum + 1);

      return new ResponseEntity<>(resultBookable, HttpStatus.OK);
    } else
      return new ResponseEntity<>(new Info(-1), HttpStatus.OK);
  }
}
