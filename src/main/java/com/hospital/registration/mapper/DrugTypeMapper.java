package com.hospital.registration.mapper;

import com.hospital.registration.domain.Drugtype;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface DrugTypeMapper {
  //全部类型
  @Select("select dyid,dyname,dystate from DRUGTYPE ")
  List<Drugtype> findAllType();

  //可用的药品类型
  @Select("select dyid,dyname,dystate from DRUGTYPE where dystate = 1")
  List<Drugtype> findDrugtype();

  //新增类型
  @Insert("insert into drugtype(dyname,dystate) values(#{dyname},1)")
  int add(@Param("dyname") String dyname);

  //类型中可用药品
  @Select("select count(*) from drug where dyid = #{dyid} and drstate = 1")
  int useDrByTp(@Param("dyid") Integer dyid);

  //改类别状态
  @Update("update drugtype set dystate = #{dystate} where dyid = #{dyid}")
  int updateTypeState(@Param("dyid") Integer dyid, @Param("dystate") Integer dystate);
}
