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

import java.net.URI;
import java.util.Properties;

/**
 *
 * @author jefrajames
 */
public class GraphQLTarget {

    private final HttpTimeout connectTimeout;
    private final HttpTimeout readTimeout;
    private final URI uri;
    private final Properties properties;
    
    public GraphQLTarget(HttpTimeout connectTimeout, HttpTimeout readTimeout, URI uri) {
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        this.uri = uri;
        this.properties = new Properties();
    }
    
    // Generates a GraphQLInvocationBuilder
    public InvocationBuilder request(String request) {
        return new InvocationBuilder(connectTimeout, readTimeout, uri, request);
    }

    public GraphQLTarget property(String key, Object value) {
        properties.put(key, value); // WebTarget instances are mutable with respect to their configuration
        return this;
    }

}
