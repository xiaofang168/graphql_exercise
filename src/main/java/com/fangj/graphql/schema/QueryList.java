package com.fangj.graphql.schema;

import com.alibaba.fastjson.JSON;
import com.fangj.graphql.bean.Address;
import com.fangj.graphql.bean.Person;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static graphql.Scalars.GraphQLInt;
import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

/**
 * @author fangjie
 * @date Created in 上午10:22 18/4/19.
 */
public class QueryList {

    public static void main(String[] args) {

        GraphQLObjectType addressType = newObject()
                .name("address")
                .field(newFieldDefinition().name("city").type(GraphQLString))
                .field(newFieldDefinition().name("street").type(GraphQLString))
                .build();

        GraphQLObjectType personType = newObject()
                .name("person")
                .field(newFieldDefinition().name("age").type(GraphQLInt))
                .field(newFieldDefinition().name("userName").type(GraphQLString))
                .field(newFieldDefinition().name("sex").type(GraphQLInt))
                .field(newFieldDefinition().name("addresses").type(new GraphQLList(addressType)))
                .build();

        DataFetcher<List<Person>> personListDataFetcher = environment -> {
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

            return Arrays.asList(person, person2);
        };

        GraphQLObjectType queryList = newObject()
                .name("queryList")
                .description("query person list")
                .field(newFieldDefinition().name("persons").type(new GraphQLList(personType)).dataFetcher(personListDataFetcher))
                .build();

        GraphQLObjectType queryOne = newObject()
                .name("queryOne")
                .description("query person one")
                .field(newFieldDefinition().name("person").type(personType).dataFetcher(environment -> {
                    Person person = new Person();
                    person.setUserName("张三");
                    person.setAge(30);
                    person.setSex(1);
                    Address address = new Address();
                    address.setCity("北京市");
                    address.setStreet("望京");
                    person.setAddresses(Arrays.asList(address));
                    return person;
                }))
                .build();

        GraphQLSchema graphQLSchema = GraphQLSchema.newSchema().query(queryList).build();
        GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();
        ExecutionResult executionResult = build.execute("{persons {userName,age,sex}}");

        Map<String, Object> map = executionResult.getData();
        System.out.println(JSON.toJSONString(map));

        GraphQLSchema graphQLSchema1 = GraphQLSchema.newSchema().query(queryOne).build();
        GraphQL build1 = GraphQL.newGraphQL(graphQLSchema1).build();
        ExecutionResult executionResult1 = build1.execute("{person {userName, age, sex, addresses {city}}}");

        Map<String, Object> map1 = executionResult1.getData();
        System.out.println(JSON.toJSONString(map1));
    }
}

