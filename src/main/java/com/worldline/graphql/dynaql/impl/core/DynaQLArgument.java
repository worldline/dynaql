package com.worldline.graphql.dynaql.impl.core;

import static com.worldline.graphql.dynaql.impl.core.utils.ValueFormatter.format;

public class DynaQLArgument extends AbstractArgument {
    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();

        builder.append(this.getName());
        builder.append(":");
        builder.append(format(this.getValue()));

        return builder.toString();
    }
}
