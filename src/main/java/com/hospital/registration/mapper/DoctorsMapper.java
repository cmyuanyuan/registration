package com.hospital.registration.mapper;

import com.hospital.registration.domain.Doctors;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 医生映射器接口
 *
 * @author SONG
 */
//@CacheNamespace(implementation = com.hospital.registration.util.RedisCache.class,readWrite = false)
public interface DoctorsMapper {

  @Select("select * from doctors where doid=#{doid}")
  Doctors findById(Integer doid);

  List<Doctors> find();

  @Update("update doctors set deid=#{deid},title=#{title},doname=#{doname},info=#{info},bcost=#{bcost},doexist=#{doexist},pcreg=#{pcreg},xcreg=#{xcreg},monam=#{monam},monpm=#{monpm},tueam=#{tueam},tuepm=#{tuepm},wedam=#{wedam},wedpm=#{wedpm},thuam=#{thuam},thupm=#{thupm},friam=#{friam},fripm=#{fripm},satam=#{satam},satpm=#{satpm},sunam=#{sunam},sumpm=#{sumpm} where doid=#{doid}")
    //@Options(flushCache = Options.FlushCachePolicy.TRUE, timeout = 20000)
  int modify(Doctors doctors);

  @Update("update doctors set doexist=#{doexist} where doid=#{doid}")
  int modifyState(@Param("doid") Integer doid, @Param("doexist") Integer doexist);

  @Insert("insert into doctors(deid,title,doname,info,bcost,doexist,pcreg,xcreg,monam,monpm,tueam,tuepm,wedam,wedpm,thuam,thupm,friam,fripm,satam,satpm,sunam,sumpm) values(#{deid},#{title},#{doname},#{info},#{bcost},1,#{pcreg},#{xcreg},#{monam},#{monpm},#{tueam},#{tuepm},#{wedam},#{wedpm},#{thuam},#{thupm},#{friam},#{fripm},#{satam},#{satpm},#{sunam},#{sumpm})")
  int add(Doctors doctors);

  @Select("select max(doid) from doctors")
  int getDoctorId();

  @Update("update doctors set doexist=#{doexist} where doid=#{doid}")
  int updateState(@Param("doid") Integer doid, @Param("doexist") Integer doexist);

  @Update("update admins set aexist=#{aexist} where aid =#{aid}")
  int updateAdState(@Param("aid") Integer aid, @Param("aexist") Integer aexist);

  @Select("select aid from admins where doid = #{doid}")
  Integer findDocAid(@Param("doid") Integer doid);

  @Select("select count(red) from reservation r left join bookable  b on r.bid=b.bid where to_days(bdate) > to_days(now()) and b.doid=#{doid} and state <> 0 ")
  int selectRed(Integer doid);

  @Select("select count(rid) from registration r left join bookable  b on r.bid=b.bid where to_days(bdate) > to_days(now()) and b.doid=#{doid} and state <>0 ")
  int selectRid(Integer doid);

  @Select("select state from admins where doid = #{doid}")
  Integer ckState(Integer doid);

  @Select("select deexist from departs e,doctors o where e.deid= o.deid and doid=#{doid}")
  int ckDeState(@Param("doid") Integer doid);

  /**
   * 查值班医生
   *
   * @param deid      科室 id
   * @param starttime 上午 0 | 下午 1
   * @return
   */
  //	@Select("select doname from doctors where deid=#{deid}")
  //用pcreg装现场可预约, xcreg现场已预约
  @Select("select DISTINCT d.doid,d.doname,d.title,b.xcum pcreg,b.xcyum xcreg from bookable b,doctors d where b.doid = d.doid and to_days(b.bdate) = to_days(now()) and b.starttime =#{starttime} and d.deid = #{deid}")
  //@Options(useCache = true, flushCache = Options.FlushCachePolicy.TRUE, timeout = 10000)
  List<Doctors> findByDeId(@Param("deid") Integer deid, @Param("starttime") Integer starttime);

  @Select("select dename by1,bcost,doname,doid from departs de,doctors do where de.deid=do.deid and doid=#{doid}")
  Doctors findDoctor(@Param("doid") Integer doid);

  // 是否可以停用该科室(判断该科室下如若有医生则不能停用,没则可以停用)
  @Select("select count(doid) from doctors where deid=#{deid}")
  int findDoctorsByDeid(@Param("deid") Integer deid);
}
