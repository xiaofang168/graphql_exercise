package com.fangj.graphql;

import com.fangj.graphql.bean.Address;
import com.fangj.graphql.bean.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author fangjie
 * @date Created in 下午3:14 18/4/19.
 */
public class DataContainer {

    public static List<Person> personList = new ArrayList<>();

    static {
        Person person = new Person();
        person.setUserName("张三");
        person.setAge(30);
        person.setSex(1);
        Address address = new Address();
        address.setCity("北京市");
        address.setStreet("望京");

        Person person2 = new Person();
        person2.setUserName("李四");
        person2.setAge(32);
        person2.setSex(0);
        Address address2 = new Address();
        address2.setCity("天津市");
        address2.setStreet("河西");

        person.setAddresses(Arrays.asList(address));
        person2.setAddresses(Arrays.asList(address2));

        personList.add(person);
        personList.add(person2);
    }


}
