package com.nuramov.hw10_Hibernate_ORM.model;

import javax.persistence.*;

/**
 * class AddressDataSet формирует таблицу "address" (адресов) и имеет отношение с классом User @OneToOne
 */
@Entity
@Table(name = "address")
public class AddressDataSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
