package com.worldline.graphql.dynaql.api.core;

import com.worldline.graphql.dynaql.impl.core.DynaQLInputObjectField;

import java.util.List;

public interface InputObject extends Buildable {
    List<DynaQLInputObjectField> getInputObjectFields();

    void setInputObjectFields(List<DynaQLInputObjectField> inputObjectFields);
}
