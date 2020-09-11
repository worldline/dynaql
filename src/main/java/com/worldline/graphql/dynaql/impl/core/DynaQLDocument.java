package com.worldline.graphql.dynaql.impl.core;

import org.eclipse.microprofile.graphql.client.core.Operation;

public class DynaQLDocument extends AbstractDocument {
    @Override
    // TODO: use StringJoiner
    public String build() {
        StringBuilder builder = new StringBuilder();

        for (Operation operation : this.getOperations()) {
            builder.append(operation.build());
        }

        return builder.toString();
    }
}
