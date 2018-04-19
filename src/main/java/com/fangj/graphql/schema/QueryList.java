package com.fangj.graphql.schema;

import com.alibaba.fastjson.JSON;
import com.fangj.graphql.DataContainer;
import com.fangj.graphql.GraphQLObjectTypeEnum;
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

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

/**
 * @author fangjie
 * @date Created in 上午10:22 18/4/19.
 */
public class QueryList {

    public static void main(String[] args) {

        GraphQLObjectType addressType = GraphQLObjectTypeEnum.ADDRESS.getType();

        GraphQLObjectType personType = GraphQLObjectTypeEnum.PERSON.getType();

        DataFetcher<List<Person>> personListDataFetcher = environment -> DataContainer.personList;

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

        GraphQLObjectType queryAddress = newObject()
                .name("queryAddress")
                .description("query address")
                .field(newFieldDefinition().name("addresses").type(new GraphQLList(addressType)).dataFetcher(environment -> {
                    Address a = new Address();
                    a.setStreet("河西区");
                    a.setCity("天津市");
                    return Arrays.asList(a);
                }))
                .build();
        GraphQLSchema graphQLSchema2 = GraphQLSchema.newSchema().query(queryAddress).build();
        GraphQL build2 = GraphQL.newGraphQL(graphQLSchema2).build();
        ExecutionResult executionResult2 = build2.execute("{addresses {city}}");
        Map<String, Object> map2 = executionResult2.getData();
        System.out.println(JSON.toJSONString(map2));
    }

}

