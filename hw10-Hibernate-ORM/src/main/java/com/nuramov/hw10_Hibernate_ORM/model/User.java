package com.nuramov.hw10_Hibernate_ORM.model;

import javax.persistence.*;

/**
 *
 */
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

    // @JoinColumn(name = "address_id") - создает столбец "address_id" в таблице "user",
    // чтобы связать ее с таблицей "address"
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private AddressDataSet address;


    // @OneToOne - Атрибут cascade означает, что операция обновления должна распространяться на дочерние записи,
    // т.е. удалив user'a мы удалим и phone
    // @JoinColumn(name = "phone_id") - создает столбец "phone_id" в таблице "user",
    // чтобы связать ее с таблицей "phone"
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "phone_id")
    private PhoneDataSet phone;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public AddressDataSet getAddress() {
        return address;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    public PhoneDataSet getPhone() {
        return phone;
    }

    public void setPhone(PhoneDataSet phone) {
        this.phone = phone;
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
