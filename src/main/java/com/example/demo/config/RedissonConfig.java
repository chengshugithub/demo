package com.example.demo.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {
    @Value(value = "${spring.redisson.address}")
    private String redissonAddress;

    @Value(value = "${spring.redisson.password}")
    private String redissonPassword;

    @Value(value = "${spring.redisson.scanInterval}")
    private int redissonScanInterval;

    @Value(value = "${spring.redisson.retryAttempts}")
    private int redissonRetryAttempts;

    @Value(value = "${spring.redisson.timeout}")
    private int redissonTimeout;

    @Bean
    public RedissonClient getRedisClient() {
        String[] nodes = redissonAddress.split(",");
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = "redis://" + nodes[i];
        }

        Config config = new Config();
//        config.useClusterServers() //这是用的集群server
//                .setScanInterval(redissonScanInterval) //设置集群状态扫描时间
//                .addNodeAddress(nodes).setRetryAttempts(redissonRetryAttempts).setTimeout(redissonTimeout);
//        if (redissonPassword!=null&&!redissonPassword.equals("")) {
//            config.useClusterServers().setPassword(redissonPassword);
//        }



        //连接单机的方式（成功）
        config.useSingleServer().setAddress(nodes[0]).setPassword(redissonPassword);
        return Redisson.create(config);
    }

}
