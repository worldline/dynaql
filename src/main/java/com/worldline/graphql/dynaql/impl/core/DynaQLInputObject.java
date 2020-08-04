package com.worldline.graphql.dynaql.impl.core;

import com.worldline.graphql.dynaql.api.core.AbstractInputObject;
import com.worldline.graphql.dynaql.api.core.InputObject;
import com.worldline.graphql.dynaql.api.core.InputObjectField;

import java.util.List;

import static java.util.Arrays.asList;

public class DynaQLInputObject extends AbstractInputObject {

    /*
        Static factory methods
    */
    @SafeVarargs
    public static InputObject inputObject(InputObjectField... inputObjectFields) {
        return new DynaQLInputObject(asList(inputObjectFields));
    }

    /*
        Constructors
    */
    public DynaQLInputObject(List<InputObjectField> inputObjectFields) {
        super(inputObjectFields);
    }

    /*
        Impl
    */
    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();

        builder.append("{");
        DynaQLInputObjectField[] inputObjectFields = this.getInputObjectFields().toArray(new DynaQLInputObjectField[0]);
        for (int i = 0; i < inputObjectFields.length; i++) {
            DynaQLInputObjectField inputObjectField = inputObjectFields[i];
            builder.append(inputObjectField.build());
            if (i < inputObjectFields.length - 1) {
                builder.append(" ");
            }
        }
        builder.append("}");

        return builder.toString();
    }
}
