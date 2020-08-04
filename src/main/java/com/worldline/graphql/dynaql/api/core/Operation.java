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

import com.worldline.graphql.dynaql.api.core.exceptions.StaticFactoryMethodUsedFromInterfaceException;

import java.util.List;

import static java.util.Arrays.asList;

public interface Operation extends Buildable {

    /*
        Static factory methods
    */
    @SafeVarargs
    static List<Operation> operations(Operation... operations) {
        return asList(operations);
    }

    // (fields)
    @SafeVarargs
    static Operation operation(Field... fields) {
        throw new StaticFactoryMethodUsedFromInterfaceException(new Throwable().getStackTrace());
    }

    // (vars, fields)
    @SafeVarargs
    static Operation operation(List<Variable> vars, Field... fields) {
        throw new StaticFactoryMethodUsedFromInterfaceException(new Throwable().getStackTrace());
    }

    // (type, fields)
    @SafeVarargs
    static Operation operation(OperationType type, Field... fields) {
        throw new StaticFactoryMethodUsedFromInterfaceException(new Throwable().getStackTrace());
    }

    // (type, vars, fields)
    @SafeVarargs
    static Operation operation(OperationType type, List<Variable> vars, Field... fields) {
        throw new StaticFactoryMethodUsedFromInterfaceException(new Throwable().getStackTrace());
    }

    // (name, fields)
    @SafeVarargs
    static Operation operation(String name, Field... fields) {
        throw new StaticFactoryMethodUsedFromInterfaceException(new Throwable().getStackTrace());
    }

    // (type, name, fields)
    @SafeVarargs
    static Operation operation(OperationType type, String name, Field... fields) {
        throw new StaticFactoryMethodUsedFromInterfaceException(new Throwable().getStackTrace());
    }

    // (name, vars, fields)
    @SafeVarargs
    static Operation operation(String name, List<Variable> vars, Field... fields) {
        throw new StaticFactoryMethodUsedFromInterfaceException(new Throwable().getStackTrace());
    }

    // (type, name, vars, fields)
    @SafeVarargs
    static Operation operation(OperationType type, String name, List<Variable> vars, Field... fields) {
        throw new StaticFactoryMethodUsedFromInterfaceException(new Throwable().getStackTrace());
    }

    /*
        Getter/Setter
    */
    OperationType getType();

    void setType(OperationType type);

    String getName();

    void setName(String name);

    List<Variable> getVariables();

    void setVariables(List<Variable> vars);

    List<Field> getFields();

    void setFields(List<Field> fields);
}
