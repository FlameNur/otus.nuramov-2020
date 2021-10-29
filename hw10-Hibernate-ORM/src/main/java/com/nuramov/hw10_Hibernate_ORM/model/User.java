package com.nuramov.hw10_Hibernate_ORM.model;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    private long id = 0;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    //@OneToOne
    class AddressDataSet {
        private String street;
    }

    //@OneToMany
    class PhoneDataSet {
        private String number;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
