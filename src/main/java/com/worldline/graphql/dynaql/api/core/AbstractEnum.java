package com.worldline.graphql.dynaql.api.core;

public abstract class AbstractEnum implements Enum {
    private String value;

    /*
        Constructors
    */
    public AbstractEnum(String value) {
        this.value = value;
    }

    /*
        Getter/Setter
    */
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
