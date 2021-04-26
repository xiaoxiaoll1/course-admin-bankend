package com.sino.thirdParty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 提供第三方服务接口, 供其他微服务远程调用
 * @author xiaozj
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class ThirdPartyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThirdPartyApplication.class, args);
    }
}
