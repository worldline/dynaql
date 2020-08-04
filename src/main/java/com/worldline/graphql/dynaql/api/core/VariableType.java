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

public interface VariableType extends Buildable {
    /*
        Static factory methods
    */
    // (scalarType)
    static VariableType nonNull(ScalarType scalarType) {
        throw new StaticFactoryMethodUsedFromInterfaceException(new Throwable().getStackTrace());
    }

    // (objectType)
    static VariableType nonNull(String name) {
        throw new StaticFactoryMethodUsedFromInterfaceException(new Throwable().getStackTrace());
    }

    // (varType object)
    static VariableType nonNull(VariableType type) {
        throw new StaticFactoryMethodUsedFromInterfaceException(new Throwable().getStackTrace());
    }

    // (scalarType)
    static VariableType list(ScalarType scalarType) {
        throw new StaticFactoryMethodUsedFromInterfaceException(new Throwable().getStackTrace());
    }

    // (typeName)
    static VariableType list(String name) {
        throw new StaticFactoryMethodUsedFromInterfaceException(new Throwable().getStackTrace());
    }

    // (variableType object)
    static VariableType list(VariableType childVarType) {
        throw new StaticFactoryMethodUsedFromInterfaceException(new Throwable().getStackTrace());
    }

    /*
        Getter/Setter
     */
    String getName();

    void setName(String name);

    boolean isNonNull();

    void setNonNull(boolean nonNull);

    VariableType getChild();

    void setChild(VariableType child);

    default boolean isList() {
        return getChild() != null;
    }
}
