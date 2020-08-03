package com.worldline.graphql.dynaql.impl.core;

import com.worldline.graphql.dynaql.api.core.Field;
import com.worldline.graphql.dynaql.api.core.Operation;
import com.worldline.graphql.dynaql.api.core.Variable;
import com.worldline.graphql.dynaql.impl.core.exceptions.BuilderException;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public class DynaQLOperation implements Operation {
    private Type type;
    private String name;
    private List<DynaQLVariable> variables;
    private List<DynaQLField> fields;

    /*
    Static factory methods
     */
    @SafeVarargs
    public static List<DynaQLOperation> operations(DynaQLOperation... operations) {
        return asList(operations);
    }

    // (fields)
    @SafeVarargs
    public static DynaQLOperation operation(DynaQLField... fields) {
        return new DynaQLOperation(Type.QUERY, "", emptyList(), asList(fields));
    }

    // (vars, fields)
    @SafeVarargs
    public static DynaQLOperation operation(List<DynaQLVariable> vars, DynaQLField... fields) {
        return new DynaQLOperation(Type.QUERY, "", vars, asList(fields));
    }

    // (type, fields)
    @SafeVarargs
    public static DynaQLOperation operation(Type type, DynaQLField... fields) {
        return new DynaQLOperation(type, "", emptyList(), asList(fields));
    }

    // (type, vars, fields)
    @SafeVarargs
    public static DynaQLOperation operation(Type type, List<DynaQLVariable> vars, DynaQLField... fields) {
        return new DynaQLOperation(type, "", vars, asList(fields));
    }

    // (name, fields)
    @SafeVarargs
    public static DynaQLOperation operation(String name, DynaQLField... fields) {
        return new DynaQLOperation(Type.QUERY, name, emptyList(), asList(fields));
    }

    // (type, name, fields)
    @SafeVarargs
    public static DynaQLOperation operation(Type type, String name, DynaQLField... fields) {
        return new DynaQLOperation(type, name, emptyList(), asList(fields));
    }

    // (name, vars, fields)
    @SafeVarargs
    public static DynaQLOperation operation(String name, List<DynaQLVariable> vars, DynaQLField... fields) {
        return new DynaQLOperation(Type.QUERY, name, vars, asList(fields));
    }

    // (type, name, vars, fields)
    @SafeVarargs
    public static DynaQLOperation operation(Type type, String name, List<DynaQLVariable> vars, DynaQLField... fields) {
        return new DynaQLOperation(type, name, vars, asList(fields));
    }


    /*
    Constructors
     */
    public DynaQLOperation(Type type, String name, List<DynaQLVariable> vars, List<DynaQLField> fields) {
        this.type = type;
        this.name = name;
        this.variables = vars;
        this.fields = fields;
    }

    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();

        switch (type) {
            case QUERY:
                builder.append("query");
                break;
            case MUTATION:
                builder.append("mutation");
                break;
            case SUBSCRIPTION:
                builder.append("subscription");
                break;
            default:
                throw new BuilderException("Operation type must be one of QUERY, MUTATION or SUBSCRIPTION");
        }

        builder.append(" ");
        builder.append(this.name);

        if (!this.variables.isEmpty()) {
            _buildVariables(builder);
        }

        if (!this.fields.isEmpty()) {
            _buildFields(builder);
        } else {
            throw new BuilderException("An operation must have at least one root field.");
        }


        return builder.toString();
    }

    private void _buildVariables(StringBuilder builder) {
        builder.append("(");

        DynaQLVariable[] vars = this.variables.toArray(new DynaQLVariable[0]);
        for (int i = 0; i < vars.length; i++) {
            DynaQLVariable variable = vars[i];
            builder.append(variable.build());
            if (i < vars.length - 1) {
                builder.append(", ");
            }
        }

        builder.append(")");
    }

    private void _buildFields(StringBuilder builder) {
        builder.append("{");

        DynaQLField[] rootFields = this.fields.toArray(new DynaQLField[0]);
        for (int i = 0; i < rootFields.length; i++) {
            DynaQLField rootField = rootFields[i];
            builder.append(rootField.build());
            if (i < rootFields.length - 1) {
                builder.append(" ");
            }
        }

        builder.append("}");
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

    public List<DynaQLVariable> getVariables() {
        return variables;
    }

    public void setVariables(List<? extends Variable> vars) {
        this.variables = (List<DynaQLVariable>) vars;
    }

    public List<DynaQLField> getFields() {
        return fields;
    }

    public void setFields(List<? extends Field> fields) {
        this.fields = (List<DynaQLField>) fields;
    }
}
