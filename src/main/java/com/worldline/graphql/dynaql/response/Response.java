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

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jefrajames
 */
public class Response {

    private Map<String, String> headers = new HashMap<>();

    private JsonObject data;
    private List<GraphQLError> errors;
    private Jsonb jsonb;


    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }

    public JsonObject getData() {
        return data;
    }

    public List<GraphQLError> getErrors() {
        return errors;
    }

    public void setErrors(List<GraphQLError> errors) {
        this.errors = errors;
    }

    public boolean hasData() {
        return data != null;
    }

    public boolean hasError() {
        return errors != null;
    }
    
    private Jsonb getJsonb() {
        if ( jsonb==null ) {
            jsonb = JsonbBuilder.create();
        }
        return jsonb;
    }

    public <T> T asDataObject(Class<T> dataType, String rootField) {
        JsonObject jsonObject = data.getJsonObject(rootField);
        return getJsonb().fromJson(jsonObject.toString(), dataType);
    }

    public <T> List<T> asDataList(Class<T> dataType, String rootField) {

        JsonArray jsonArray = data.getJsonArray(rootField);
        List<T> result = new ArrayList<T>();
        
        jsonArray.forEach(o -> {result.add(getJsonb().fromJson(o.toString(), dataType));});
        
        return result;
    }
}
