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

public interface Field extends Buildable {

    /*
        Static factory methods
    */
    @SafeVarargs
    static List<Field> fields(Field... fields) {
        return asList(fields);
    }

    // (name)
    static Field field(String name) {
        throw new StaticFactoryMethodUsedFromInterfaceException(new Throwable().getStackTrace());
    }

    // (name, subfields)
    @SafeVarargs
    static Field field(String name, Field... fields) {
        throw new StaticFactoryMethodUsedFromInterfaceException(new Throwable().getStackTrace());
    }

    // (name, args)
    @SafeVarargs
    static Field field(String name, Argument... args) {
        throw new StaticFactoryMethodUsedFromInterfaceException(new Throwable().getStackTrace());
    }

    // (name, args, subfields)
    @SafeVarargs
    static Field field(String name, List<Argument> args, Field... fields) {
        throw new StaticFactoryMethodUsedFromInterfaceException(new Throwable().getStackTrace());
    }

    /*
        Getter/Setter
    */
    String getName();

    void setName(String name);

    List<? extends Argument> getArguments();

    void setArguments(List<? extends Argument> arguments);

    List<? extends Field> getFields();

    void setFields(List<? extends Field> fields);
}
