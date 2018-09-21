package com.hospital.registration.mapper;

import com.hospital.registration.domain.*;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * 医生就诊
 *
 * @author SONG
 */
public interface DoctorvisMapper {
  /**
   * 查询某医生的挂号单
   *
   * @param doid
   * @return
   */
  List<Registration> findRegistrationByDoic(@Param("doid") Integer doid);

  /**
   * 修改订单状态
   */
  @Update("update registration set state=#{state} where rid=#{rid}")
  int stateprg(@Param("rid") Integer rid, @Param("state") Integer state);

  /**
   * 查看病人病例的方法
   */
  List<History> findHistory(@Param("cid") Integer cid, @Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

  /**
   * 查看药方项
   */
  List<Drugandprescripton> findDrug(@Param("prid") Integer prid);

  /**
   * 今天挂号单卡号状态
   *
   * @param rid
   * @return
   */
  @Select("select state,r.by2 ,r.by1 from registration r, bookable b,cards c where r.bid=b.bid and c.cid=r.cid and  r.rid=#{rid} and to_days(b.bdate) = to_days(now()) and state > 0")
  MedicalState getMedical(@Param("rid") Integer rid);

  /**
   * 查询药品 部门和数量
   *
   * @return
   */
  List<Druganddeparts> getDrug(
          @Param("deid") Integer deid,
          @Param("price1") Double price1,
          @Param("price2") Double price2);

  /**
   * 点击药方 创建 药方单 并拿到 药方id ,初始化未结账 1
   */
  @Insert("insert into prescripton(cid,doid,drstate,prdate) values(#{cid},#{doid},1,now())")
  int allPrescripton(
          @Param("cid") Integer cid,
          @Param("doid") Integer doid);

  /**
   * 拿到 药方id
   */
  @Select("select max(prid) from prescripton")
  int findprid();

  /**
   * 添加药方项
   */
  @Insert(" insert into drugandprescripton(drid,prid,drnum) values(#{drid},#{prid},#{drnum})")
  int alldrugpres(
          @Param("drid") Integer drid,
          @Param("prid") Integer prid,
          @Param("drnum") Integer drnum);

  @Update("update registration set by2=#{by2} where rid =#{rid}")
  int updaby2(@Param("rid") Integer rid, @Param("by2") Integer by2);

  /**
   * 修改药方项
   */
  @Update("update drugandprescripton set drnum=#{drnum} where drid=#{drid} and prid=#{prid}")
  int updatedrug(
          @Param("drnum") Integer drnum,
          @Param("drid") Integer drid,
          @Param("prid") Integer prid);

  /**
   * 删除药方项
   */
  @Delete("delete from drugandprescripton where drid=#{drid} and prid=#{prid}")
  int deletedrug(
          @Param("drid") Integer drid,
          @Param("prid") Integer prid);

  /**
   * 给病人添加病例
   *
   * @return
   */
  @Insert(" insert into history(cid,doid,prid,brief,deal,hidate) values(#{cid},#{doid},#{prid},#{brief},#{deal},now())")
  int allHistory(
          @Param("cid") Integer pid,
          @Param("doid") Integer doid,
          @Param("prid") Integer prid,
          @Param("brief") String brief,
          @Param("deal") Integer deal);

  /**
   * 修改订单状态的
   */
  @Update("update registration set state=#{state} where rid=#{rid}")
  int updarig(@Param("rid") Integer rid, @Param("state") Integer state);

  @Update("update registration set by1=#{brief} , state = 2 where rid=#{rid}")
  int saveMedical(@Param("rid") Integer rid, @Param("brief") String brief);
}
