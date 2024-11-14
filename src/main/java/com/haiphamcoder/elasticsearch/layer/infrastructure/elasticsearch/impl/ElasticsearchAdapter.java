package com.haiphamcoder.elasticsearch.layer.infrastructure.elasticsearch.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.haiphamcoder.elasticsearch.layer.infrastructure.elasticsearch.IElasticsearchAdapter;

import java.io.IOException;

public class ElasticsearchAdapter implements IElasticsearchAdapter {
    private final ElasticsearchClient client;

    public ElasticsearchAdapter(ElasticsearchTransport transport) {
        this.client = new ElasticsearchClient(transport);
    }

    @Override
    public <T> IndexResponse indexDocument(String indexName, String id, T document) throws IOException {
        return client.index(new IndexRequest.Builder<T>()
                .index(indexName)
                .id(id)
                .document(document)
                .build());
    }

    @Override
    public void close() throws IOException {
        ((RestClientTransport) client._transport()).restClient().close();
    }
}
