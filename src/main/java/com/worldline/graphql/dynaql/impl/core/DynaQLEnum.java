package com.worldline.graphql.dynaql.impl.core;

public class DynaQLEnum extends AbstractEnum {
    @Override
    public String build() {
        return this.getValue();
    }
}
