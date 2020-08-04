package com.worldline.graphql.dynaql.impl.core;

import com.worldline.graphql.dynaql.api.core.AbstractArgument;
import com.worldline.graphql.dynaql.api.core.Argument;
import com.worldline.graphql.dynaql.api.core.InputObject;
import com.worldline.graphql.dynaql.api.core.Variable;

import static com.worldline.graphql.dynaql.impl.core.utils.ValueFormatter.format;

public class DynaQLArgument extends AbstractArgument {

    /*
        Static factory methods
    */
    // (name, raw value)
    public static Argument arg(String name, Object value) { return new DynaQLArgument(name, value); }

    // (name, inputObject)
    public static Argument arg(String name, InputObject inputObject) {
        return new DynaQLArgument(name, inputObject);
    }

    // (name, variable)
    public static Argument arg(String name, Variable var) {
        return new DynaQLArgument(name, var);
    }

    /*
        Constructors
    */
    public DynaQLArgument(String name, Object value) {
        super(name, value);
    }

    public DynaQLArgument(String name, Variable var) {
        super(name, var);
    }

    /*
        Impl
     */
    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();

        builder.append(this.getName());
        builder.append(":");
        builder.append(format(this.getValue()));

        return builder.toString();
    }
}
