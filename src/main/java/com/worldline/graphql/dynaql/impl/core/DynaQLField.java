package com.worldline.graphql.dynaql.impl.core;

public class DynaQLField extends AbstractField {
    // TODO: Use StringJoiner
    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();

        builder.append(this.getName());

        // Arguments to build ?
        if (!this.getArguments().isEmpty()) {
            builder.append("(");
            builder.append(_buildArgs());
            builder.append(")");
        }

        // Sub-fields to build ?
        if (!this.getFields().isEmpty()) {
            builder.append("{");
            builder.append(_buildFields());
            builder.append("}");
        }

        return builder.toString();
    }

    // TODO: Use StringJoiner  or Stream + Collectors.joining (https://www.baeldung.com/java-strings-concatenation)
    private String _buildArgs() {
        StringBuilder builder = new StringBuilder();

        DynaQLArgument[] arguments = this.getArguments().toArray(new DynaQLArgument[0]);
        for (int i = 0; i < arguments.length; i++) {
            DynaQLArgument argument = arguments[i];
            builder.append(argument.build());
            if (i < arguments.length - 1) {
                builder.append(", ");
            }
        }

        return builder.toString();
    }

    // TODO: Use StringJoiner  or Stream + Collectors.joining (https://www.baeldung.com/java-strings-concatenation)
    private String _buildFields() {
        StringBuilder builder = new StringBuilder();

        DynaQLField[] fields = this.getFields().toArray(new DynaQLField[0]);
        for (int i = 0; i < fields.length; i++) {
            DynaQLField field = fields[i];
            builder.append(field.build());
            if (i < fields.length - 1) {
                builder.append(" ");
            }
        }

        return builder.toString();
    }
}
