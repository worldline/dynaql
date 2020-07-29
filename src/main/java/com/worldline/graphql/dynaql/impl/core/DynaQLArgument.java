package com.worldline.graphql.dynaql.impl.core;

import com.worldline.graphql.dynaql.api.core.Argument;

import java.util.List;

import static com.worldline.graphql.dynaql.impl.core.utils.ValueFormatter.format;
import static java.util.Arrays.asList;

public class DynaQLArgument implements Argument {
    private String name;
    private Object value;

    /*
    Static factory methods
     */
    @SafeVarargs
    public static List<DynaQLArgument> args(DynaQLArgument... args) {
        return asList(args);
    }

    public static DynaQLArgument arg(String name, Object value) {
        return new DynaQLArgument(name, value);
    }
    public static DynaQLArgument arg(String name, DynaQLInputObject inputObject) {
        return new DynaQLArgument(name, inputObject);
    }
    public static DynaQLArgument arg(String name, DynaQLVariable var) {
        return new DynaQLArgument(name, var);
    }

    /*
    Constructors
    */
    public DynaQLArgument(String name, Object value) {
        this.name = name;
        this.value = value;
    }
    public DynaQLArgument(String name, DynaQLVariable var) {
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
