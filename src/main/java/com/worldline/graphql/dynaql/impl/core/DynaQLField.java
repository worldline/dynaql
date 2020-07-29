package com.worldline.graphql.dynaql.impl.core;

import com.worldline.graphql.dynaql.api.core.Argument;
import com.worldline.graphql.dynaql.api.core.Field;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public class DynaQLField implements Field {
    private String name;
    private List<DynaQLArgument> arguments;
    private List<DynaQLField> fields;

    /*
    Static factory methods
     */
    @SafeVarargs
    public static List<DynaQLField> fields(DynaQLField... fields) {
        return asList(fields);
    }

    // (name)
    public static DynaQLField field(String name) {
        return new DynaQLField(name, emptyList(), emptyList());
    }

    // (name, subfields)
    @SafeVarargs
    public static DynaQLField field(String name, DynaQLField... fields) {
        return new DynaQLField(name, emptyList(), asList(fields));
    }

    // (name, args)
    @SafeVarargs
    public static DynaQLField field(String name, DynaQLArgument... args) {
        return new DynaQLField(name, asList(args), emptyList());
    }

    // (name, args, subfields)
    @SafeVarargs
    public static DynaQLField field(String name, List<DynaQLArgument> args, DynaQLField... fields) {
        return new DynaQLField(name, args, asList(fields));
    }

    /*
    Constructors
     */
    public DynaQLField(String name, List<DynaQLArgument> args, List<DynaQLField> fields) {
        this.name = name;
        this.arguments = args;
        this.fields = fields;
    }

    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();

        builder.append(this.name);

        // Arguments to build ?
        if (!this.arguments.isEmpty()) {
            builder.append("(");
            builder.append(_buildArgs());
            builder.append(")");
        }

        // Sub-fields to build ?
        if (!this.fields.isEmpty()) {
            builder.append("{");
            builder.append(_buildFields());
            builder.append("}");
        }

        return builder.toString();
    }

    private String _buildArgs() {
        StringBuilder builder = new StringBuilder();

        DynaQLArgument[] arguments = this.arguments.toArray(new DynaQLArgument[0]);
        for (int i = 0; i < arguments.length; i++) {
            DynaQLArgument argument = arguments[i];
            builder.append(argument.build());
            if (i < arguments.length - 1) {
                builder.append(", ");
            }
        }

        return builder.toString();
    }

    private String _buildFields() {
        StringBuilder builder = new StringBuilder();

        DynaQLField[] fields = this.fields.toArray(new DynaQLField[0]);
        for (int i = 0; i < fields.length; i++) {
            DynaQLField field = fields[i];
            builder.append(field.build());
            if (i < fields.length - 1) {
                builder.append(" ");
            }
        }

        return builder.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DynaQLArgument> getArguments() {
        return arguments;
    }

    public void setArguments(List<? extends Argument> arguments) {
        this.arguments = (List<DynaQLArgument>)arguments;
    }

    public List<DynaQLField> getFields() {
        return fields;
    }

    public void setFields(List<? extends Field> fields) {
        this.fields = (List<DynaQLField>)fields;
    }
}
