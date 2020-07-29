package com.worldline.graphql.dynaql.impl.core.utils;

import com.worldline.graphql.dynaql.impl.core.DynaQLEnum;
import com.worldline.graphql.dynaql.impl.core.DynaQLInputObject;
import com.worldline.graphql.dynaql.impl.core.DynaQLVariable;
import com.worldline.graphql.dynaql.impl.core.exceptions.BuilderException;

import java.lang.reflect.Array;
import java.time.LocalDate;

public class ValueFormatter {

    public static String format(Object value) throws BuilderException {
        StringBuilder builder = new StringBuilder();

        if (value == null) {
            builder.append("null");
        } else if (value instanceof DynaQLVariable) {
            DynaQLVariable var = (DynaQLVariable) value;
            builder.append("$" + var.getName());
        } else if (value instanceof DynaQLInputObject) {
            DynaQLInputObject inputObject = (DynaQLInputObject) value;
            builder.append(inputObject.build());
        } else if (value instanceof DynaQLEnum) {
            DynaQLEnum gqlEnum = (DynaQLEnum) value;
            builder.append(gqlEnum.getValue());
        } else if (value.getClass().isArray()) {
            _appendArray(builder, value);
        } else if (value instanceof String) {
            _appendAsQuotedString(builder, String.valueOf(value));
        } else if (value instanceof Character) {
            _appendAsQuotedString(builder, String.valueOf(value));
        } else if (value instanceof LocalDate) {
            _appendAsQuotedString(builder, String.valueOf(value));
        } else {
            builder.append(value);
        }

        return builder.toString();
    }

    private static void _appendArray(StringBuilder builder, Object array) throws BuilderException {
        int length = Array.getLength(array);

        builder.append("[");
        for (int i = 0; i < length; i++) {
            builder.append(format(Array.get(array, i)));
            if (i < length - 1) {
                builder.append(",");
            }
        }
        builder.append("]");
    }

    private static void _appendAsQuotedString(StringBuilder builder, String value) {
        builder.append('"');
        for (char c : value.toCharArray()) {
            switch (c) {
                case '"':
                case '\\':
                    builder.append('\\');
                    builder.append(c);
                    break;
                case '\r':
                    builder.append("\\r");
                    break;
                case '\n':
                    builder.append("\\n");
                    break;
                default:
                    if (c < 0x20) {
                        builder.append(String.format("\\u%04x", (int) c));
                    } else {
                        builder.append(c);
                    }
                    break;
            }
        }
        builder.append('"');
    }
}
