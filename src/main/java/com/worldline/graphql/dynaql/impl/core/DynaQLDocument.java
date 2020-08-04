package com.worldline.graphql.dynaql.impl.core;

import com.worldline.graphql.dynaql.api.core.AbstractDocument;
import com.worldline.graphql.dynaql.api.core.Document;
import com.worldline.graphql.dynaql.api.core.Operation;

import java.util.List;

import static java.util.Arrays.asList;

public class DynaQLDocument extends AbstractDocument {

    /*
        Static factory methods
    */
    @SafeVarargs
    public static Document document(Operation... operations) {
        return new DynaQLDocument(asList(operations));
    }

    /*
        Constructors
    */
    public DynaQLDocument(List<Operation> operations) {
        super(operations);
    }

    /*
        Impl
    */
    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();

        for (Operation operation : this.getOperations()) {
            builder.append(operation.build());
        }

        return builder.toString();
    }
}
