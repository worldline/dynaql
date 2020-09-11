package com.worldline.graphql.dynaql.impl;

import org.eclipse.microprofile.graphql.client.Error;

import java.util.List;
import java.util.Map;

public class DynaQLError implements Error {
    private String message;
    private List<Map<String, Integer>> locations;
    private Object[] path;
    private Map<String, Object> extensions;

    public DynaQLError() {
        // Used by JsonB
    }

    public DynaQLError(String message, List<Map<String, Integer>> locations, Object[] path, Map<String, Object> extensions) {
        this.message = message;
        this.locations = locations;
        this.path = path;
        this.extensions = extensions;
    }

    public String getMessage() {
        return message;
    }

    public List<Map<String, Integer>> getLocations() {
        return locations;
    }

    public Object[] getPath() {
        return path;
    }

    public Map<String, Object> getExtensions() {
        return extensions;
    }

    @Override
    public String toString() {
        return "GraphQLError{" + "message=" + message + ", locations=" + locations + ", path=" + path + ", extensions=" + extensions + '}';
    }
}
