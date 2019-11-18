package com.lanswon.uumfeign.service;

import com.lanswon.uumfeign.domain.dto.DataRtnDTO;
import com.lanswon.uumfeign.domain.entity.AuthUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Description: 调用UUM服务
 * @Author GU-YW
 * @Date 2019/11/18 10:03
 */
@FeignClient(value = "CLOUD-UUM",fallbackFactory = UumServiceFallback.class)
public interface UumService {

    /**
     * 根据用户名获得用户信息
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping("/uum/user/{username}")
    DataRtnDTO<AuthUser> getUserInfoByUsername(@PathVariable("username") String username);
}
