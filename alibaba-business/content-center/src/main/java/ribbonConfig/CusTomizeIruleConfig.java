package ribbonConfig;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import com.lanswon.contentcenter.config.irule.NacosMetadataRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**  ribbon 负载均衡配置，包目录必须和容器启动包平级
 * @Author GU-YW
 * @Date 2019/10/11 15:48
 */
@Configuration
@RibbonClient(name = "user-center",configuration = CusTomizeIruleConfig.class)
public class CusTomizeIruleConfig {

    @Bean
    public IRule myIrule(){
        return new NacosMetadataRule();
    }

    @Bean
    @LoadBalanced
    @SentinelRestTemplate
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
