package com.hospital.registration.mapper;

import com.hospital.registration.domain.Bookable;
import com.hospital.registration.domain.Books;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 预约排班映射器接口
 *
 * @author SONG
 */
@CacheNamespace(implementation = com.hospital.registration.util.RedisCache.class, flushInterval = 60000, readWrite = false)
public interface BooksMapper {

  //某部门当天的挂号人数
  @Select("select count(snum) from registration r,bookable b,doctors do,departs de where r.bid=b.bid and do.doid=b.doid and de.deid=do.deid and de.deid=#{deid} and to_days(bdate) = to_days(now()) ")
  int getToday(@Param("deid") Integer deid);

  //某部门昨天的挂号人数
  @Select("select count(snum) from registration r,bookable b,doctors do, departs de where r.bid=b.bid and do.doid=b.doid and de.deid=do.deid and de.deid=#{deid} and to_days(now()) - to_days(bdate) = 1 ")
  int getYesterday(@Param("deid") Integer deid);

  //某部门本周的挂号人数
  @Select("select count(snum) from registration r,bookable b,doctors do, departs de where r.bid=b.bid and do.doid=b.doid and de.deid=do.deid and de.deid=#{deid} and YEARWEEK(date_format(bdate,'%Y-%m-%d')) = YEARWEEK(now())")
  int getWeek(@Param("deid") Integer deid);

  //某部门本月挂号人数
  @Select("select count(snum) from registration r,bookable b,doctors do, departs de where r.bid=b.bid and do.doid=b.doid and de.deid=do.deid and de.deid=#{deid} and date_format(bdate,'%Y%m') = date_format(curdate(),'%Y%m')")
  int getMonth(@Param("deid") Integer deid);

  //某部门本季度挂号人数
  @Select("select count(snum) from registration r,bookable b,doctors do, departs de where r.bid=b.bid and do.doid=b.doid and de.deid=do.deid and de.deid=#{deid} and QUARTER(bdate)=QUARTER(now())")
  int getQuarter(@Param("deid") Integer deid);

  /**
   * 取预约号
   *
   * @param starttime
   * @param state
   * @param idcard
   * @return
   */
  List<Books> findAllBook(@Param("starttime") Integer starttime,
                          @Param("state") Integer state,
                          @Param("idcard") String idcard);

  /**
   * 是否绑卡
   *
   * @param red
   * @param card
   * @return
   */
  @Select("select count(*) from  reservation r,patients p,cards c where r.pid = p.pid and c.idcard = p.idcard and r.red = #{red} and c.cid = #{card}")
  @Options(flushCache = Options.FlushCachePolicy.TRUE, timeout = 20000)
  Integer bdCard(@Param("red") Integer red, @Param("card") Integer card);

  Books findBooksById(@Param("red") Integer red);

  /**
   * 排班表
   *
   * @param red
   * @return
   */
  @Select(" select bid from reservation where red = #{red} ")
  @Options(flushCache = Options.FlushCachePolicy.TRUE, timeout = 20000)
  Integer findbid(@Param("red") Integer red);

  /**
   * 获得票号
   *
   * @param starttime
   * @param bid
   * @return
   */
  @Select("select count(snum) from registration r,bookable b where to_days(b.bdate) = to_days(now()) and starttime=#{starttime} and r.bid=b.bid and b.bid = #{bid}")
  @Options(flushCache = Options.FlushCachePolicy.TRUE, timeout = 20000)
  int getSnum(@Param("starttime") Integer starttime, @Param("bid") Integer bid);

  /**
   * @param medcard 诊疗卡号
   * @param bid
   * @param snum
   * @return
   */
  // 挂完号状态应该为 1:未看
  @Insert("insert into registration(cid,bid,snum,state,by2) values(#{medcard},#{bid},#{snum},1,0)")
  @Options(flushCache = Options.FlushCachePolicy.TRUE, timeout = 20000)
  int addticket(@Param("medcard") Integer medcard, @Param("bid") Integer bid, @Param("snum") Integer snum);

  @Select("select xcyum from bookable where bid=#{bid}")
  @Options(flushCache = Options.FlushCachePolicy.TRUE, timeout = 20000)
  int findByBid(@Param("bid") Integer bid);

  @Update("update bookable set xcyum=#{xcyum} where bid=#{bid} ")
  @Options(flushCache = Options.FlushCachePolicy.TRUE, timeout = 20000)
  int updatebookable(@Param("bid") Integer bid, @Param("xcyum") Integer xcyum);

  // state 改为 0 表示网约已取号
  @Update("update reservation set state = 0 where red = #{red}")
  @Options(flushCache = Options.FlushCachePolicy.TRUE, timeout = 20000)
  Integer updateRestate(@Param("red") Integer red);

  /**
   * 某医生挂号具体信息
   *
   * @param doid
   * @param starttime
   * @return
   */
  @Select("select DISTINCT bid,xcum,xcyum from bookable where doid=#{doid} and to_days(bdate) = to_days(now()) and starttime=#{starttime}")
  @Options(flushCache = Options.FlushCachePolicy.TRUE, timeout = 20000)
  Bookable findByDoid(@Param("doid") Integer doid,
                      @Param("starttime") Integer starttime);

}
