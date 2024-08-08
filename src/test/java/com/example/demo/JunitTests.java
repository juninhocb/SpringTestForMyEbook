package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import org.junit.jupiter.api.condition.EnabledIf;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class JunitTests {
    PersonService personService;
    @BeforeEach
    void setup(){
        personService = new PersonService(null);
    }
    @Test
    @DisplayName("Verify if method is calculating correctly")
    void testCalculusImcOne(){
        var person = new Person(null, null, 170, 75, null);
        var response = personService.calculateImc(person);
        assertEquals(BigDecimal.valueOf(25.95), response);
    }
    @Test
    @DisplayName("Ensure that person is not obese")
    void testCalculusImcTwo(){
        var obeseIndex = BigDecimal.valueOf(30);
        var person = new Person(null, null, 170, 75, null);
        var response = personService.calculateImc(person);
        assertTrue(response.compareTo(obeseIndex) < 0, "Person should not be considered obese");
    }
    @Test
    @Disabled("Because is not used, since ...")
    void notUsedTest(){
       //...
    }
    @Test
    @EnabledIf("isServiceNull")
    @DisplayName("This test is disabled if the service is not null")
    @Disabled("Was enabled for example...")
    void disableIfServiceIsNotNull(){
        fail("This test should be disabled if the service is not null");
    }
    boolean isServiceNull() {
        return personService == null;
    }
}
