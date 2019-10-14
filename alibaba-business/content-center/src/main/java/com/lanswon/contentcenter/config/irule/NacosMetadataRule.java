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
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** 整合nacos元数据及同集群和权重的负载均衡策略
 * @Author GU-YW
 * @Date 2019/10/11 17:09
 */
@Slf4j
public class NacosMetadataRule extends AbstractLoadBalancerRule {

    @Autowired
    private NacosDiscoveryProperties discoveryProperties;

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

    @Override
    public Server choose(Object key) {
        //--- 负载均衡规则：优先选择同集群下，符合metadata的实例,如果没有，就选择所有集群下，符合metadata的实例----

        //获取服务集群
        String clusterName=discoveryProperties.getClusterName();

        //获取当前服务的原元数据
        Map<String, String> metadata = discoveryProperties.getMetadata();
        //当前服务可以调用的服务版本
        String target_version=metadata.get("target-version");

        BaseLoadBalancer loadBalancer= (BaseLoadBalancer) this.getLoadBalancer();
        //想要请求的微服务名称
        String name =loadBalancer.getName();

        NamingService namingService=discoveryProperties.namingServiceInstance();

        try {
            //所有服务实例
            List<Instance> instances = namingService.selectInstances(name, true);

            List<Instance> metadataMatchInstances = instances;

            //配置了元数据
            if(StringUtils.isNotEmpty(target_version)){
                metadataMatchInstances = instances.stream()
                        .filter(instance -> Objects.equals(target_version, instance.getMetadata().get("version")))
                        .collect(Collectors.toList());
                if(CollectionUtils.isEmpty(metadataMatchInstances)){
                    log.warn("未找到元数据匹配的目标实例！请检查配置。targetVersion = {}, instance = {}", target_version, instances);
                    return null;
                }
            }

            List<Instance> clusterMetadataMatchInstances = metadataMatchInstances;

            //配置了服务集群
            if(StringUtils.isNotEmpty(clusterName)){
                clusterMetadataMatchInstances= metadataMatchInstances.stream().
                        filter(instance -> Objects.equals(clusterName,instance.getClusterName())).
                        collect(Collectors.toList());
                if(CollectionUtils.isEmpty(clusterMetadataMatchInstances)){
                    log.warn("发生跨集群调用。clusterName = {}, targetVersion = {}, clusterMetadataMatchInstances = {}", clusterName, target_version, clusterMetadataMatchInstances);
                    clusterMetadataMatchInstances=metadataMatchInstances;
                }
            }

            Instance instance=LoadBalancer.getHost(clusterMetadataMatchInstances);
            log.debug("选择的instance:port={},instanceId={}",instance.getPort(),instance.getInstanceId());
            return  new NacosServer(instance);

        } catch (NacosException e) {
            e.printStackTrace();
        }


        return null;

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
