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

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
