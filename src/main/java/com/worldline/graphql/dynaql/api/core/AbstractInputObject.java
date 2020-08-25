package com.worldline.graphql.dynaql.api.core;

import org.eclipse.microprofile.graphql.client.core.InputObject;
import org.eclipse.microprofile.graphql.client.core.InputObjectField;

import java.util.List;

public abstract class AbstractInputObject implements InputObject {
    private List<InputObjectField> inputObjectFields;

    /*
        Constructors
    */
    public AbstractInputObject() {
    }

    /*
        Getter/Setter
     */
    public List<InputObjectField> getInputObjectFields() {
        return inputObjectFields;
    }

    public void setInputObjectFields(List<InputObjectField> inputObjectFields) {
        this.inputObjectFields = inputObjectFields;
    }
}
