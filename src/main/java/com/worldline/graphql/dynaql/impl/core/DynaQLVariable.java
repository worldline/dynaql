package com.worldline.graphql.dynaql.impl.core;

import static com.worldline.graphql.dynaql.impl.core.utils.ValueFormatter.format;

public class DynaQLVariable extends AbstractVariable {
    @Override
    // TODO: User StringJoiner
    public String build() {
        StringBuilder builder = new StringBuilder();

        builder.append("$");
        builder.append(this.getName());
        builder.append(":");
        builder.append(this.getType().build());

        if(this.getDefaultValue().isPresent()) {
            builder.append("=");
            builder.append(format(this.getDefaultValue().get()));
        }

        return builder.toString();
    }
}
