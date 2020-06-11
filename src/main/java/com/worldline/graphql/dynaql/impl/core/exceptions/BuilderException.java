package com.worldline.graphql.dynaql.impl.core.exceptions;

public class BuilderException extends RuntimeException {
    public BuilderException(String builder_error) {
        super(builder_error);
    }
}
