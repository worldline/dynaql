package com.worldline.dynaql.request;


import com.worldline.dynaql.request.exceptions.RequestBuilderException;

import static com.worldline.dynaql.request.util.ValueFormatter.format;

public class InputObjectField implements IBuildable {
    private String name;
    private Object value;

    public static InputObjectField prop(String name, Object value) {
        return new InputObjectField(name, value);
    }

    public InputObjectField(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public void build(StringBuilder builder) throws RequestBuilderException {
        builder.append(this.name);
        builder.append(":");
        builder.append(format(this.value));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
