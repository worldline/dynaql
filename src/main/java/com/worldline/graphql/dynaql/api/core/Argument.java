package com.worldline.graphql.dynaql.api.core;

public interface Argument extends Buildable {
    String getName();

    void setName(String name);

    Object getValue();

    void setValue(Object value);
}
