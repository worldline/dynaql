package com.worldline.graphql.dynaql.impl.core;

import com.worldline.graphql.dynaql.api.core.AbstractVariable;
import com.worldline.graphql.dynaql.api.core.ScalarType;
import com.worldline.graphql.dynaql.api.core.Variable;
import com.worldline.graphql.dynaql.api.core.VariableType;

import java.util.List;

import static com.worldline.graphql.dynaql.impl.core.utils.ValueFormatter.format;
import static java.util.Arrays.asList;

public class DynaQLVariable extends AbstractVariable {

    /*
        Static factory methods
    */
    @SafeVarargs
    public static List<Variable> vars(Variable... vars) {
        return asList(vars);
    }

    // (name, scalarType)
    public static Variable var(String name, ScalarType scalarType) {
        return new DynaQLVariable(name, scalarType, null);
    }

    // (name, scalarType, defaultValue)
    public static Variable var(String name, ScalarType scalarType, Object defaultValue) {
        return new DynaQLVariable(name, scalarType, defaultValue);
    }

    // (name, objectType)
    public static Variable var(String name, String objectType) {
        return new DynaQLVariable(name, objectType, null);
    }

    // (name, objectType, defaultValue)
    public static Variable var(String name, String objectType, Object defaultValue) {
        return new DynaQLVariable(name, objectType, defaultValue);
    }

    // (name, variableType)
    public static Variable var(String name, VariableType type) {
        return new DynaQLVariable(name, type, null);
    }

    // (name, variableType, defaultValue)
    public static Variable var(String name, VariableType type, Object defaultValue) {
        return new DynaQLVariable(name, type, defaultValue);
    }

    /*
        Constructors
    */
    public DynaQLVariable(String name, VariableType type, Object defaultValue) {
        super(name, type, defaultValue);
    }

    public DynaQLVariable(String name, ScalarType scalarType, Object defaultValue) {
        super(name, new DynaQLVariableType(scalarType.toString(), false, null), defaultValue);
    }

    public DynaQLVariable(String name, String typeName, Object defaultValue) {
        super(name, new DynaQLVariableType(typeName, false, null), defaultValue);
    }

    /*
        Impl
     */
    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();

        builder.append("$");
        builder.append(this.getName());
        builder.append(":");
        builder.append(this.getType().build());

        if(this.getDefaultValue() != null) {
            builder.append("=");
            builder.append(format(this.getDefaultValue()));
        }

        return builder.toString();
    }
}
