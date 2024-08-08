package com.example.demo;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("h2")
@DataJpaTest
@Import(H2TestsRunner.class)
class PersonRepositoryTests {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setup(){
        if (personRepository == null || entityManager == null){
            fail("Bean was not injected");
        }
    }

    @Test
    @DisplayName("This method will test the four implemented methods " +
            "by person repository interface")
    @Transactional
    void testRepositoryMethods(){
        var john = personRepository.findByName("John");
        assertThat(john.isPresent()).isTrue();
        assertThat(john.get().getWeight()).isEqualTo(75);

        var peopleTallerThan165 = personRepository.findTaller();
        assertThat(peopleTallerThan165.size()).isOne();
        assertThat(peopleTallerThan165.getFirst().getName()).isEqualTo("John");

        var peopleWithMoreThan70Kgs = personRepository.findAllWithWeightBetterThan(70);
        assertThat(peopleWithMoreThan70Kgs.size()).isEqualTo(2);

        var anne = personRepository.findByName("Anne");
        assertThat(anne.isPresent()).isTrue();
        personRepository.updateName("Anna", anne.get().getId());
        entityManager.clear();
        var anne2 = personRepository.findByName("Anne");
        assertThat(anne2.isEmpty()).isTrue();
        var anna = personRepository.findByName("Anna");
        assertThat(anna.isPresent()).isTrue();
        assertThat(anna.get().getName()).isEqualTo("Anna");
        assertThat(anna.get().getHeight()).isEqualTo(162);
    }
}
