package com.worldline.graphql.dynaql.impl.core;

import com.worldline.graphql.dynaql.api.core.Document;
import com.worldline.graphql.dynaql.api.core.Operation;

import java.util.List;

import static java.util.Arrays.asList;

public class DynaQLDocument implements Document {
    private List<DynaQLOperation> operations;

    /*
    Static factory methods
     */
    @SafeVarargs
    public static DynaQLDocument document(DynaQLOperation... operations) {
        return new DynaQLDocument(asList(operations));
    }

    /*
    Constructors
    */
    public DynaQLDocument(List<DynaQLOperation> operations) {
        this.operations = operations;
    }

    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();

        for (Operation operation : this.operations) {
            builder.append(operation.build());
        }

        return builder.toString();
    }

    public List<DynaQLOperation> getOperations() {
        return operations;
    }

    public void setOperations(List<? extends Operation> operations) {
        this.operations = (List<DynaQLOperation>) operations;
    }
}
