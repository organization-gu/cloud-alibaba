package com.lanswon.uumfeign.service;

import com.lanswon.uumfeign.domain.dto.DataRtnDTO;
import com.lanswon.uumfeign.domain.entity.AuthUser;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @Author GU-YW
 * @Date 2019/10/15 15:47
 */
@Component
@Slf4j
public class UumServiceFallback implements FallbackFactory<UumService> {


    @Override
    public UumService create(Throwable throwable) {
        return new UumService() {
            @Override
            public DataRtnDTO<AuthUser> getUserInfoByUsername(String username) {
                DataRtnDTO<AuthUser> dataRtnDTO = new DataRtnDTO<AuthUser>();
                dataRtnDTO.status= HttpStatus.SERVICE_UNAVAILABLE.value();
                dataRtnDTO.msg="服务不可用,请稍后再试";
                return dataRtnDTO;
            }
        };
    }
}
