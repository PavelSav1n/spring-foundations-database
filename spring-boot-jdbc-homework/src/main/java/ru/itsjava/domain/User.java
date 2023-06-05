package ru.itsjava.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor // for UserDaoImpl.findUserById
public class User {
    // All field can be changed in future
    private long id; // will be filled from H2 DB
    private String name;
    private int age;
    private Pet pet;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public User(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public User(String name, int age, Pet pet) {
        this.name = name;
        this.age = age;
        this.pet = pet;
    }
}
