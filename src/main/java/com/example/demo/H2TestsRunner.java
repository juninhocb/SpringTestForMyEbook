package com.example.demo;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("h2")
class H2TestsRunner {
    @Bean
    ApplicationRunner applicationRunner(PersonRepository personRepository){
        return args ->{
            personRepository.saveAll(PersonUtils.getPeople());
        };
    }
}
