package com.lanswon.contentcenter.config.sentinel;

import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 统一处理sentinel异常页面
 * @Author GU-YW
 * @Date 2019/10/15 17:05
 */
@Component
public class MyUrlBlockHandler implements UrlBlockHandler {
    @Override
    public void blocked(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws IOException {
        ErrorMsg msg = new ErrorMsg();
        if (e instanceof FlowException) {
            msg = ErrorMsg.builder()
                    .status(100)
                    .msg("限流了")
                    .build();
        } else if (e instanceof DegradeException) {
            msg = ErrorMsg.builder()
                    .status(101)
                    .msg("降级了")
                    .build();
        } else if (e instanceof ParamFlowException) {
            msg = ErrorMsg.builder()
                    .status(102)
                    .msg("热点参数限流")
                    .build();
        } else if (e instanceof SystemBlockException) {
            msg = ErrorMsg.builder()
                    .status(103)
                    .msg("系统规则（负载/...不满足要求）")
                    .build();
        } else if (e instanceof AuthorityException) {
            msg = ErrorMsg.builder()
                    .status(104)
                    .msg("授权规则不通过")
                    .build();
        }
        // http状态码
        httpServletResponse.setStatus(500);
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setHeader("Content-Type", "application/json;charset=utf-8");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        // spring mvc自带的json操作工具，叫jackson
        new ObjectMapper()
                .writeValue(
                        httpServletResponse.getWriter(),
                        msg
                );
    }
}
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class ErrorMsg {
    private Integer status;
    private String msg;
}