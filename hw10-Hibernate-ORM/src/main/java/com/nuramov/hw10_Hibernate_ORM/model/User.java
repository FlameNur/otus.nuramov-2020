package com.nuramov.hw10_Hibernate_ORM.model;

import javax.persistence.*;

/**
 * class User формирует таблицу "user" (пользователей) и имеет отношения с классами
 * AddressDataSet - @OneToOne
 * PhoneDataSet - @ManyToOne
 */
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = 0;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    // Атрибут cascade = CascadeType.ALL означает, что операция обновления должна распространяться на дочерние записи,
    // т.е. удалив user'a, мы удаляем и address
    // @JoinColumn(name = "address_id") - создает столбец "address_id" в таблице "user",
    // чтобы связать ее с таблицей "address"
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressDataSet address;

    // Атрибут cascade означает, что операция обновления должна распространяться на дочерние записи,
    // т.е. удалив user'a мы удалим и phone
    // @JoinColumn(name = "phone_id") - создает столбец "phone_id" в таблице "user",
    // чтобы связать ее с таблицей "phone"
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "phone_id")
    private PhoneDataSet phone;

    // Должен быть дефолтный конструктор для @Entity класса
    public User() {

    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public AddressDataSet getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    public PhoneDataSet getPhone() {
        return phone;
    }

    public void setPhone(PhoneDataSet phone) {
        this.phone = phone;
        if (this.phone != null) {
            this.phone.addUser(this);
        }
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
