package com.haiphamcoder.elasticsearch.layer.infrastructure.elasticsearch;

import co.elastic.clients.elasticsearch.core.IndexResponse;

import java.io.IOException;

public interface IElasticsearchAdapter {

    <T> IndexResponse indexDocument(String indexName, String id, T document) throws IOException;

    void close() throws IOException;
}
