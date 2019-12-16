package com.lanswon.authserver.dao;

import com.lanswon.authserver.entity.SysSignupInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;

/**
 * @Description:
 * @Author GU-YW
 * @Date 2019/12/14 11:06
 */
public interface SysSignupInfoMapper extends BaseMapper<SysSignupInfo> {

    int createTable(@Param("tableName")String tableName);
}
