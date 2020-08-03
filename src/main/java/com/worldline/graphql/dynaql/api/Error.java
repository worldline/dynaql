package com.worldline.graphql.dynaql.api;

import java.util.List;
import java.util.Map;

public interface Error {

    String getMessage();
    List<Map<String, Integer>> getLocations();

    Object[] getPath();
    Map<String, Object> getExtensions();
}
