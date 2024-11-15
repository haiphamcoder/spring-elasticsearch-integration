package com.haiphamcoder.elasticsearch.layer.infrastructure.elasticsearch;

import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;

import java.io.IOException;
import java.util.Map;

public interface IElasticsearchAdapter {

    <T> IndexResponse indexDocument(String indexName, String id, T document) throws IOException;

    <T> BulkResponse bulkIndexDocuments(String indexName, Map<String, T> documents) throws IOException;

    <T> GetResponse<T> getDocument(String indexName, String id, Class<T> documentClass) throws IOException;

    void close() throws IOException;
}
