package com.worldline.graphql.dynaql.api.core;

public enum GraphQLScalarType {
    GQL_INT("Int"),
    GQL_FLOAT("Float"),
    GQL_STRING("String"),
    GQL_BOOL("Boolean"),
    GQL_ID("ID");

    private String type;

    GraphQLScalarType(String type) {
        this.type = type;
    }

    public String toString() {
        return type;
    }
}
