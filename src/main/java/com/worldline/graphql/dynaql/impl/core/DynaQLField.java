package com.worldline.graphql.dynaql.impl.core;

import com.worldline.graphql.dynaql.api.core.AbstractField;
import com.worldline.graphql.dynaql.api.core.Argument;
import com.worldline.graphql.dynaql.api.core.Field;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public class DynaQLField extends AbstractField {

    /*
        Static factory methods
    */
    @SafeVarargs
    public static List<Field> fields(Field... fields) {
        return asList(fields);
    }

    // (name)
    public static Field field(String name) {
        return new DynaQLField(name, emptyList(), emptyList());
    }

    // (name, subfields)
    @SafeVarargs
    public static Field field(String name, Field... fields) {
        return new DynaQLField(name, emptyList(), asList(fields));
    }

    // (name, args)
    @SafeVarargs
    public static Field field(String name, Argument... args) {
        return new DynaQLField(name, asList(args), emptyList());
    }

    // (name, args, subfields)
    @SafeVarargs
    public static Field field(String name, List<Argument> args, Field... fields) {
        return new DynaQLField(name, args, asList(fields));
    }

    /*
        Constructors
    */
    public DynaQLField(String name, List<Argument> args, List<Field> fields) {
        super(name, args, fields);
    }

    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();

        builder.append(this.getName());

        // Arguments to build ?
        if (!this.getArguments().isEmpty()) {
            builder.append("(");
            builder.append(_buildArgs());
            builder.append(")");
        }

        // Sub-fields to build ?
        if (!this.getFields().isEmpty()) {
            builder.append("{");
            builder.append(_buildFields());
            builder.append("}");
        }

        return builder.toString();
    }

    private String _buildArgs() {
        StringBuilder builder = new StringBuilder();

        DynaQLArgument[] arguments = this.getArguments().toArray(new DynaQLArgument[0]);
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

        DynaQLField[] fields = this.getFields().toArray(new DynaQLField[0]);
        for (int i = 0; i < fields.length; i++) {
            DynaQLField field = fields[i];
            builder.append(field.build());
            if (i < fields.length - 1) {
                builder.append(" ");
            }
        }

        return builder.toString();
    }
}
