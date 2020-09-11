package com.worldline.graphql.dynaql.impl.core;

import org.eclipse.microprofile.graphql.client.core.Argument;
import org.eclipse.microprofile.graphql.client.core.Field;

import java.util.List;

public abstract class AbstractField implements Field {
    private String name;
    private List<Argument> arguments;
    private List<Field> fields;

    /*
        Constructors
    */
    public AbstractField() {
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

    public List<Argument> getArguments() {
        return arguments;
    }

    public void setArguments(List<Argument> arguments) {
        this.arguments = arguments;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
}
