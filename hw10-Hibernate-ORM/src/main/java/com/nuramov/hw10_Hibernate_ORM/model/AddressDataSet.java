package com.nuramov.hw10_Hibernate_ORM.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * class AddressDataSet формирует таблицу "address" (адресов) и имеет отношение с классом User @OneToMany
 */
@Entity
@Table(name = "address")
public class AddressDataSet {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "street")
    private String street;

    // Атрибут mappedBy = "address" связывает классы User и AddressDataSet через поле address класса User
    // orphanRemoval = true — Если мы удалим улицу из БД — все связанные с ним юзеры также будут удалены
    // Атрибут cascade означает, что операция обновления должна распространяться на дочерние записи
    // FetchType.LAZY — ленивая выборка. Элементы коллекции будут выбираться из базы данных
    // только при обращении к какому-либо свойству коллекции
    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<User> users;

    public AddressDataSet() {
        users = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUsers(User user) {
        user.setAddress(this);
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
        user.setAddress(null);
    }
}
