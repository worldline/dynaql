package com.worldline.graphql.dynaql.request;

public class GraphQLEnum {
    private String value;

    public static GraphQLEnum gqlEnum(String value) {
        return new GraphQLEnum(value);
    }

    public GraphQLEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
