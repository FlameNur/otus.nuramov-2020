package com.nuramov.hw10_Hibernate_ORM.model;

import javax.persistence.*;

/**
 * class AddressDataSet формирует таблицу "address" (адресов) и имеет отношение с классом User @OneToMany
 */
@Entity
@Table(name = "address")
public class AddressDataSet {

    // Генерация id стоит по дефолту
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "street")
    private String street;

    // Атрибут mappedBy = "address" связывает классы User и AddressDataSet через поле address класса User
    @OneToOne(mappedBy = "address")
    User user;

    public long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
