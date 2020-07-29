package com.worldline.graphql.dynaql.impl.core;


import com.worldline.graphql.dynaql.api.core.InputObjectField;

import static com.worldline.graphql.dynaql.impl.core.utils.ValueFormatter.format;

public class DynaQLInputObjectField implements InputObjectField {
    private String name;
    private Object value;

    /*
    Static factory methods
     */
    // (name, value)
    public static DynaQLInputObjectField prop(String name, Object value) {
        return new DynaQLInputObjectField(name, value);
    }

    // (name, variable)
    public static DynaQLInputObjectField prop(String name, DynaQLVariable var) {
        return new DynaQLInputObjectField(name, var);
    }


    /*
    Constructors
     */
    public DynaQLInputObjectField(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public DynaQLInputObjectField(String name, DynaQLVariable var) {
        this.name = name;
        this.value = var;
    }

    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();

        builder.append(this.name);
        builder.append(":");
        builder.append(format(this.value));

        return builder.toString();
    }

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
