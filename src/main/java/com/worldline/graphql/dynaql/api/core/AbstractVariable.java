package com.worldline.graphql.dynaql.api.core;

public abstract class AbstractVariable implements Variable {
    private String name;
    private VariableType type;
    private Object defaultValue;

    /*
        Constructors
    */
    public AbstractVariable(String name, VariableType type, Object defaultValue) {
        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    /*
        Getter/Setter
    */
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public VariableType getType() {
        return type;
    }

    @Override
    public void setType(VariableType type) {
        this.type = type;
    }

    @Override
    public Object getDefaultValue() {
        return defaultValue;
    }

    @Override
    public void setDefaultValue(Object value) {
        this.defaultValue = value;
    }
}
