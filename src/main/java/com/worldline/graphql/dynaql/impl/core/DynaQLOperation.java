package com.worldline.graphql.dynaql.impl.core;

import com.worldline.graphql.dynaql.api.core.Operation;
import com.worldline.graphql.dynaql.impl.core.exceptions.BuilderException;

import java.util.List;

import static java.util.Arrays.asList;

public class DynaQLOperation implements Operation {
    private Type type;
    private String name;
    private List<DynaQLField> fields;

    @SafeVarargs
    public static List<DynaQLOperation> operations(DynaQLOperation... operations) {
        return asList(operations);
    }

    @SafeVarargs
    public static DynaQLOperation operation(DynaQLField... fields) { return new DynaQLOperation(Type.QUERY, "", asList(fields)); }
    public static DynaQLOperation operation(List<DynaQLField> fields) { return new DynaQLOperation(Type.QUERY, "", fields); }

    @SafeVarargs
    public static DynaQLOperation operation(Type type, DynaQLField... fields) {
        return new DynaQLOperation(type, "", asList(fields));
    }
    public static DynaQLOperation operation(Type type, List<DynaQLField> fields) {
        return new DynaQLOperation(type, "", fields);
    }

    @SafeVarargs
    public static DynaQLOperation operation(String name, DynaQLField... fields) {
        return new DynaQLOperation(Type.QUERY, name, asList(fields));
    }
    public static DynaQLOperation operation(String name, List<DynaQLField> fields) {
        return new DynaQLOperation(Type.QUERY, name, fields);
    }

    @SafeVarargs
    public static DynaQLOperation operation(Type type, String name, DynaQLField... fields) {
        return new DynaQLOperation(type, name, asList(fields));
    }
    public static DynaQLOperation operation(Type type, String name, List<DynaQLField> fields) {
        return new DynaQLOperation(type, name, fields);
    }

    public DynaQLOperation(Type type, String name, List<DynaQLField> fields) {
        this.type = type;
        this.name = name;
        this.fields = fields;
    }

    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();

        switch (type) {
            case QUERY:
                builder.append("query ");
                break;
            case MUTATION:
                builder.append("mutation ");
                break;
            case SUBSCRIPTION:
                builder.append("subscription ");
                break;
            default:
                throw new BuilderException("Operation type must be one of QUERY, MUTATION or SUBSCRIPTION");
        }

        builder.append(this.name);
        builder.append("{");
        if (!this.fields.isEmpty()) {
            DynaQLField[] rootFields = this.fields.toArray(new DynaQLField[0]);
            for (int i = 0; i < rootFields.length; i++) {
                DynaQLField rootField = rootFields[i];
                builder.append(rootField.build());
                if (i < rootFields.length - 1) {
                    builder.append(" ");
                }
            }
        } else {
            throw new BuilderException("An operation must have at least one root field.");
        }
        builder.append("}");

        return builder.toString();
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DynaQLField> getFields() {
        return fields;
    }

    public void setFields(List<DynaQLField> fields) {
        this.fields = fields;
    }
}
