package com.worldline.graphql.dynaql.api.core;

import com.worldline.graphql.dynaql.impl.core.DynaQLVariable;

public abstract class AbstractInputObjectField implements InputObjectField {
    private String name;
    private Object value;

    /*
        Constructors
    */
    public AbstractInputObjectField(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public AbstractInputObjectField(String name, DynaQLVariable var) {
        this.name = name;
        this.value = var;
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
