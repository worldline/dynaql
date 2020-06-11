package com.worldline.graphql.dynaql.api.core;

import com.worldline.graphql.dynaql.impl.core.DynaQLArgument;

import java.util.List;

public interface Field extends Buildable {
    String getName();

    void setName(String name);

    List<DynaQLArgument> getArguments();

    void setArguments(List<DynaQLArgument> arguments);

    List<? extends Field> getFields();

    void setFields(List<? extends Field> fields);
}
