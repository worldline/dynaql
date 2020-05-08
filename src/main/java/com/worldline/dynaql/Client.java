package com.worldline.dynaql;


import com.worldline.dynaql.client.GraphQLTarget;
import com.worldline.dynaql.client.HttpTimeout;
import com.worldline.dynaql.request.Document;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

public class Client {
    private Document document;
    private final HttpTimeout connectTimeout;
    private final HttpTimeout readTimeout;
    private final Properties properties;

    protected Client(HttpTimeout connectTimeout, HttpTimeout readTimeout) {
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        this.properties = new Properties();
    }

    // Should be URI and operation
    public GraphQLTarget target(String uri) {
        URI target;

        if (uri == null || !uri.startsWith("http")) {
            throw new IllegalArgumentException("Illegal URI target value: " + uri);
        }

        try {
            target = new URI(uri);
        } catch (URISyntaxException ex) {
            throw new IllegalArgumentException("Illegal URI target value: " + uri);
        }

        return new GraphQLTarget(connectTimeout, readTimeout, target);
    }

    public void property(String key, Object value) {
        properties.put(key, value);
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}
