package com.worldline.graphql.dynaql.impl.core;


import com.worldline.graphql.dynaql.api.core.AbstractInputObjectField;
import com.worldline.graphql.dynaql.api.core.InputObjectField;
import com.worldline.graphql.dynaql.api.core.Variable;

import static com.worldline.graphql.dynaql.impl.core.utils.ValueFormatter.format;

public class DynaQLInputObjectField extends AbstractInputObjectField {

    /*
        Static factory methods
    */
    // (name, value)
    public static InputObjectField prop(String name, Object value) {
        return new DynaQLInputObjectField(name, value);
    }

    // (name, variable)
    public static InputObjectField prop(String name, Variable var) {
        return new DynaQLInputObjectField(name, var);
    }


    /*
        Constructors
    */
    public DynaQLInputObjectField(String name, Object value) {
        super(name, value);
    }

    public DynaQLInputObjectField(String name, Variable var) {
        super(name, var);
    }

    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();

        builder.append(this.getName());
        builder.append(":");
        builder.append(format(this.getValue()));

        return builder.toString();
    }
}
