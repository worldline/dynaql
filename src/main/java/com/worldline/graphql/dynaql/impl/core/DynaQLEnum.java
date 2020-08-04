package com.worldline.graphql.dynaql.impl.core;

import com.worldline.graphql.dynaql.api.core.AbstractEnum;

public class DynaQLEnum extends AbstractEnum {
    private String value;

    /*
        Static factory methods
    */
    public static com.worldline.graphql.dynaql.api.core.Enum gqlEnum(String value) {
        return new DynaQLEnum(value);
    }

    /*
        Constructors
    */
    public DynaQLEnum(String value) {
        super(value);
    }
}
