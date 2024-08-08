package com.example.demo;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("mysql-tests")
@SpringBootTest
class PersonServiceTests {
    @Autowired
    private PersonService personService;
    @BeforeEach
    void setup(){
        if (personService == null){
            fail("Bean was not injected");
        }
    }
    @Test
    @DisplayName("Test some services together")
    @Transactional
    void testSomeServices(){
        var sizeBeforeAll = personService.findAll().size();
        List<Person> savedPeople = new ArrayList<>();
        PersonUtils.getPeople().forEach(person -> {
            var saved = personService.insert(person);
            savedPeople.add(saved);
        });
        var sizeAfterInserting = personService.findAll().size();
        assertThat(sizeAfterInserting).isEqualTo(sizeBeforeAll+PersonUtils.getPeople().size());
        var personSaved = savedPeople.getFirst();
        var personFromService = personService.findById(personSaved.getId());
        assertThat(personFromService).isNotNull();
        assertThat(personFromService.getId()).isNotNull();
    }

    @Test
    @DisplayName("When a person was not found, should throw our custom exception")
    void testWhenAPersonWasNotFound(){
        assertThatThrownBy(() -> personService
                .findById(9999999))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Person with id = 9999999 was not found");
    }


}

