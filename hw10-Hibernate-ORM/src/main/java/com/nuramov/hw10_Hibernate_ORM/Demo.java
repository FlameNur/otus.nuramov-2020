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

        User user2 = new User();
        user2.setAge(20);
        user2.setName("Sally");

        PhoneDataSet phoneDataSet = new PhoneDataSet();
        phoneDataSet.setNumber("111");
        phoneDataSet.setUser(user1);

        PhoneDataSet phoneDataSet2 = new PhoneDataSet();
        phoneDataSet2.setNumber("222");
        phoneDataSet2.setUser(user2);

        AddressDataSet addressDataSet = new AddressDataSet();
        addressDataSet.setStreet("dadkakjda");

        addressDataSet.addUsers(user1);
        addressDataSet.addUsers(user2);

        user1.setAddress(addressDataSet);
        user1.setPhone(phoneDataSet);

        user2.setAddress(addressDataSet);
        user2.setPhone(phoneDataSet2);

        userService.saveUser(user1);
        user1.setName("New name");
        userService.updateUser(user1);

        userService.saveUser(user2);

        User newUser = userService.findUser(1);
        System.out.println(newUser);

        User newUser2 = userService.findUser(2);
        System.out.println(newUser2);

        userService.deleteUser(user1);
        User newUser3 = userService.findUser(1);
        System.out.println(newUser3);

    }
}
