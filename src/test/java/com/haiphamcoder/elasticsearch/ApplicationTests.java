package com.haiphamcoder.elasticsearch;

import com.haiphamcoder.elasticsearch.layer.application.domain.dto.Person;
import com.haiphamcoder.elasticsearch.layer.application.repository.IPersonDataEsRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ApplicationTests {
    @Autowired
    IPersonDataEsRepository personDataEsRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void testSavePerson() {
        boolean status = personDataEsRepository.save(new Person(1, "Alice", 1990, "123 Main St", "example@email.com", "123456"));
        log.info("Person saved: {}", status);

        Person person = personDataEsRepository.getPersonById(2);
        log.info("Person retrieved: {}", person);
    }
}
