package com.worldline.graphql.dynaql.impl.core;

public class DynaQLInputObject extends AbstractInputObject {
    // TODO: Use StringJoiner  or Stream + Collectors.joining (https://www.baeldung.com/java-strings-concatenation)
    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();

        builder.append("{");
        DynaQLInputObjectField[] inputObjectFields = this.getInputObjectFields().toArray(new DynaQLInputObjectField[0]);
        for (int i = 0; i < inputObjectFields.length; i++) {
            DynaQLInputObjectField inputObjectField = inputObjectFields[i];
            builder.append(inputObjectField.build());
            if (i < inputObjectFields.length - 1) {
                builder.append(" ");
            }
        }
        builder.append("}");

        return builder.toString();
    }
}
