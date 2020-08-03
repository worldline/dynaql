package com.worldline.graphql.dynaql.impl.core;

import com.worldline.graphql.dynaql.api.core.Enum;

public class DynaQLEnum implements Enum {
    private String value;

    /*
    Static factory methods
     */
    public static DynaQLEnum gqlEnum(String value) {
        return new DynaQLEnum(value);
    }

    /*
    Constructors
    */
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
