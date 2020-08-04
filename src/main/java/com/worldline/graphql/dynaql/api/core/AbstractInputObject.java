package com.worldline.graphql.dynaql.api.core;

import java.util.List;

public abstract class AbstractInputObject implements InputObject {
    private List<InputObjectField> inputObjectFields;

    /*
        Constructors
    */
    public AbstractInputObject(List<InputObjectField> inputObjectFields) {
        this.inputObjectFields = inputObjectFields;
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
