package com.worldline.graphql.dynaql.request;

import com.worldline.graphql.dynaql.request.exceptions.RequestBuilderException;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public class Field implements IBuildable {
    private String name;
    private List<Argument> arguments;
    private List<Field> fields;

    @SafeVarargs
    public static List<Field> fields(Field... fields) {
        return asList(fields);
    }

    public static Field field(String name) {
        return new Field(name, emptyList(), emptyList());
    }

    @SafeVarargs
    public static Field field(String name, Field... fields) {
        return new Field(name, emptyList(), asList(fields));
    }
    public static Field field(String name, List<Field> fields) {
        return new Field(name, emptyList(), fields);
    }

    @SafeVarargs
    public static Field field(String name, Argument... args) {
        return new Field(name, asList(args), emptyList());
    }

    @SafeVarargs
    public static Field field(String name, List<Argument> args, Field... fields) {
        return new Field(name, args, asList(fields));
    }
    public static Field field(String name, List<Argument> args, List<Field> fields) {
        return new Field(name, args, fields);
    }


    public Field(String name, List<Argument> args, List<Field> fields) {
        this.name = name;
        this.arguments = args;
        this.fields = fields;
    }

    @Override
    public void build(StringBuilder builder) throws RequestBuilderException {
        builder.append(this.name);

        // Arguments to build ?
        if (!this.arguments.isEmpty()) {
            builder.append("(");
            _buildArgs(builder);
            builder.append(")");
        }

        // Sub-fields to build ?
        if (!this.fields.isEmpty()) {
            builder.append("{");
            _buildFields(builder);
            builder.append("}");
        }
    }

    private void _buildArgs(StringBuilder builder) throws RequestBuilderException {
        Argument[] arguments = this.arguments.toArray(new Argument[0]);
        for (int i = 0; i < arguments.length; i++) {
            Argument argument = arguments[i];
            argument.build(builder);
            if (i < arguments.length - 1) {
                builder.append(",");
            }
        }
    }

    private void _buildFields(StringBuilder builder) throws RequestBuilderException {
        Field[] fields = this.fields.toArray(new Field[0]);
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.build(builder);
            if (i < fields.length - 1) {
                builder.append(" ");
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Argument> getArguments() {
        return arguments;
    }

    public void setArguments(List<Argument> arguments) {
        this.arguments = arguments;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
}
