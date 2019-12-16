package com.lanswon.authserver.entity;

import com.lanswon.authcore.support.SimpleResponse;
import lombok.Builder;
import lombok.Data;

/**
 * @Description:
 * @Author GU-YW
 * @Date 2019/12/12 21:08
 */
@Data
@Builder
public class Result {
    int status;

    SimpleResponse simpleResponse;
}
