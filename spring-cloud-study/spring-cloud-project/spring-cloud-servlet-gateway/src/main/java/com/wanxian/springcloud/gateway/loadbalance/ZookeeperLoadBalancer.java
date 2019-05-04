package com.wanxian.springcloud.gateway.loadbalance;

import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public class ZookeeperLoadBalancer extends BaseLoadBalancer {
    private final DiscoveryClient discoveryClient;

    public ZookeeperLoadBalancer(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
        //init
        updateServers();
    }

    /**
     * 更新所有服务器
     */
    @Scheduled(fixedRate = 5000)
    public void updateServers() {
        discoveryClient.getServices().forEach(serverName -> {
            List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serverName);
            serviceInstances.forEach(serviceInstance -> {
                Server server = new Server(serviceInstance.isSecure() ? "https://" : "http://",
                        serviceInstance.getHost(), serviceInstance.getPort());
                addServer(server);
            });
        });
    }


}
