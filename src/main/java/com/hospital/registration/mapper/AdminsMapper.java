package com.hospital.registration.mapper;

import com.hospital.registration.domain.Admins;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 用户映射器接口
 *
 * @author SONG
 */
public interface AdminsMapper {
  /**
   * 登录 (在用)
   *
   * @param aname
   * @param pwd
   * @return
   */
  @Deprecated
  @Select("select aid,aname,pwd,state,aexist,login_time loginTime,doid from admins where aexist=1 and aname=#{aname} and pwd=#{pwd}")
  Admins find(@Param("aname") String aname, @Param("pwd") String pwd);

  /**
   * 查询登录用户 [1 普通管理员 3 挂号收银员 4 划价发药员] - 0 超级管理员 2 医生
   *
   * @param state
   * @return
   */
  @Select("select * from admins where state = #{state}")
  List<Admins> getAdmins(@Param("state") Integer state);

  @Update("update admins set aexist=#{aexist} where aid = #{aid}")
  int changeState(@Param("aexist") Integer aexist, @Param("aid") Integer aid);

  /**
   * 修改登录时间
   *
   * @param aid
   * @return
   */
  @Update("update admins set login_time=now() where aid = #{aid}")
  int updateLoginTime(@Param("aid") Integer aid);

  /**
   * 修改密码
   *
   * @param aid
   * @param password
   * @return
   */
  @Update("update admins set pwd=#{password} where aid = #{aid}")
  int changePassword(@Param("aid") Integer aid, @Param("password") String password);

  // see resources/mybatis/mapper/AdminsMapper.xml
  // @Select("select aid,aname,pwd,state,aexist,email,lastPasswordResetDate from admins where aexist=1 and aname=#{aname}")
  Admins findByUsername(@Param("aname") String aname);

  @Insert("insert into admins(aname,pwd,state,by1,aexist,email,lastPasswordResetDate,doid) values(#{aname},#{pwd},#{state},#{by1},#{aexist},#{email},#{lastPasswordResetDate},#{doid})")
  int addAdmins(Admins admins);

  @Insert("INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (#{aid}, #{authId})")
  int addUserAuthority(@Param("aid") Integer aid, @Param("authId") Integer authId);

  @Select("select max(aid) from admins")
  int getAdminsId();

  @Select(" select count(*) from admins where aname=#{aname}")
  int findName(@Param("aname") String aname);
}
