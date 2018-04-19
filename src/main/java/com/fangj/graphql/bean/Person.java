package com.fangj.graphql.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author fangjie
 * @date Created in 上午10:18 18/4/19.
 */
@Data
public class Person implements Serializable {
    private Integer age;
    private Integer sex;
    private String userName;
    private List<Address> addresses;
}
