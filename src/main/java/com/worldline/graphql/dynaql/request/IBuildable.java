package com.worldline.graphql.dynaql.request;

import com.worldline.graphql.dynaql.request.exceptions.RequestBuilderException;

public interface IBuildable {
    void build(StringBuilder builder) throws RequestBuilderException;
}
