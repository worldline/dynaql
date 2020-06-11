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
package com.worldline.graphql.dynaql.impl;

import com.worldline.graphql.dynaql.api.Response;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jefrajames
 */
public class DynaQLResponse implements Response {

    private JsonObject data;
    private List<GraphQLError> errors;
    private Jsonb jsonb;

    public void setData(JsonObject data) {
        this.data = data;
    }

    @Override
    public JsonObject getData() {
        return data;
    }

    @Override
    public List<GraphQLError> getErrors() {
        return errors;
    }

    public void setErrors(List<GraphQLError> errors) {
        this.errors = errors;
    }

    @Override
    public boolean hasData() {
        return data != null;
    }

    @Override
    public boolean hasError() {
        return errors != null;
    }

    private Jsonb getJsonb() {
        if (jsonb == null) {
            jsonb = JsonbBuilder.create();
        }
        return jsonb;
    }

    
    @Override
    public <T> T getObject(Class<T> dataType, String rootField) {
        JsonObject jsonObject = data.getJsonObject(rootField);
        return getJsonb().fromJson(jsonObject.toString(), dataType);
    }

    
    @Override
    public <T> List<T> getList(Class<T> dataType, String rootField) {
        
        List<T> result = new ArrayList<T>();
        
        Object item = data.get(rootField);
        if ( item instanceof JsonObject ) {
            // A single Object can be returned as a mono-element List
            result.add(getObject(dataType, rootField));
            return result;
        }

        JsonArray jsonArray = (JsonArray) item;
        
        jsonArray.forEach(o -> {
            result.add(getJsonb().fromJson(o.toString(), dataType));
        });

        return result;
    }

    @Override
    public String toString() {
        return "GraphQLResponse{" + "data=" + data + ", errors=" + errors + ", jsonb=" + jsonb + '}';
    }

    public static class DynaQLError implements GraphQLError {

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
}
