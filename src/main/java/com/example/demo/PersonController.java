package com.example.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/person")
@RequiredArgsConstructor
@Slf4j
class PersonController {
    private final PersonService personService;

    @GetMapping
    ResponseEntity<List<Person>> findAll() {
        return ResponseEntity.ok().body(personService.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<Person> findById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(personService.findById(id));
    }

    @PostMapping
    ResponseEntity<Void> create(@RequestBody Person person,
                                UriComponentsBuilder ucb) {
        var createdResource = personService.insert(person);
        var resourcePath = ucb
                .path("/api/person/{id}")
                .buildAndExpand(createdResource.getId())
                .toUri();
        return ResponseEntity.created(resourcePath).build();
    }

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<Void> handleNotFoundException(NotFoundException e) {
        log.warn("not found exception was raised " + e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
