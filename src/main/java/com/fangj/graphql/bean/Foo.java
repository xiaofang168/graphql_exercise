package com.fangj.graphql.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @author fangjie
 * @date Created in 下午7:24 18/4/18.
 */
@Data
public class Foo implements Serializable {
    private Long id;
    private String name;
}
