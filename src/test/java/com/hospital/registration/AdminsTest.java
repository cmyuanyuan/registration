package com.hospital.registration;

import com.hospital.registration.domain.Admins;
import com.hospital.registration.mapper.AdminsMapper;
import com.hospital.registration.util.MD5;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 用户测试类
 *
 * @author SONG
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminsTest {
  @Autowired
  private AdminsMapper adminsMapper;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  public void getPassword() {
    /**
     * 任何应用考虑到安全,绝不能明文的方式保存密码。
     * 密码应该通过哈希算法进行加密。
     * 有很多标准的算法比如SHA或者MD5,结合salt(盐)是一个不错的选择。
     * Spring Security 提供了BCryptPasswordEncoder类,
     * 实现Spring的PasswordEncoder接口使用BCrypt强哈希方法来加密密码。
     *
     * BCrypt强哈希方法:每次加密的结果都不一样。
     */
    System.out.println(passwordEncoder.encode("admin"));
  }

  @Deprecated
  @Test
  public void login() {
    // BCrypt 加密后,原登录失效
    Admins admins = adminsMapper.find("admin", MD5.getInstance().getMD5ofStr("admin"));
    System.out.println(admins.getAname() + " " + admins.getAexist());

    if (admins != null) {
      adminsMapper.updateLoginTime(admins.getAid());
    }

    // 通过 assertThat 进行断言判断
    assertThat(admins.getAexist()).isEqualTo(1);
    assertThat(admins.getAname()).isEqualTo("admin");
  }
}
