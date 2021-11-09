package com.nuramov.hw10_Hibernate_ORM.model;

import javax.persistence.*;

/**
 * class PhoneDataSe формирует таблицу "phone" (телефонов) и имеет отношение с классом User @OneToOne
 */
@Entity
@Table(name = "phone")
public class PhoneDataSet {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "number")
    private String number;

    // Атрибут mappedBy = "phone" связывает классы User и Phone через поле phone класса User
    @OneToOne(mappedBy = "phone", cascade = CascadeType.ALL)
    private User user;

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getNumber() {
        return number;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
