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
package com.worldline.graphql.dynaql.api.core.exceptions;

public class StaticFactoryMethodUsedFromInterfaceException extends RuntimeException {

    public StaticFactoryMethodUsedFromInterfaceException(StackTraceElement[] stackTrace) {
        super("Static factory method `" + stackTrace[0].getMethodName() + "(...)` from `"
                + stackTrace[0].getClassName() + "` interface cannot be called directly." +
                " It must be overridden in an implementing class and imported from there.");
    }
}
