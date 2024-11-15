package com.haiphamcoder.elasticsearch.layer.infrastructure.elasticsearch.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.haiphamcoder.elasticsearch.layer.infrastructure.elasticsearch.IElasticsearchAdapter;

import java.io.IOException;
import java.util.Map;

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
    public <T> BulkResponse bulkIndexDocuments(String indexName, Map<String, T> documents) throws IOException {
        return null;
    }

    @Override
    public <T> GetResponse<T> getDocument(String indexName, String id, Class<T> documentClass) throws IOException {
        return client.get(new GetRequest.Builder()
                .index(indexName)
                .id(id)
                .build(), documentClass);
    }

    @Override
    public void close() throws IOException {
        ((RestClientTransport) client._transport()).restClient().close();
    }
}
