package com.hospital.registration;

import com.hospital.registration.domain.Admins;
import com.hospital.registration.domain.Departs;
import com.hospital.registration.mapper.AdminsMapper;
import com.hospital.registration.mapper.DepartsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisCacheTest {
  @Autowired
  private DepartsMapper departsMapper;

  @Autowired
  private AdminsMapper adminsMapper;

  @Test
  public void testDeparts() throws Exception {

    List<Departs> departsList1 = departsMapper.find();
    System.out.println("第一次查询：" + departsList1.size());

    List<Departs> departsList2 = departsMapper.find();
    System.out.println("第二次查询：" + departsList2.size());

    List<Departs> departsList3 = departsMapper.find();
    System.out.println("第三次查询：" + departsList3.size());
  }

  @Test
  public void findByUsername() {
    Admins admins = adminsMapper.findByUsername("admin");
    System.out.println(admins.getAname() + "....1");

    Admins admins1 = adminsMapper.findByUsername("admin");
    System.out.println(admins1.getAname() + "....2");

    Admins admins2 = adminsMapper.findByUsername("admin");
    System.out.println(admins2.getAname() + "....3");
  }
}
