package com.worldline.graphql.dynaql.impl.core;

import com.worldline.graphql.dynaql.api.core.GraphQLEnum;

public class DynaQLEnum implements GraphQLEnum {
    private String value;

    public static DynaQLEnum gqlEnum(String value) {
        return new DynaQLEnum(value);
    }

    public DynaQLEnum(String value) {
        this.value = value;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
