package com.haiphamcoder.elasticsearch.config.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElasticConfigProperties {
    private List<String> hosts;
    private Integer port;
    private String schema;
    private boolean auth;
    private String username;
    private String password;
    private int maxConnPerRoute;
    private int maxConnTotal;
    private int connectTimeout;
    private int socketTimeout;
}
