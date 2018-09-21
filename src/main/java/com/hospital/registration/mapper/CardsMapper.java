package com.hospital.registration.mapper;

import com.hospital.registration.domain.Cards;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 医疗卡映射器接口
 *
 * @author SONG
 */
public interface CardsMapper {
  /**
   * 注册医疗卡
   *
   * @param cards
   * @return
   */
  @Insert("insert into cards(pname,gender,phone,idcard,pwd,newdate,ramaining,doexist) values(#{pname},#{gender},#{phone},#{idcard},#{pwd},now(),#{ramaining},#{doexist})")
  int add(Cards cards);

  /**
   * 充值 | 收费
   */
  @Update("update cards set ramaining=#{ramaining} where cid=#{cid}")
  int updateRamaining(@Param("cid") Integer cid, @Param("ramaining") Double ramaining);

  /**
   * 医疗卡启用 | 停用
   */
  @Update("update cards set doexist=#{doexist} where cid=#{cid}")
  int enable(@Param("cid") Integer cid, @Param("doexist") Integer doexist);

  /**
   * 余额查询
   */
  @Select("select cid,pname,gender,phone,idcard,pwd,newdate,ramaining,doexist from cards where cid=#{cid} ")
  Cards findByIdcard(@Param("cid") Integer cid);

  /**
   * 医疗卡查询
   */
  @Select("select cid,pname,gender,phone,idcard,pwd,newdate,ramaining,doexist from cards order by cid desc")
  List<Cards> find();
}
