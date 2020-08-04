package com.worldline.graphql.dynaql.api.core;

import java.util.List;

public abstract class AbstractField implements Field {
    private String name;
    private List<? extends Argument> arguments;
    private List<? extends Field> fields;

    /*
        Constructors
    */
    public AbstractField(String name, List<? extends Argument> args, List<? extends Field> fields) {
        this.name = name;
        this.arguments = args;
        this.fields = fields;
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

    public List<? extends Argument> getArguments() {
        return arguments;
    }

    public void setArguments(List<? extends Argument> arguments) {
        this.arguments = arguments;
    }

    public List<? extends Field> getFields() {
        return fields;
    }

    public void setFields(List<? extends Field> fields) {
        this.fields = fields;
    }
}
