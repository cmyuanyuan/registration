package com.hospital.registration.mapper;

import com.hospital.registration.domain.Bookable;
import com.hospital.registration.domain.Doctors;
import com.hospital.registration.domain.WeekBean;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * @author SONG
 */
public interface BookableMapper {
  //查科室排班信息
  List<WeekBean> findBK(@Param("bklist") List<String> bklist, @Param("deid") Integer deid);

  //查科室有效医生
  @Select("select * from doctors where deid = #{deid} and doexist = 1")
  List<Doctors> findAllDoc(@Param("deid") Integer deid);

  @Insert("INSERT INTO Bookable(doid,bdate,starttime,used,bnum,ynum,xcum,xcyum) VALUES (#{bk.doctors.doid},#{bk.bdate},#{bk.starttime},#{bk.used},#{bk.bnum},#{bk.ynum},#{bk.xcum},#{bk.xcyum})")
  Integer addBK(@Param("bk") Bookable bk);

  //删除排班
  @Delete("delete from bookable where doid = #{doid} and bdate = #{date1}")
  Integer delBK(@Param("doid") Integer doid, @Param("date1") Date date1);
}
