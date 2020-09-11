package com.worldline.graphql.dynaql.impl.core;

import org.eclipse.microprofile.graphql.client.core.Enum;

public abstract class AbstractEnum implements Enum {
    private String value;

    /*
        Constructors
    */
    public AbstractEnum() {
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
