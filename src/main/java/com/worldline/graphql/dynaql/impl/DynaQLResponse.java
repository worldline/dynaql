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

import org.eclipse.microprofile.graphql.client.Error;
import org.eclipse.microprofile.graphql.client.Response;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.util.ArrayList;
import java.util.List;

public class DynaQLResponse implements Response {
    private final JsonObject data;
    private final List<Error> errors;
    private Jsonb jsonb;

    public DynaQLResponse(JsonObject data, List<Error> errors) {
        this.data = data;
        this.errors = errors;
    }

    public <T> T getObject(Class<T> dataType, String rootField) {
        JsonObject jsonObject = data.getJsonObject(rootField);
        return getJsonb().fromJson(jsonObject.toString(), dataType);
    }

    public <T> List<T> getList(Class<T> dataType, String rootField) {
        List<T> result = new ArrayList<T>();

        Object item = data.get(rootField);
        if (item instanceof JsonObject) {
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

    public JsonObject getData() {
        return data;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public boolean hasData() {
        return data != null;
    }

    public boolean hasError() {
        return errors != null;
    }

    public String toString() {
        return "GraphQLResponse{" + "data=" + data + ", errors=" + errors + ", jsonb=" + jsonb + '}';
    }

    private Jsonb getJsonb() {
        if (jsonb == null) {
            jsonb = JsonbBuilder.create();
        }
        return jsonb;
    }
}
