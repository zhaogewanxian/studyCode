package com.wanxian.springcloud.loadbalance;

import org.apache.commons.lang.StringUtils;
import org.omg.PortableInterceptor.ClientRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

public class LoadBalanceRequestInterceptor implements ClientHttpRequestInterceptor {
    private volatile Map<String, Set<String>> targetUrlsCache = new HashMap<>();
    @Autowired
    private DiscoveryClient discoveryClient;

    @Scheduled(fixedDelay = 10 * 1000)
    public void updateTargetUrls() {
        Map<String, Set<String>> newTargetUrlsCache = new HashMap<>();
        discoveryClient.getServices().forEach(serviceName -> {
            List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceName);
            Set<String> newTargetUrl = serviceInstances.stream().map(s ->
                    s.isSecure() ? "https://" + s.getHost() + ":" + s.getPort() :
                            "http://" + s.getHost() + ":" + s.getPort()
            ).collect(Collectors.toSet());
            newTargetUrlsCache.put(serviceName, newTargetUrl); //本地缓存，防止注册中心挂
        });
        //swap
        this.targetUrlsCache = newTargetUrlsCache;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        //URI:/${APP-name}/uri
        URI requestUri = request.getURI();
        String path = requestUri.getPath();
        String[] paths = StringUtils.split(path.substring(1), "/");
        String serviceName = paths[0];
        String uri = paths[1];
        List<String> urls = new ArrayList<>(targetUrlsCache.get(serviceName));
        int size = urls.size();
        int index = new Random().nextInt(size);
        String targetUrl = urls.get(index);
        //拼接最终请求url
        String actuaUrl = targetUrl + "/" + uri + "?" + requestUri.getQuery();
        //执行请求
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<InputStream> responseEntity = restTemplate.getForEntity(actuaUrl, InputStream.class);
        return null;
    }

    private static class SimpleClientHttpResponse implements ClientHttpResponse {

        @Override
        public HttpStatus getStatusCode() throws IOException {
            return null;
        }

        @Override
        public int getRawStatusCode() throws IOException {
            return 0;
        }

        @Override
        public String getStatusText() throws IOException {
            return null;
        }

        @Override
        public void close() {

        }

        @Override
        public InputStream getBody() throws IOException {
            return null;
        }

        @Override
        public HttpHeaders getHeaders() {
            return null;
        }
    }
}
