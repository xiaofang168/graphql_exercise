package com.fangj.graphql.schema;

import com.alibaba.fastjson.JSON;
import com.fangj.graphql.DataContainer;
import com.fangj.graphql.GraphQLObjectTypeEnum;
import com.fangj.graphql.bean.Person;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;

import java.util.Map;
import java.util.Optional;

import static graphql.Scalars.GraphQLInt;
import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

/**
 * @author fangjie
 * @date Created in 下午2:04 18/4/19.
 */
public class QuaryWithArgs {

    public static void main(String[] args) {
        GraphQLObjectType queryType = newObject()
                .name("personQuery")
                .field(newFieldDefinition()
                        .type(GraphQLObjectTypeEnum.PERSON.getType())
                        .name("person")
                        .argument(newArgument()
                                .name("age")
                                .type(new GraphQLNonNull(GraphQLInt)))
                        .dataFetcher(environment -> {
                            int age = environment.getArgument("age");
                            Optional<Person> person = DataContainer.personList.stream().filter(p -> p.getAge() == age).findFirst();
                            return person;
                        }))
                .build();

        GraphQLSchema graphQLSchema1 = GraphQLSchema.newSchema().query(queryType).build();
        GraphQL build1 = GraphQL.newGraphQL(graphQLSchema1).build();
        ExecutionResult executionResult1 = build1.execute("{person(age:30) {userName, age, sex, addresses {city}}}");
        Map<String, Object> map1 = executionResult1.getData();
        System.out.println(JSON.toJSONString(map1));
    }

}
