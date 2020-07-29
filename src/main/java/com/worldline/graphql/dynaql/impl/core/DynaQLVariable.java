package com.worldline.graphql.dynaql.impl.core;

import com.worldline.graphql.dynaql.api.core.GraphQLScalarType;
import com.worldline.graphql.dynaql.api.core.Variable;
import com.worldline.graphql.dynaql.api.core.VariableType;

import java.util.List;

import static com.worldline.graphql.dynaql.impl.core.utils.ValueFormatter.format;
import static java.util.Arrays.asList;

public class DynaQLVariable implements Variable {
    private String name;
    private DynaQLVariableType type;
    private Object defaultValue;

    /*
     Static factory methods
     */
    @SafeVarargs
    public static List<DynaQLVariable> vars(DynaQLVariable... vars) {
        return asList(vars);
    }

    // (name, scalarType)
    public static DynaQLVariable var(String name, GraphQLScalarType scalarType) {
        return new DynaQLVariable(name, scalarType.toString(), null);
    }

    // (name, scalarType, defaultValue)
    public static DynaQLVariable var(String name, GraphQLScalarType scalarType, Object defaultValue) {
        return new DynaQLVariable(name, scalarType.toString(), defaultValue);
    }

    // (name, objectType)
    public static DynaQLVariable var(String name, String objectType) {
        return new DynaQLVariable(name, objectType, null);
    }

    // (name, objectType, defaultValue)
    public static DynaQLVariable var(String name, String objectType, Object defaultValue) {
        return new DynaQLVariable(name, objectType, defaultValue);
    }

    // (name, VariableType)
    public static DynaQLVariable var(String name, DynaQLVariableType type) {
        return new DynaQLVariable(name, type, null);
    }

    // (name, VariableType, defaultValue)
    public static DynaQLVariable var(String name, DynaQLVariableType type, Object defaultValue) {
        return new DynaQLVariable(name, type, defaultValue);
    }

    /*
     Constructors
     */
    public DynaQLVariable(String name, String typeName, Object defaultValue) {
        this.name = name;
        DynaQLVariableType type = new DynaQLVariableType(typeName, false, null);
        this.type = type;
        this.defaultValue = defaultValue;
    }

    public DynaQLVariable(String name, DynaQLVariableType type, Object defaultValue) {
        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();

        builder.append("$");
        builder.append(name);
        builder.append(":");
        builder.append(type.build());

        if(defaultValue != null) {
            builder.append("=");
            builder.append(format(defaultValue));
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
    public DynaQLVariableType getType() {
        return type;
    }

    @Override
    public void setType(VariableType type) {
        this.type = (DynaQLVariableType) type;
    }

    @Override
    public Object getDefaultValue() {
        return defaultValue;
    }

    @Override
    public void setDefaultValue(Object value) {
        this.defaultValue = defaultValue;
    }
}
