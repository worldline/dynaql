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
package com.worldline.graphql.dynaql.response;

import java.util.List;
import java.util.Map;

/**
 * @see <a href="https://facebook.github.io/graphql/#sec-Errors">GraphQL Spec - 7.2.2 Errors</a>
 * 
 * @author jefrajames
 */
public class GraphQLError {
    
    private String message;
    private List<Map<String, Integer>> locations;
    private Object[] path;
    private Map<String, Object> extensions;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Map<String, Integer>> getLocations() {
        return locations;
    }

    public void setLocations(List<Map<String, Integer>> locations) {
        this.locations = locations;
    }

    public Object[] getPath() {
        return path;
    }

    public void setPath(Object[] path) {
        this.path = path;
    }

    public Map<String, Object> getExtensions() {
        return extensions;
    }

    public void setExtensions(Map<String, Object> extensions) {
        this.extensions = extensions;
    }
    
    @Override
    public String toString() {
        return "GraphQLError{" + "message=" + message + ", locations=" + locations + ", path=" + path + ", extensions=" + extensions + '}';
    }
    
}
