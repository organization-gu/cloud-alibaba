# spring cloud alibaba  
## 简介
核心技术采用Fegin、Ribbon、JWT
Token、Mybatis、TkMybatis、SpringBoot、springCloudStream、Nacos、Sentinel、
RocketMQ、zipkin、elasticsearch、Grafana等主要框架和中间件。
## 项目环境版本
* SpringBoot版本 2.1.5.RELEASE
* SpringCloud版本 Greenwich.SR2
* SpringCloudAlibaba版本 2.1.0.RELEASE
* JDK版本 1.8+
* maven版本 3.5+ 
## 项目代码地址
https://github.com/organization-gu/cloud-alibaba.git 

## 内部服务组件地址
* nacos: http://192.168.44.78:8848/nacos 账号/密码： nacos/nacos 
* sentinel—dashboard http://192.168.44.78:9999 账号/密码： admin/123456
* rocketMQ-dashboard http://192.168.44.78:17890 
* elasticsarch http://192.168.44.78:9200
* Grafana http://192.168.44.78:3000 账号/密码： admin/123456

## 功能点介绍:

### 服务注册与调用 
    基于nacos来实现的服务注册与调用，在Spring Cloud中使用
    Feign 我们可以做到使用HTTP请求远程服务时能与调用本地方法一样的编码体验，
    开发者完全感知不到这是远程方法，更感知不到这是个HTTP请求
### 服务鉴权
    通过JWT的方式来加强服务之间调度的权限验证，保证内部服务的安全性
### 负载均衡
    将服务保留的rest请求由网关控制，SpringCloud系列的gateway整合了rebbion，
    可以帮我们进行正常的网关负载均衡。其中扩展可以自定义负载均衡策略来满足
    正常的业务需求。
### 熔断机制
    因为采取了服务的分布，为了避免服务之间的调用“雪崩”，采用了sentinel的作为
    熔断器和限流，sentinel可以提供多种限流和熔断策略，避免了服务之间的“雪崩”。
### 监控
    利用Spring Boot Admin 来监控各个独立Service的运行状态；
    利用turbine来实时查看接口的运行状态和调用频率(待实现)；
    通过Zipkin来查看各个服务之间的调用链等。
    利用Grafana来监控服务器状态（待完善）
### 代码生成器
    基于Mybatis-generator插件和TKMybatis自动生成关于持久层的基础增、删、
    改、查。 通过配置数据库字段的注释，自动生成枚举类、数据字典注解等。
### 分布式事务
    现阶段利用rocketMQ和SpringCloudStream来实现服务间接口调用数据一致性解决
    方案.
    下阶段可以利用阿里的分布式事务中间件：seata，以 高效 并且对业务 0侵入 
    的方式，解决 微服务 场景下面临的分布式事务问题。
   
