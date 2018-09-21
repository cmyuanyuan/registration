package com.hospital.registration.mapper;

import com.hospital.registration.domain.Departs;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 部门映射器接口
 *
 * @author SONG
 */
@CacheNamespace(implementation = com.hospital.registration.util.RedisCache.class, flushInterval = 60000, readWrite = false)
public interface DepartsMapper {

  @Select("select * from departs")
  List<Departs> find();

  /**
   * 查询所有可用科室
   *
   * @return
   */
  @Select("select * from departs where deexist=1 ")
  List<Departs> findDeparts();

  @Select("select * from departs where deid=#{deid}")
  Departs findById(Integer deid);

  // 添加科室
  @Insert("insert into departs(dename,intro,deexist) values(#{dename},#{intro},#{deexist})")
  @Options(flushCache = Options.FlushCachePolicy.TRUE, timeout = 20000)
  int add(Departs departs);

  // 修改科室
  @Update("update departs set dename=#{dename},intro=#{intro}, deexist=#{deexist} where deid=#{deid}")
  @Options(flushCache = Options.FlushCachePolicy.TRUE, timeout = 20000)
  int modify(Departs departs);

  // 修改科室状态
  @Update("update departs set deexist=#{deexist} where deid=#{deid}")
  @Options(flushCache = Options.FlushCachePolicy.TRUE, timeout = 20000)
  int changeState(@Param("deid") Integer deid, @Param("deexist") Integer deexist);
}
