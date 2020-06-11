package com.worldline.graphql.dynaql.request;

import java.util.List;

import static java.util.Arrays.asList;

public interface IDocument {
    @SafeVarargs
    public static Document document(Operation... operations);
    public static Document document(List<Operation> operations);

    public Document(List<Operation> operations);

}
