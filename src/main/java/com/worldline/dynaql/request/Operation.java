package com.worldline.dynaql.request;

import com.worldline.dynaql.request.exceptions.RequestBuilderException;
import java.util.List;
import static java.util.Arrays.asList;

public class Operation implements IBuildable {
    private Type type;
    private String name;
    private List<Field> fields;

    @SafeVarargs
    public static List<Operation> operations(Operation... operations) {
        return asList(operations);
    }
    @SafeVarargs
    public static Operation operation(Field... fields) { return new Operation(fields); }
    @SafeVarargs
    public static Operation operation(Type type, Field... fields) {
        return new Operation(type, fields);
    }
    @SafeVarargs
    public static Operation operation(Type type, String name, Field... fields) {
        return new Operation(type, name, fields);
    }

    @SafeVarargs
    public Operation(Field... fields) {
        this.type = Type.QUERY;
        this.name = "";
        this.fields = asList(fields);
    }

    @SafeVarargs
    public Operation(Type type, Field... fields) {
        this.type = type;
        this.name = "";
        this.fields = asList(fields);
    }

    @SafeVarargs
    public Operation(Type type, String name, Field... fields) {
        this.type = type;
        this.name = name;
        this.fields = asList(fields);
    }

    @Override
    public void build(StringBuilder builder) throws RequestBuilderException {
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
                throw new RequestBuilderException("Operation type must be one of QUERY, MUTATION or SUBSCRIPTION");
        }

        builder.append(this.name);
        builder.append("{");
        if (!this.fields.isEmpty()) {
            Field[] rootFields = this.fields.toArray(new Field[0]);
            for (int i = 0; i < rootFields.length; i++) {
                Field rootField = rootFields[i];
                rootField.build(builder);
                if (i < rootFields.length - 1) {
                    builder.append(" ");
                }
            }
        } else {
            throw new RequestBuilderException("An operation must have at least one root field.");
        }
        builder.append("}");
    }

    public enum Type {
        QUERY,
        MUTATION,
        SUBSCRIPTION
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

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
}
