/*
 * Copyright 2020 jefrajames.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.worldline.graphql.dynaql.client;

import com.worldline.graphql.dynaql.response.Response;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jefrajames
 */
public class InvocationBuilder {

    private final HttpTimeout connectTimeout;
    private final HttpTimeout readTimeout;
    private final URI uri;
    private final String request;
    private final Map<String, String> variables; // TODO: manage non String variables
    private final Map<String, String> headers;

    public InvocationBuilder(HttpTimeout connectTimeout, HttpTimeout readTimeout, URI uri, String request) {
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        this.uri = uri;
        this.request = request;
        this.variables = new HashMap<>();
        this.headers = new HashMap<>();
    }

    
    public InvocationBuilder variable(String name, String value) {
        variables.put(name, value);
        return this;
    }
    
     public InvocationBuilder header(String name, String value) {
        headers.put(name, value);
        return this;
    }
     
     public Invocation build() {
         return new Invocation(connectTimeout, readTimeout, uri, request, variables, headers);
     }
     
     public Response invoke() {
         return new Invocation(connectTimeout, readTimeout, uri, request, variables, headers).invoke();
     }

}
