package com.haiphamcoder.elasticsearch.layer.application.repository;

import com.haiphamcoder.elasticsearch.layer.application.domain.dto.Person;

public interface IPersonDataEsRepository {
    boolean save(Person person);
}
