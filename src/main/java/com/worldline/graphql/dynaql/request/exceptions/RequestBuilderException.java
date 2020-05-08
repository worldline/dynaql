package com.worldline.graphql.dynaql.request.exceptions;

public class RequestBuilderException extends Exception {
    public RequestBuilderException(String builder_error) {
        super(builder_error);
    }
}
