package com.worldline.graphql.dynaql.impl.core;

import com.worldline.graphql.dynaql.api.core.ScalarType;
import com.worldline.graphql.dynaql.api.core.VariableType;

public class DynaQLVariableType implements VariableType {
    private String name;
    private boolean nonNull;
    private DynaQLVariableType child;

    /*
    Static factory methods
     */
    // (scalarType)
    public static DynaQLVariableType nonNull(ScalarType scalarType) {
        return new DynaQLVariableType(scalarType.toString(), true, null);
    }

    // (objectType)
    public static DynaQLVariableType nonNull(String name) {
        return new DynaQLVariableType(name, true, null);
    }

    // (varType object)
    public static DynaQLVariableType nonNull(DynaQLVariableType type) {
        type.nonNull = true;
        return type;
    }

    // (scalarType)
    public static DynaQLVariableType list(ScalarType scalarType) {
        DynaQLVariableType childVarType = new DynaQLVariableType(scalarType.toString(), false, null);
        return new DynaQLVariableType(childVarType.getName(), false, childVarType);
    }

    // (typeName)
    public static DynaQLVariableType list(String name) {
        DynaQLVariableType childVarType = new DynaQLVariableType(name, false, null);
        return new DynaQLVariableType(childVarType.getName(), false, childVarType);
    }

    // (variableType object)
    public static DynaQLVariableType list(DynaQLVariableType childVarType) {
        return new DynaQLVariableType(childVarType.getName(), false, childVarType);
    }

    /*
     Constructors
     */
    public DynaQLVariableType(String name, boolean nonNull, DynaQLVariableType child) {
        if (child != null) {
            this.name = "list(" + name + ")";
        } else {
            this.name = name;
        }
        this.nonNull = nonNull;
        this.child = child;
    }

    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();

        if (child != null) {
            builder.append("[");
            builder.append(child.build());
            builder.append("]");
        } else {
            builder.append(name);
        }

        if (nonNull) {
            builder.append("!");
        }

        return builder.toString();
    }

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
    public DynaQLVariableType getChild() {
        return child;
    }

    @Override
    public void setChild(VariableType child) {
        this.child = (DynaQLVariableType) child;
    }
}
