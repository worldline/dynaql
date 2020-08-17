package com.worldline.graphql.dynaql.impl.core;

import com.worldline.graphql.dynaql.api.core.AbstractVariableType;
import com.worldline.graphql.dynaql.api.core.ScalarType;
import com.worldline.graphql.dynaql.api.core.VariableType;

public class DynaQLVariableType extends AbstractVariableType {

    /*
        Static factory methods
    */
    // (scalarType)
    public static VariableType nonNull(ScalarType scalarType) {
        return new DynaQLVariableType(scalarType, true, null);
    }

    // (objectType)
    public static VariableType nonNull(String name) {
        return new DynaQLVariableType(name, true, null);
    }

    // (varType)
    public static VariableType nonNull(VariableType type) {
        type.setNonNull(true);
        return type;
    }

    // (scalarType)
    public static VariableType list(ScalarType scalarType) {
        DynaQLVariableType childVarType = new DynaQLVariableType(scalarType, false, null);
        return new DynaQLVariableType(childVarType.getName(), false, childVarType);
    }

    // (typeName)
    public static VariableType list(String name) {
        DynaQLVariableType childVarType = new DynaQLVariableType(name, false, null);
        return new DynaQLVariableType(childVarType.getName(), false, childVarType);
    }

    // (variableType)
    public static VariableType list(VariableType childVarType) {
        return new DynaQLVariableType(childVarType.getName(), false, childVarType);
    }

    /*
        Constructors
    */
    public DynaQLVariableType(ScalarType scalarType, boolean nonNull, VariableType child) {
        super(scalarType.toString(), nonNull, child);
    }

    public DynaQLVariableType(String name, boolean nonNull, VariableType child) {
        super(name, nonNull, child);
    }

    /*
        Impl
     */
    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();

        if (this.getChild() != null) {
            builder.append("[");
            builder.append(this.getChild().build());
            builder.append("]");
        } else {
            builder.append(this.getName());
        }

        if (this.isNonNull()) {
            builder.append("!");
        }

        return builder.toString();
    }
}
