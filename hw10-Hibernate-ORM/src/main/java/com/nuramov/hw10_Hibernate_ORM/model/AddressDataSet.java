package com.nuramov.hw10_Hibernate_ORM.model;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class AddressDataSet {

    //Тут куча вопросов
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Column(name = "street")
    private String street;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
