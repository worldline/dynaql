package com.worldline.graphql.dynaql.impl.core;

import com.worldline.graphql.dynaql.api.core.InputObject;
import com.worldline.graphql.dynaql.api.core.InputObjectField;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public class DynaQLInputObject implements InputObject, Cloneable {
    private List<DynaQLInputObjectField> inputObjectFields;

    @SafeVarargs
    public static DynaQLInputObject object(DynaQLInputObjectField... inputObjectFields) {
        return new DynaQLInputObject(asList(inputObjectFields));
    }
    public static DynaQLInputObject object(List<DynaQLInputObjectField> inputObjectFields) {
        return new DynaQLInputObject(inputObjectFields);
    }


    public DynaQLInputObject(List<DynaQLInputObjectField> inputObjectFields) {
        this.inputObjectFields = inputObjectFields;
    }

    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();

        builder.append("{");
        DynaQLInputObjectField[] inputObjectFields = this.inputObjectFields.toArray(new DynaQLInputObjectField[0]);
        for (int i = 0; i < inputObjectFields.length; i++) {
            DynaQLInputObjectField inputObjectField = inputObjectFields[i];
            builder.append(inputObjectField.build());
            if (i < inputObjectFields.length - 1) {
                builder.append(",");
            }
        }
        builder.append("}");

        return builder.toString();
    }

    @Override
    public DynaQLInputObject clone()  {
        DynaQLInputObject inputObject = new DynaQLInputObject(emptyList());
        inputObject.inputObjectFields = new ArrayList<>(this.inputObjectFields);

        return inputObject;
    }

    public List<DynaQLInputObjectField> getInputObjectFields() {
        return inputObjectFields;
    }

    public void setInputObjectFields(List<? extends InputObjectField> inputObjectFields) {
        this.inputObjectFields = (List<DynaQLInputObjectField>)inputObjectFields;
    }
}
