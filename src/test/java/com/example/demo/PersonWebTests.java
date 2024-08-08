package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("mysql-tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonWebTests {
    @Autowired
    private TestRestTemplate restTemplate;
    private static final String RESOURCE_PATH = "/api/person";
    @Test
    @DisplayName("Testing all endpoints of person resource")
    @Transactional
    void testAllEndpoints(){
        var person = PersonUtils.getPeople().getFirst();
        var httpEntity = new HttpEntity<>(person);
        ResponseEntity<Void> getGeneratedId = restTemplate
                .exchange(RESOURCE_PATH,
                        HttpMethod.POST,
                        httpEntity,
                        Void.class);
        assertThat(getGeneratedId.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        var location = getGeneratedId.getHeaders().getLocation();
        assertThat(location).isNotNull();
        ResponseEntity<Person> getPerson = restTemplate
                .exchange(location,
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        Person.class);
        assertThat(getPerson.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getPerson.getBody()).isNotNull();
        assertThat(getPerson.getBody().getImc()).isNull();
        assertThat(getPerson.getBody().getName()).isEqualTo(person.getName());
        ResponseEntity<List<Person>> getPeople = restTemplate
                .exchange(RESOURCE_PATH,
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        new ParameterizedTypeReference<List<Person>>() {});
        assertThat(getPeople.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getPeople.getBody()).isNotNull();
        assertThat(getPeople.getBody().size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("When not found, should return 404 as status code")
    void testIfNotFoundCodeWasTheStatusCode(){
        ResponseEntity<Person> getPerson = restTemplate
                .exchange(RESOURCE_PATH+"/9999999",
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        Person.class);
        assertThat(getPerson.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(getPerson.getBody()).isNull();
    }

}
