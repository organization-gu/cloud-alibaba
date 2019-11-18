package com.lanswon.cloudgateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/** 全局过滤器
 * @Author GU-YW
 * @Date 2019/10/23 20:00
 */
@Component
@Slf4j
public class AuthFilter implements GlobalFilter, Ordered {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    RestTemplate restTemplate;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request= exchange.getRequest();
        //请求的地址
        String requestUri = request.getURI().getPath();
        //请求的方法
        String requestMethod = request.getMethod().name();

        log.debug("网关请求信息=[{}]",requestUri);
        //访问授权服务不拦截
        if(!requestUri.contains("auth")){
            HttpHeaders headers = request.getHeaders();
            ServerHttpResponse response = exchange.getResponse();
            String token = StringUtils.substringAfter(StringUtils.join(headers.get("Authorization"),","),"bearer ");
            log.debug("token=[{}]",token);
            if(StringUtils.isBlank(token)){
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().set("info","Token为空");
                return response.setComplete();
            }
            //获取服务实例
            List<ServiceInstance> serviceInstances=discoveryClient.getInstances("cloud-authserver");
            if(serviceInstances.size()==0){
                response.setStatusCode(HttpStatus.BAD_GATEWAY);
                response.getHeaders().set("info","无鉴权服务");
                return response.setComplete();
            }
            ServiceInstance serviceInstance = selectOneInstance(serviceInstances);
            String uri = serviceInstance.getUri().toString()+"/oauth/checkToken?token="+token;
            log.debug("授权服务ID=[{}]",uri);
            Map<String,Object> result=null;
            result = restTemplate.getForObject(uri,Map.class);
            log.debug("验证token结果=[{}]",result);
            if(!result.get("code").equals(HttpStatus.OK.getReasonPhrase())){
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().set("info","TOKEN Invalid");
                return response.setComplete();
            }
            List<String> sourceUrl = (List<String>) result.get("authorities");
            log.debug("当前拥有的权限[{}]",sourceUrl);

            if(!checkPermission(requestUri,requestMethod,sourceUrl)){
                response.setStatusCode(HttpStatus.FORBIDDEN);
                response.getHeaders().set("info","NO Permission");
                return response.setComplete();
            }

        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private ServiceInstance selectOneInstance(List<ServiceInstance> serviceInstances){
        if (serviceInstances.size()==1){
            return serviceInstances.get(0);
        }
        int size = serviceInstances.size();
        int index = RandomUtils.nextInt(0,size);
        return serviceInstances.get(index);
    }

    private Boolean checkPermission(String url,String method,List<String> sourceUrl){
        Boolean has = false;
        if(sourceUrl==null||sourceUrl.size()==0){
            return has;
        }
        for(String permission:sourceUrl ){
            String [] str=StringUtils.splitPreserveAllTokens(permission,"|");
            if(antPathMatcher.match(str[0],url)&&str[1].equals(method)){
                has = true;
                break;
            }
        }

        return has;
    }
}
