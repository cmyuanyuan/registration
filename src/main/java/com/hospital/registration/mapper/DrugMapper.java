package com.hospital.registration.mapper;

import com.hospital.registration.domain.Departs;
import com.hospital.registration.domain.Drug;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface DrugMapper {

  //查所有药
  List<Drug> findDrugs(@Param("price1") Double price1, @Param("price2") Double price2);

  //查药
  @Select("select * from drug where drid = #{drid}")
  Drug findById(Integer drid);

  //新增药品
  @Insert("insert into drug(dyid,drname,drsum,drprice,drstate) values (#{dyid},#{drname},#{drsum},#{drprice},1 )")
  int add(Drug drug);

  //查药品主键
  @Select("select max(drid) from drug")
  int findDrugId();

  //新增药品科室关系
  @Insert("insert into druganddeparts(drid,deid) values(#{drid},#{deid})")
  int addDrugDe(@Param("drid") Integer drid, @Param("deid") Integer deid);

  //修改药品
  @Update("update drug set dyid=#{dyid},drname=#{drname},drsum=#{drsum},drprice=#{drprice},drstate=#{drstate} where drid = #{drid}")
  int modifyDrug(Drug drug);

  //删除药品科室关系
  @Delete("delete from druganddeparts where drid = #{drid}")
  int removeDrDe(@Param("drid") Integer drid);

  //查药品科室关系
  @Select("select t.deid,t.dename from departs t left join druganddeparts d on t.deid = d.deid where d.drid = #{drid}")
  List<Departs> findDrDe(@Param("drid") Integer drid);

  //检查类型状态
  @Select("select dystate from drug d,drugtype t where d.dyid = t.dyid and drid = #{drid} ")
  int ckDyState(Integer drid);

  //改状态
  @Update("update drug set drstate = #{drstate} where drid = #{drid}")
  int changeState(@Param("drid") Integer drid, @Param("drstate") Integer drstate);
}
