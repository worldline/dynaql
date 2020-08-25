package com.worldline.graphql.dynaql.api.core;

import org.eclipse.microprofile.graphql.client.core.InputObjectField;

public abstract class AbstractInputObjectField implements InputObjectField {
    private String name;
    private Object value;

    /*
        Constructors
    */
    public AbstractInputObjectField() {
    }

    /*
        Getter/Setter
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
