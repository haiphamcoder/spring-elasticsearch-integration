package com.haiphamcoder.elasticsearch;

import com.haiphamcoder.elasticsearch.layer.application.domain.dto.Person;
import com.haiphamcoder.elasticsearch.layer.application.repository.IPersonDataEsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {
    @Autowired
    IPersonDataEsRepository personDataEsRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void testSavePerson() {
        personDataEsRepository.save(new Person(1, "Alice", 1990, "123 Main St", "example@email.com", "123456"));
    }

}
