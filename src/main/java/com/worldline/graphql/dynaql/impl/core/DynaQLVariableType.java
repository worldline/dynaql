package com.worldline.graphql.dynaql.impl.core;

public class DynaQLVariableType extends AbstractVariableType {
    @Override
    // TODO: Use StringJoiner
    public String build() {
        StringBuilder builder = new StringBuilder();

        if (this.getChild() != null) {
            builder.append("[");
            builder.append(this.getChild().build());
            builder.append("]");
        } else {
            builder.append(this.getName());
        }

        if (this.isNonNull()) {
            builder.append("!");
        }

        return builder.toString();
    }
}
