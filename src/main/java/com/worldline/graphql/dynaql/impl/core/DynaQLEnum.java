package com.worldline.graphql.dynaql.impl.core;

import com.worldline.graphql.dynaql.api.core.AbstractEnum;

import static com.worldline.graphql.dynaql.impl.core.utils.ValueFormatter.format;

public class DynaQLEnum extends AbstractEnum {
    @Override
    public String build() {
        return this.getValue();
    }
}
