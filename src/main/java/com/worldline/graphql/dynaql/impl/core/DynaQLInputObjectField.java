package com.worldline.graphql.dynaql.impl.core;


import com.worldline.graphql.dynaql.api.core.AbstractInputObjectField;

import static com.worldline.graphql.dynaql.impl.core.utils.ValueFormatter.format;

public class DynaQLInputObjectField extends AbstractInputObjectField {
    // TODO: Use simple String
    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();

        builder.append(this.getName());
        builder.append(":");
        builder.append(format(this.getValue()));

        return builder.toString();
    }
}
