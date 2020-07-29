package com.worldline.graphql.dynaql.api.core;

public interface VariableType extends Buildable {
    String getName();

    void setName(String name);

    boolean isNonNull();

    void setNonNull(boolean nonNull);

    VariableType getChild();

    void setChild(VariableType child);

    default boolean isList() {
        return getChild() != null;
    }
}
