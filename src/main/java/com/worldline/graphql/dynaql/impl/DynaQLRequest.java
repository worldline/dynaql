package com.worldline.graphql.dynaql.impl;

import com.worldline.graphql.dynaql.api.GraphQLRequest;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 
 * @author jefrajames
 */
public class DynaQLRequest implements GraphQLRequest {

    private final String request;
    private Map<String, Object> variables;

    protected DynaQLRequest(String request) {
        this.request = request;
    }


    public String getRequest() {
        return request;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    @Override
    public DynaQLRequest addVariable(String name, Object value) {
        if ( variables==null )
            variables = new HashMap<>();
        
        variables.put(name, value);
        return this;
    }
    
    @Override
     public DynaQLRequest resetVariables() {
         variables = null;
         return this;
     }

    @Override
    public String toString() {
        return "GraphQLRequest{" + "request=" + request + ", variables=" + variables + '}';
    }

    private JsonObject formatJsonVariables() {
        JsonObjectBuilder varBuilder = Json.createObjectBuilder();

        variables.forEach((k, v) -> {
            // Other types to process here
            if (v instanceof String) {
                varBuilder.add(k, (String) v);
            } else if (v instanceof Integer) {
                varBuilder.add(k, (Integer) v);
            }
        });

        return varBuilder.build();
    }

    @Override
    public String toJson() {
        JsonObjectBuilder queryBuilder = Json.createObjectBuilder().add("query", request);
        if (variables!=null) {
            queryBuilder.add("variables", formatJsonVariables());
        }

        return queryBuilder.build().toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DynaQLRequest other = (DynaQLRequest) obj;
        if (!Objects.equals(this.request, other.request)) {
            return false;
        }
        if (!Objects.equals(this.variables, other.variables)) {
            return false;
        }
        return true;
    }
}
