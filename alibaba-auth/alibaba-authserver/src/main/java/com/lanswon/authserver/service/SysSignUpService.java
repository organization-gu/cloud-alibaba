package com.lanswon.authserver.service;


import com.lanswon.authserver.entity.Result;
import com.lanswon.authserver.entity.SysSignupInfo;

import java.util.Map;

/**
 * @Description:
 * @Author GU-YW
 * @Date 2019/12/12 20:45
 */
public interface SysSignUpService {

     /**
      * 第三方系统注册认证服务中心
      * @param sysSignupInfo
      * @return
      */
     Result signUp(SysSignupInfo sysSignupInfo);


     /**
      * 查询第三方系统注册信息
      * @param sysSignupInfo
      * @return
      */
     Result client(SysSignupInfo sysSignupInfo);
}
