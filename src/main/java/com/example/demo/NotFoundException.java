package com.example.demo;

class NotFoundException extends RuntimeException {
    NotFoundException(Integer id) {
        super("Person with id = " + id + " was not found");
    }
}
