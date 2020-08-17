package com.worldline.graphql.dynaql.impl.core;

import com.worldline.graphql.dynaql.api.core.AbstractOperation;
import com.worldline.graphql.dynaql.api.core.Field;
import com.worldline.graphql.dynaql.api.core.Operation;
import com.worldline.graphql.dynaql.api.core.OperationType;
import com.worldline.graphql.dynaql.api.core.Variable;
import com.worldline.graphql.dynaql.api.core.exceptions.BuildException;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public class DynaQLOperation extends AbstractOperation {

    /*
        Static factory methods
    */
    @SafeVarargs
    public static List<Operation> operations(Operation... operations) {
        return asList(operations);
    }

    // (fields)
    @SafeVarargs
    public static Operation operation(Field... fields) {
        return new DynaQLOperation(OperationType.QUERY, "", emptyList(), asList(fields));
    }

    // (vars, fields)
    @SafeVarargs
    public static Operation operation(List<Variable> vars, DynaQLField... fields) {
        return new DynaQLOperation(OperationType.QUERY, "", vars, asList(fields));
    }

    // (type, fields)
    @SafeVarargs
    public static Operation operation(OperationType type, Field... fields) {
        return new DynaQLOperation(type, "", emptyList(), asList(fields));
    }

    // (type, vars, fields)
    @SafeVarargs
    public static Operation operation(OperationType type, List<Variable> vars, Field... fields) {
        return new DynaQLOperation(type, "", vars, asList(fields));
    }

    // (name, fields)
    @SafeVarargs
    public static Operation operation(String name, Field... fields) {
        return new DynaQLOperation(OperationType.QUERY, name, emptyList(), asList(fields));
    }

    // (type, name, fields)
    @SafeVarargs
    public static Operation operation(OperationType type, String name, Field... fields) {
        return new DynaQLOperation(type, name, emptyList(), asList(fields));
    }

    // (name, vars, fields)
    @SafeVarargs
    public static Operation operation(String name, List<Variable> vars, Field... fields) {
        return new DynaQLOperation(OperationType.QUERY, name, vars, asList(fields));
    }

    // (type, name, vars, fields)
    @SafeVarargs
    public static Operation operation(OperationType type, String name, List<Variable> vars, Field... fields) {
        return new DynaQLOperation(type, name, vars, asList(fields));
    }


    /*
        Constructors
     */
    public DynaQLOperation(OperationType type, String name, List<Variable> vars, List<Field> fields) {
        super(type, name, vars, fields);
    }

    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();

        switch (this.getType()) {
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
                throw new BuildException("Operation type must be one of QUERY, MUTATION or SUBSCRIPTION");
        }

        builder.append(" ");
        builder.append(this.getName());

        if (!this.getVariables().isEmpty()) {
            _buildVariables(builder);
        }

        if (!this.getFields().isEmpty()) {
            _buildFields(builder);
        } else {
            throw new BuildException("An operation must have at least one root field.");
        }

        return builder.toString();
    }

    private void _buildVariables(StringBuilder builder) {
        builder.append("(");

        DynaQLVariable[] vars = this.getVariables().toArray(new DynaQLVariable[0]);
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

        DynaQLField[] rootFields = this.getFields().toArray(new DynaQLField[0]);
        for (int i = 0; i < rootFields.length; i++) {
            DynaQLField rootField = rootFields[i];
            builder.append(rootField.build());
            if (i < rootFields.length - 1) {
                builder.append(" ");
            }
        }

        builder.append("}");
    }
}
