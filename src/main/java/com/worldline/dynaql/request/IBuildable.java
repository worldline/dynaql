package com.worldline.dynaql.request;

import com.worldline.dynaql.request.exceptions.RequestBuilderException;

public interface IBuildable {
    void build(StringBuilder builder) throws RequestBuilderException;
}
