package com.lanswon.authserver.service.impl;

import com.lanswon.authcore.support.SimpleResponse;
import com.lanswon.authserver.dao.SysSignupInfoMapper;
import com.lanswon.authserver.dao.UserMapper;
import com.lanswon.authserver.entity.Result;
import com.lanswon.authserver.entity.SysSignupInfo;
import com.lanswon.authserver.entity.User;
import com.lanswon.authserver.service.SysSignUpService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @Description:
 * @Author GU-YW
 * @Date 2019/12/12 20:55
 */
@Service
public class SysSignUpServiceImpl implements SysSignUpService {

    @Resource
    SysSignupInfoMapper signupInfoMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    PasswordEncoder passwordEncoder;

    @Autowired
    JdbcClientDetailsService jdbcClientDetailsService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result signUp(SysSignupInfo sysSignupInfo) {
        //获取数据库第三方系统信息
        ClientDetails details = null;
        try {
            details = jdbcClientDetailsService.loadClientByClientId(sysSignupInfo.getSysClientId());
        } catch (NoSuchClientException e) {
           //不需要处理
        }
        if(!Objects.isNull(details)){
            return Result.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .simpleResponse(new SimpleResponse("clientId 已经存在"))
                    .build();
        }

        if(StringUtils.isBlank(sysSignupInfo.getSysPassword())){
            sysSignupInfo.setSysPassword("123456");
        }
        String tableName = sysSignupInfo.getSysClientId()+"_user";
        //新建第三方系统user表
        signupInfoMapper.createTable(tableName);

        //构建spring内部第三方系统信息
        BaseClientDetails clientDetails=new BaseClientDetails(sysSignupInfo.getSysClientId(), null, "all,read,write",
                null, null, sysSignupInfo.getSysIndex());
        clientDetails.setAccessTokenValiditySeconds(sysSignupInfo.getTokenValiditySeconds());
        clientDetails.setClientSecret(passwordEncoder.encode(sysSignupInfo.getSysPassword()));
        clientDetails.setRefreshTokenValiditySeconds(sysSignupInfo.getRefreshTokenValiditySeconds());

        jdbcClientDetailsService.addClientDetails(clientDetails);

        //构建用户信息
        User admin = User.builder().username("admin").password(passwordEncoder.encode("123456")).accountEnable(true).accountNonExpired(true).accountNonLock(true)
                .credentialNonExpired(true).createdTime(new Date()).updatedTime(new Date()).tableName(tableName)
                .credentialNonExpired(true).accountNonLock(true).accountNonExpired(true).accountEnable(true).build();
        userMapper.insertUser(admin);
        return Result.builder()
                .status(HttpStatus.OK.value())
                .simpleResponse(new SimpleResponse(sysSignupInfo))
                .build();
    }

    @Override
    public Result client(SysSignupInfo sysSignupInfo) {

        ClientDetails details = jdbcClientDetailsService.loadClientByClientId(sysSignupInfo.getSysClientId());
        if(details != null){
            if(!passwordEncoder.matches(sysSignupInfo.getSysPassword(),details.getClientSecret())){
                return Result.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .simpleResponse(new SimpleResponse("注册密码错误"))
                        .build();
            }
        }
        return Result.builder()
                .status(HttpStatus.OK.value())
                .simpleResponse(new SimpleResponse(details))
                .build();

    }


}
