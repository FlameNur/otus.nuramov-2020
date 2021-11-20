package com.nuramov.hw10_Hibernate_ORM.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * class PhoneDataSe формирует таблицу "phone" (телефонов) и имеет отношение с классом User @OneToOne
 */
@Entity
@Table(name = "phone")
public class PhoneDataSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "number")
    private String number;

    // Атрибут mappedBy = "phone" связывает классы User и Phone через поле phone класса User
    // {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}
    // orphanRemoval=true
    @OneToMany(mappedBy = "phone", fetch = FetchType.EAGER)
    private List<User> users;

    public PhoneDataSet() {
        users = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        user.setPhone(this);
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
