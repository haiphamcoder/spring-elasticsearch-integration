package com.haiphamcoder.elasticsearch.layer.application.repository.impl;

import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch.core.GetResponse;
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

    @Override
    public Person getPersonById(int id) {
        try {
            GetResponse<Person> response = elasticsearchAdapter.getDocument(INDEX_NAME, String.valueOf(id), Person.class);
            return response.source();
        } catch (IOException e) {
            log.error("Failed to get person with id {}", id, e);
        }
        return null;
    }

    @Override
    public boolean save(Person person) {
        try {
            IndexResponse response = elasticsearchAdapter.indexDocument(INDEX_NAME, String.valueOf(person.getId()), person);
            return response.result().equals(Result.Created) || response.result().equals(Result.Updated);
        } catch (IOException e) {
            log.error("Failed to save person with id {}", person.getId(), e);
        }
        return false;
    }
}
