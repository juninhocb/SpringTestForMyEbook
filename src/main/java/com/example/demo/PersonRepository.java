package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

interface PersonRepository extends JpaRepository<Person, Integer> {

    @Query
    Optional<Person> findByName(String name);

    @Query("SELECT p FROM Person p WHERE height > 165")
    List<Person> findTaller();

    @Query(value = "SELECT * FROM `person` WHERE `weight` > :criteria", nativeQuery = true)
    List<Person> findAllWithWeightBetterThan(@Param(value = "criteria") Integer weight);

    @Modifying
    @Query("UPDATE Person p SET p.name = :newName WHERE p.id = :id")
    void updateName(String newName, Integer id);

}
