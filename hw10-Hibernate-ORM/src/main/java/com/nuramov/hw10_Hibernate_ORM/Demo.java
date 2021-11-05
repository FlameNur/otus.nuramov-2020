package com.nuramov.hw10_Hibernate_ORM;

import com.nuramov.hw10_Hibernate_ORM.Servise.UserService;
import com.nuramov.hw10_Hibernate_ORM.model.AddressDataSet;
import com.nuramov.hw10_Hibernate_ORM.model.PhoneDataSet;
import com.nuramov.hw10_Hibernate_ORM.model.User;

public class Demo {
    public static void main(String[] args) {
        UserService userService = new UserService();

        User user1 = new User();
        user1.setAge(10);
        user1.setName("Bill");

        PhoneDataSet phoneDataSet = new PhoneDataSet();
        phoneDataSet.setNumber("111");
        phoneDataSet.setUser(user1);

        AddressDataSet addressDataSet = new AddressDataSet();
        addressDataSet.setStreet("dadkakjda");
        addressDataSet.addUsers(user1);

        user1.setAddress(addressDataSet);
        user1.setPhone(phoneDataSet);

        userService.saveUser(user1);

    }
}
