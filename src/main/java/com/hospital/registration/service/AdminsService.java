package com.hospital.registration.service;

import com.hospital.registration.domain.Admins;
import com.hospital.registration.mapper.AdminsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 用户业务逻辑类
 *
 * @author SONG
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class AdminsService {
  @Autowired
  private AdminsMapper adminsMapper;

  public List<Admins> getAdmins(Integer state) {
    return adminsMapper.getAdmins(state);
  }

  // 启用和停用
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
  public int changeState(Integer aexist, Integer aid) {
    return adminsMapper.changeState(aexist, aid);
  }

  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
  public int add(Admins admins) {
    int result = 0;
    if (0 == adminsMapper.findName(admins.getAname())) {
      admins.setEmail(admins.getAname() + "@gmail.com");
      admins.setLastPasswordResetDate(new Date());
      admins.setAexist(1);
      // 新增用户
      result = adminsMapper.addAdmins(admins);

      int aid = adminsMapper.getAdminsId();
      // 授权
      result = adminsMapper.addUserAuthority(aid, admins.getState());
    }
    return result;
  }

  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
  public int changePassword(Integer id, String password) {
    return adminsMapper.changePassword(id, password);
  }

  @Deprecated
  public Admins find(String aname, String pwd) {
    Admins admins = adminsMapper.find(aname, pwd);
    if (admins != null) {
      adminsMapper.updateLoginTime(admins.getAid());
      admins.setLoginTime(new Date());
    }
    return admins;
  }
}
