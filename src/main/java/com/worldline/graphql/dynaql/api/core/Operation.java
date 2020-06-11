package com.worldline.graphql.dynaql.api.core;

import com.worldline.graphql.dynaql.impl.core.DynaQLField;

import java.util.List;

public interface Operation extends Buildable {
    enum Type {
        QUERY,
        MUTATION,
        SUBSCRIPTION
    }

    Type getType();

    void setType(Type type);

    String getName();

    void setName(String name);

    List<DynaQLField> getFields();

    void setFields(List<DynaQLField> fields);
}
