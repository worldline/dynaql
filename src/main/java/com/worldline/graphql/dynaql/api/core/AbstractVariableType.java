package com.worldline.graphql.dynaql.api.core;

public abstract class AbstractVariableType implements VariableType {
    private String name;
    private boolean nonNull;
    private VariableType child;

    /*
        Constructors
    */
    public AbstractVariableType(String name, boolean nonNull, VariableType child) {
        if (child != null) {
            this.name = "list(" + name + ")";
        } else {
            this.name = name;
        }
        this.nonNull = nonNull;
        this.child = child;
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
    public boolean isNonNull() {
        return nonNull;
    }

    @Override
    public void setNonNull(boolean nonNull) {
        this.nonNull = nonNull;
    }

    @Override
    public VariableType getChild() {
        return child;
    }

    @Override
    public void setChild(VariableType child) {
        this.child = child;
    }
}
