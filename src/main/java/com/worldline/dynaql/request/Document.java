package com.worldline.dynaql.request;

import com.worldline.dynaql.request.exceptions.RequestBuilderException;

import java.util.List;

import static java.util.Arrays.asList;

public class Document implements IBuildable {
    private List<Operation> operations;
    private List<Fragment> fragments;

    @SafeVarargs
    public static Document document(Operation... operations) {
        return new Document(operations);
    }
    public static Document document(List<Operation> operations, List<Fragment> fragments) {
        return new Document(operations, fragments);
    }

    @SafeVarargs
    public Document(Operation... operations) {
        this.operations = asList(operations);
        this.fragments = asList(new Fragment[0]);
    }
    public Document(List<Operation> operations, List<Fragment> fragments) {
        this.operations = operations;
        this.fragments = fragments;
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

    public List<Fragment> getFragments() {
        return fragments;
    }

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
    }
}
