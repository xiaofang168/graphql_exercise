package com.fangj.graphql.schema;

import com.alibaba.fastjson.JSON;
import com.fangj.graphql.GraphQLObjectTypeEnum;
import com.fangj.graphql.bean.Foo;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;

import java.util.Map;

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

/**
 * @author fangjie
 * @date Created in 下午7:22 18/4/18.
 */
public class DefineRun {

    public static void main(String[] args) {
        DataFetcher<Foo> fooDataFetcher = environment -> {
            Foo foo = new Foo();
            foo.setId(1L);
            foo.setName("test");
            return foo;
        };

        // 定义暴露给客户端的查询query api
        GraphQLObjectType queryType = newObject()
                .name("helloWorldQuery")
                .description("query foo")
                .field(newFieldDefinition().name("foo").type(GraphQLObjectTypeEnum.FOO.getType()).dataFetcher(fooDataFetcher))
                .build();


        GraphQLSchema graphQLSchema = GraphQLSchema.newSchema().query(queryType).build();

        GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();
        ExecutionResult executionResult = build.execute("{foo {id,name} }");

        Map<String, Object> map = executionResult.getData();
        System.out.println(JSON.toJSONString(map));

    }

}
