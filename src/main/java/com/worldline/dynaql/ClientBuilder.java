package com.worldline.dynaql;

import com.worldline.dynaql.client.HttpTimeout;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ClientBuilder {
    private HttpTimeout connectTimeout;
    private HttpTimeout readTimeout;
    private final Properties properties = new Properties();

    public static ClientBuilder newBuilder() {
        return new ClientBuilder();
    }


    public ClientBuilder connectTimeout(long value, TimeUnit timeUnit) {
        this.connectTimeout = new HttpTimeout(value, timeUnit);
        return this;
    }

    public ClientBuilder readTimeout(long value, TimeUnit timeUnit) {
        this.readTimeout = new HttpTimeout(value, timeUnit);
        return this;
    }

    // Build a Client
    public Client build() {
        return new Client(connectTimeout, readTimeout);
    }

    public HttpTimeout getConnectTimeout() {
        return connectTimeout;
    }

    public HttpTimeout getReadTimeout() {
        return readTimeout;
    }

    public ClientBuilder property(String key, Object value) {
        properties.put(key, value);
        return this;
    }
}
