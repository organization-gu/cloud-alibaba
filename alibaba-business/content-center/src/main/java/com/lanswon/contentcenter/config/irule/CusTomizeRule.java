package com.lanswon.contentcenter.config.irule;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/** 整合nacos权重的负载均衡策略
 * @Author GU-YW
 * @Date 2019/10/11 17:09
 */
@Slf4j
public class CusTomizeRule extends AbstractLoadBalancerRule {

    @Autowired
    private NacosDiscoveryProperties discoveryProperties;
    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

    @Override
    public Server choose(Object key) {
        BaseLoadBalancer loadBalancer= (BaseLoadBalancer) this.getLoadBalancer();
        //想要请求的微服务名称
        String name =loadBalancer.getName();
        log.debug("微服务名称={}",name);
        //获取nacos服务发现的相关API
        try {
            Instance instance=discoveryProperties.namingServiceInstance().selectOneHealthyInstance(name);
            log.debug("选择的instance:port={},instanceId={}",instance.getPort(),instance.getInstanceId());
            return new NacosServer(instance);
        } catch (NacosException e) {
            e.printStackTrace();
            return null;
        }


    }
}
