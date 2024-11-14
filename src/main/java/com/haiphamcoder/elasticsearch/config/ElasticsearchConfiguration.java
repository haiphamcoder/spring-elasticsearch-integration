package com.haiphamcoder.elasticsearch.config;

import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.haiphamcoder.elasticsearch.config.properties.ElasticConfigProperties;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ElasticsearchConfiguration {
    @Bean("mainEsConfigProperties")
    @ConfigurationProperties(prefix = "elasticsearch")
    public ElasticConfigProperties elasticConfigProperties() {
        return new ElasticConfigProperties();
    }

    @Bean("mainRestClient")
    public RestClient restClient(@Qualifier("mainEsConfigProperties") ElasticConfigProperties esConfigProperties) {
        return initRestClient(esConfigProperties);
    }

    @Bean("mainRestClientTransport")
    public RestClientTransport restClientTransport(@Qualifier("mainRestClient") RestClient restClient) {
        return initRestClientTransport(restClient);
    }

    private RestClient initRestClient(ElasticConfigProperties esConfigProperties) {
        HttpHost[] httpHosts = new HttpHost[esConfigProperties.getHosts().size()];
        for (int i = 0; i < esConfigProperties.getHosts().size(); i++) {
            httpHosts[i] = new HttpHost(esConfigProperties.getHosts().get(i), esConfigProperties.getPort(), esConfigProperties.getSchema());
        }
        RestClientBuilder restClientBuilder = RestClient.builder(httpHosts);

        // Set the configuration for the HTTP client
        restClientBuilder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnPerRoute(esConfigProperties.getMaxConnPerRoute())
                    .setMaxConnTotal(esConfigProperties.getMaxConnTotal())
                    .setKeepAliveStrategy(((httpResponse, httpContext) -> {
                        HeaderElementIterator headerElementIterator = new BasicHeaderElementIterator(
                                httpResponse.headerIterator(HTTP.CONN_KEEP_ALIVE)
                        );
                        while (headerElementIterator.hasNext()) {
                            HeaderElement headerElement = headerElementIterator.nextElement();
                            String name = headerElement.getName();
                            String value = headerElement.getValue();
                            if (value != null && name.equalsIgnoreCase("timeout")) {
                                return Long.parseLong(value) * 1000;
                            }
                        }
                        return Duration.ofSeconds(30).toMillis();
                    }));

            if (esConfigProperties.isAuth()) {
                BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                credentialsProvider.setCredentials(
                        AuthScope.ANY,
                        new UsernamePasswordCredentials(
                                esConfigProperties.getUsername(),
                                esConfigProperties.getPassword()
                        )
                );
                httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
            return httpClientBuilder;
        });

        // Set the configuration for the request
        restClientBuilder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(esConfigProperties.getConnectTimeout());
            requestConfigBuilder.setSocketTimeout(esConfigProperties.getSocketTimeout());
            return requestConfigBuilder;
        });

        return restClientBuilder.build();
    }

    private RestClientTransport initRestClientTransport(RestClient restClient) {
        return new RestClientTransport(restClient, new JacksonJsonpMapper());
    }
}
