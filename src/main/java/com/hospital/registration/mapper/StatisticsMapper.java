package com.hospital.registration.mapper;

import com.hospital.registration.domain.Drug;
import com.hospital.registration.domain.Drugtype;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@CacheNamespace(implementation = com.hospital.registration.util.RedisCache.class, flushInterval = 60000, readWrite = false)
public interface StatisticsMapper {
  // 总病人列表
  @Select("select  count(*) from history h where h.doid = #{doid} and MONTH(h.hidate) = #{month} and YEAR(h.hidate) = YEAR(now())")
  Integer findAllP(@Param("doid") Integer doid, @Param("month") Integer month);

  //回家人数
  @Select("select  count(*) from history h where h.doid = #{doid} and h.deal=1 and MONTH(h.hidate) = #{month} and YEAR(h.hidate) = YEAR(now())")
  Integer findHomeP(@Param("doid") Integer doid, @Param("month") Integer month);

  //开药人数
  @Select("select  count(*) from history h where h.doid = #{doid} and h.deal=2 and MONTH(h.hidate) = #{month} and YEAR(h.hidate) = YEAR(now())")
  Integer findDrugP(@Param("doid") Integer doid, @Param("month") Integer month);

  //住院人数
  @Select("select  count(*) from history h where h.doid = #{doid} and h.deal=3 and MONTH(h.hidate) = #{month} and YEAR(h.hidate) = YEAR(now())")
  Integer findHosP(@Param("doid") Integer doid, @Param("month") Integer month);

  //统计药品数量
  @Select("select d.drid,d.drname,d.drsum from drug d where d.dyid =#{dyid} ")
  List<Drug> statDrugs(@Param("dyid") Integer dyid);

  //查各药品类型销量
  @Select("select dy.dyid, dy.dyname ,sum(dp.drnum) by2 from (drugtype dy left join drug dr on dy.dyid=dr.dyid ) left join (select dp.drid,dp.drnum,pp.* from  drugandprescripton dp left join prescripton pp on pp.prid=dp.prid where date_format(pp.prdate,'%Y-%m') =#{mon} )dp on  dr.drid = dp.drid where dy.dyid=#{dyid}  group by  dy.dyid,dy.dyname  ")
  Drugtype statSalDt(@Param("dyid") Integer dyid, @Param("mon") String mon);

  @Select("select dr.drid,dr.drname,sum(dp.drnum) by2 from drug dr left join (select dp.drid,dp.drnum,pp.* from  drugandprescripton dp left join prescripton pp on pp.prid=dp.prid where date_format(pp.prdate,'%Y-%m') =#{mon}  )dp on dr.drid = dp.drid where dr.drid = #{drid} group by dr.drid,dr.drname")
  Drug statSalDr(@Param("drid") Integer drid, @Param("mon") String mon);

  //统计每类药数量 --借dystate字段装数量
  @Select("select t.dyid,t.dyname,count(d.drid) dystate from drugtype t left join drug d on  t.dyid = d.dyid   group by t.dyid,t.dyname")
  List<Drugtype> statTypes();
}
