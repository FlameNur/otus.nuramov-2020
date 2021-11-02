package com.nuramov.hw10_Hibernate_ORM.model;

import javax.persistence.*;

@Entity
@Table(name = "phone")
public class PhoneDataSet {

    //Тут куча вопросов
    @OneToOne(cascade = CascadeType.ALL)
    @Column(name = "namber")
    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
