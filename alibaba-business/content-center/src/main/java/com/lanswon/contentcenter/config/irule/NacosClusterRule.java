package com.lanswon.contentcenter.config.irule;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.core.Balancer;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * nacos同集群优先调用及整合nacos权重的负载均衡策略
 * @Author GU-YW
 * @Date 2019/10/11 20:24
 */
@Slf4j
public class NacosClusterRule extends AbstractLoadBalancerRule {

    @Autowired
    private NacosDiscoveryProperties discoveryProperties;

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

    @Override
    public Server choose(Object key) {
        //获取集群名
        String clusterName=discoveryProperties.getClusterName();

        BaseLoadBalancer loadBalancer= (BaseLoadBalancer) this.getLoadBalancer();
        //想要请求的微服务名称
        String name =loadBalancer.getName();

        NamingService namingService=discoveryProperties.namingServiceInstance();

        try {
            //配置了集群
            if(StringUtils.isNotEmpty(clusterName)){
                List<String> clusters=new ArrayList<>();
                clusters.add(clusterName);
//             ========================最新方法=======================

                Instance instance=namingService.selectOneHealthyInstance(name,clusters, true);

//             =====================以前版本手动添加==================
//            //找到指定服务的所有实例 A
//            List<Instance> instances= namingService.selectInstances(name,true);
//            //过滤出相同集群下的微服务实例 B
//            List<Instance> sameIntances=instances.stream()
//                    .filter(instance -> Objects.equals(instance.getClusterName(),clusterName))
//                    .collect(Collectors.toList());
//            //如果B是空，就用A
//            List<Instance> toBeChose=new ArrayList<>();
//            if(sameIntances.isEmpty()){
//                toBeChose=instances;
//            }else{
//                toBeChose=sameIntances;
//            }
//            //基于权重的负载均衡算法，返回1个实例
//            Instance instance=LoadBalancer.getHost(toBeChose);
                log.debug("选择的instance:port={},instanceId={}",instance.getPort(),instance.getInstanceId());
                return new NacosServer(instance);
            }else{
                Instance instance = namingService.selectOneHealthyInstance(name, true);
                log.debug("选择的instance:port={},instanceId={}",instance.getPort(),instance.getInstanceId());
                return new NacosServer(instance);
            }

        } catch (NacosException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 调用权重负载均衡算法 原Balancer方法无法调用
     */
    static class LoadBalancer extends Balancer {
        public static Instance getHost(List<Instance> hosts) {
            return  getHostByRandomWeight( hosts);
        }
    }
}
