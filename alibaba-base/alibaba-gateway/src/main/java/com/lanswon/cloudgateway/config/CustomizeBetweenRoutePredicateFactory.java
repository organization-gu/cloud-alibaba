package com.lanswon.cloudgateway.config;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * 自定义路由匹配规则 类命名规则必须 xxxRoutePredicateFactory
 * @Author GU-YW
 * @Date 2019/10/22 10:04
 */
@Component
public class CustomizeBetweenRoutePredicateFactory extends AbstractRoutePredicateFactory<CustomizeBetweenConfig> {

    public CustomizeBetweenRoutePredicateFactory() {
        super(CustomizeBetweenConfig.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(CustomizeBetweenConfig config) {
        LocalTime start = config.getStart();
        LocalTime end = config.getEnd();
        return exchange ->{
            LocalTime now = LocalTime.now();
            return now.isAfter(start)&& now.isBefore(end);
        };
    }

    /**
     * 设置参数顺序
     * @return
     */
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("start","end");
    }


    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        System.out.println(formatter.format(LocalTime.now()));
    }
}
