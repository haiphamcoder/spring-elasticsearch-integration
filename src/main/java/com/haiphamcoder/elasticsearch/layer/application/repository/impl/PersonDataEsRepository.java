package com.haiphamcoder.elasticsearch.layer.application.repository.impl;

import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.haiphamcoder.elasticsearch.layer.application.domain.dto.Person;
import com.haiphamcoder.elasticsearch.layer.application.repository.IPersonDataEsRepository;
import com.haiphamcoder.elasticsearch.layer.infrastructure.elasticsearch.impl.ElasticsearchAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
@Slf4j
public class PersonDataEsRepository implements IPersonDataEsRepository {
    private final ElasticsearchAdapter elasticsearchAdapter;
    private static final String INDEX_NAME = "person";

    public PersonDataEsRepository(@Qualifier("mainRestClientTransport") RestClientTransport transport) {
        this.elasticsearchAdapter = new ElasticsearchAdapter(transport);
    }

    public boolean save(Person person) {
        try {
            IndexResponse response = elasticsearchAdapter.indexDocument(INDEX_NAME, String.valueOf(person.getId()), person);
            return true;
        } catch (IOException e) {
            log.error("Failed to save person with id {}", person.getId(), e);
        }
        return false;
    }
}
