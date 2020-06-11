package com.worldline.graphql.dynaql.impl.core;

import com.worldline.graphql.dynaql.api.core.Document;

import java.util.List;

import static java.util.Arrays.asList;

public class DynaQLDocument implements Document {
    private List<DynaQLOperation> operations;

    @SafeVarargs
    public static DynaQLDocument document(DynaQLOperation... operations) {
        return new DynaQLDocument(asList(operations));
    }
    public static DynaQLDocument document(List<DynaQLOperation> operations) {
        return new DynaQLDocument(operations);
    }

    public DynaQLDocument(List<DynaQLOperation> operations) {
        this.operations = operations;
    }

    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();

        for (DynaQLOperation operation : this.operations) {
            builder.append(operation.build());
        }

        return builder.toString();
    }

    public List<DynaQLOperation> getOperations() {
        return operations;
    }

    public void setOperations(List<DynaQLOperation> operations) {
        this.operations = operations;
    }
}
