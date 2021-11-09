package com.nuramov.hw10_Hibernate_ORM;

import com.nuramov.hw10_Hibernate_ORM.Service.UserService;
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

        User user3 = new User();
        user3.setAge(50);
        user3.setName("Huanita");

        PhoneDataSet phoneDataSet1 = new PhoneDataSet();
        phoneDataSet1.setNumber("111");
        phoneDataSet1.setUser(user1);

        PhoneDataSet phoneDataSet2 = new PhoneDataSet();
        phoneDataSet2.setNumber("222");
        phoneDataSet2.setUser(user2);

        PhoneDataSet phoneDataSet3 = new PhoneDataSet();
        phoneDataSet3.setNumber("555");
        phoneDataSet3.setUser(user3);

        AddressDataSet addressDataSet = new AddressDataSet();
        addressDataSet.setStreet("dadkakjda");

        addressDataSet.addUsers(user1);
        addressDataSet.addUsers(user2);
        addressDataSet.addUsers(user3);

        user1.setAddress(addressDataSet);
        user1.setPhone(phoneDataSet1);

        user2.setAddress(addressDataSet);
        user2.setPhone(phoneDataSet2);

        user3.setAddress(addressDataSet);
        user3.setPhone(phoneDataSet3);

        userService.saveUser(user1);
        user1.setName("New name");
        userService.updateUser(user1);

        userService.saveUser(user2);
        userService.saveUser(user3);

        User newUser1 = userService.findUser(1);
        System.out.println(newUser1);

        User newUser2 = userService.findUser(3);
        System.out.println(newUser2);

        User newUser3 = userService.findUser(5);
        System.out.println(newUser3);

        PhoneDataSet phoneUser3 = newUser3.getPhone();
        AddressDataSet addressUser3 = newUser3.getAddress();
        System.out.println(phoneUser3.getNumber());    // 555 все правильно
        System.out.println(addressUser3.getStreet());  // dadkakjda все правильно


        userService.deleteUser(user1);
        User deletedUser1 = userService.findUser(1);
        System.out.println(deletedUser1);

    }
}
