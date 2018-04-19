package com.fangj.graphql;

import graphql.schema.GraphQLList;
import graphql.schema.GraphQLObjectType;

import static graphql.Scalars.GraphQLInt;
import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

/**
 * @author fangjie
 * @date Created in 下午2:17 18/4/19.
 */
public enum GraphQLObjectTypeEnum {

    FOO(newObject()
            .name("foo")
            .field(newFieldDefinition().name("id").type(GraphQLString))
            .field(newFieldDefinition().name("name").type(GraphQLString))
            .build()),

    ADDRESS(newObject()
            .name("address")
            .field(newFieldDefinition().name("city").type(GraphQLString))
            .field(newFieldDefinition().name("street").type(GraphQLString))
            .build()),

    PERSON(newObject()
            .name("person")
            .field(newFieldDefinition().name("age").type(GraphQLInt))
            .field(newFieldDefinition().name("userName").type(GraphQLString))
            .field(newFieldDefinition().name("sex").type(GraphQLInt))
            .field(newFieldDefinition().name("addresses").type(new GraphQLList(GraphQLObjectTypeEnum.ADDRESS.getType())))
            .build());


    private GraphQLObjectType graphQLObjectType;

    GraphQLObjectTypeEnum(GraphQLObjectType graphQLObjectType) {
        this.graphQLObjectType = graphQLObjectType;
    }

    public GraphQLObjectType getType() {
        return graphQLObjectType;
    }

}
