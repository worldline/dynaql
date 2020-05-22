package com.worldline.graphql.dynaql.request;

import com.worldline.graphql.dynaql.request.exceptions.RequestBuilderException;

import java.util.List;

import static java.util.Arrays.asList;

public class Document implements IBuildable {
    private List<Operation> operations;

    @SafeVarargs
    public static Document document(Operation... operations) {
        return new Document(asList(operations));
    }
    public static Document document(List<Operation> operations) {
        return new Document(operations);
    }

    public Document(List<Operation> operations) {
        this.operations = operations;
    }

    @Override
    public void build(StringBuilder builder) throws RequestBuilderException {
        for (Operation operation : this.operations) {
            operation.build(builder);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        try {
            this.build(builder);
        } catch (RequestBuilderException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }
}
