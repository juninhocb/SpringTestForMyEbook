package com.example.demo;

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
class PersonUtils {
    List<Person> getPeople(){
        Person p1 = new Person(null, "John", 170, 75, null);
        Person p2 = new Person(null, "Anne", 162, 55, null);
        Person p3 = new Person(null, "Brock", 165, 72, null);
        return List.of(p1,p2,p3);
    }
}
