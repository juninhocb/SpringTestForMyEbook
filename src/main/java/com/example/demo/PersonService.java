package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
class PersonService {
    private final PersonRepository personRepository;

    List<Person> findAll() {
        return personRepository.findAll();
    }

    Person findById(Integer id) {
        var personOpt = personRepository.findById(id);
        return personOpt.orElseThrow(() -> new NotFoundException(id));
    }

    Person insert(Person person) {
        return personRepository.save(person);
    }

    BigDecimal calculateImc(Person person) {
        double heightInMeter = person.getHeight() / 100.00;
        var result = person.getWeight() / (Math.pow(heightInMeter, 2));
        return BigDecimal
                .valueOf(result)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
