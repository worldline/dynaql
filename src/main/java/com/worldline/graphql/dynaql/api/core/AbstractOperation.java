/*
 * Copyright (c) 2020 Contributors to the Eclipse Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.worldline.graphql.dynaql.api.core;

import java.util.List;

public abstract class AbstractOperation implements Operation {
    private OperationType type;
    private String name;
    private List<? extends Variable> variables;
    private List<? extends Field> fields;

    /*
        Constructors
    */
    public AbstractOperation(OperationType type, String name, List<? extends Variable> vars, List<? extends Field> fields) {
        this.type = type;
        this.name = name;
        this.variables = vars;
        this.fields = fields;
    }

    /*
        Getter/Setter
    */
    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<? extends Variable> getVariables() {
        return variables;
    }

    public void setVariables(List<? extends Variable> vars) {
        this.variables = vars;
    }

    public List<? extends Field> getFields() {
        return fields;
    }

    public void setFields(List<? extends Field> fields) {
        this.fields = fields;
    }
}
