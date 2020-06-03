package com.some.es.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

/**
 * @description:
 * @vsersion: V1.0
 * @author: qzq
 * @date: 2020-06-03 09:53
 */
@Configuration
@Slf4j
@Data
public class EsConfig {

    @Value("${elasticsearch.host}")
    private String hosts = ""; // 集群地址
    @Value("${elasticsearch.port:9200}")
    private Integer port ; // 使用的端口号
    @Value("${elasticsearch.schema:http}")
    private String schema ; // 使用的协议
    @Value("${elasticsearch.searchTimeout:10000}")
    private Integer searchTimeout ; //搜索超时时间

    @Value("${elasticsearch.maxConnTotal:20}")
    private Integer maxConnTotal ; //最大连接数
    @Value("${elasticsearch.maxConnPerRoute:10}")
    private Integer maxConnPerRoute ; //最大同路由 -针对一个域名同时间正在使用的最多的连接数

    @Value("${elasticsearch.numberOfShards:5}")
    private Integer numberOfShards ;  //数据分片数，默认为5
    @Value("${elasticsearch.numberOfReplicas:1}")
    private Integer numberOfReplicas ; //数据备份数

    @Value("${elasticsearch.userName:}")
    private String userName ;
    @Value("${elasticsearch.password:}")
    private String password ;
    @Value("${elasticsearch.connectTimeOut:0}")
    private Integer connectTimeOut;
    @Value("${elasticsearch.socketTimeout:0}")
    private Integer socketTimeout;
    @Value("${elasticsearch.connectionRequestTimeout:0}")
    private Integer connectionRequestTimeout;

    private RestHighLevelClient restClient;
    @Bean
    public RestHighLevelClient restHighLevelClient()
    {
        try {
            ArrayList<HttpHost> hostList = new ArrayList<>();
            String[] hostStrs = hosts.split(",");
            for (String host : hostStrs) {
                hostList.add(new HttpHost(host, port, schema));
            }

            RestClientBuilder builder = RestClient.builder(hostList.toArray(new HttpHost[0]))
                    .setFailureListener(new RestClient.FailureListener() { // 连接失败策略
                    }).setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
                        @Override
                        public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                            if (connectTimeOut != null && connectTimeOut > 0){
                                requestConfigBuilder.setConnectTimeout(connectTimeOut);
                            }
                            if (socketTimeout != null && socketTimeout> 0){
                                requestConfigBuilder.setSocketTimeout(socketTimeout);
                            }
                            if (connectionRequestTimeout != null && connectionRequestTimeout > 0){
                                requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeout);
                            }
                            return requestConfigBuilder;
                        }
                    })
                    .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                        @Override
                        public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {

                            if (maxConnTotal != null && maxConnTotal> 0){
                                httpClientBuilder.setMaxConnTotal(maxConnTotal);
                            }

                            if (maxConnPerRoute != null && maxConnPerRoute > 0){
                                httpClientBuilder.setMaxConnTotal(maxConnPerRoute);
                            }

                            if (userName == null || password == null){
                                return httpClientBuilder;
                            }else{
                                CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                                credentialsProvider.setCredentials(AuthScope.ANY,
                                        new UsernamePasswordCredentials(userName, password));
                                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                            }
                        }
                    });

           return new RestHighLevelClient(builder);
        }catch (Exception e) {
            log.error("es fail: {}",  e.getMessage(),e);
            return null;
        }
    }
}
