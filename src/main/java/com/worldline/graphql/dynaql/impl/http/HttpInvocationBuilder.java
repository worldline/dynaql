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
package com.worldline.graphql.dynaql.impl.http;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.eclipse.microprofile.graphql.client.Request;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


public class HttpInvocationBuilder {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(HttpInvocation.class);

    private final HttpConfiguration configuration;
    private URI uri;
    private Request graphqlRequest;
    private final Map<String, String> headers;

    private HttpInvocationBuilder() {
        this.headers = new HashMap<>();
        this.configuration = new HttpConfiguration();
    }

    public static HttpInvocationBuilder newBuilder() {
        return new HttpInvocationBuilder();
    }

    public HttpInvocationBuilder property(String key, Object value) {
        configuration.property(key, value);
        return this;
    }

    public HttpInvocationBuilder header(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public HttpInvocationBuilder uri(String endpoint) {

        if (endpoint == null || !endpoint.startsWith("http")) {
            throw new IllegalArgumentException("Illegal URI value: " + endpoint);
        }

        try {
            this.uri = new URI(endpoint);
        } catch (URISyntaxException ex) {
            throw new IllegalArgumentException("Illegal URI value: " + uri);
        }

        return this;
    }

    public HttpInvocationBuilder graphQLRequest(Request graphqlRequest) {
        this.graphqlRequest = graphqlRequest;
        return this;
    }

    private void setProxy(RequestConfig.Builder configBuilder) {

        String hostname = (String) configuration.get(HttpConfiguration.PROXY_HOSTNAME);
        if (hostname == null) {
            return; // No proxy configured
        }

        Integer port = (Integer) configuration.get(HttpConfiguration.PROXY_PORT);
        if (port == null || port <= 0) {
            port = HttpConfiguration.DEFAULT_HTTP_PROXY_PORT;
        }

        configBuilder.setProxy(new HttpHost(hostname, port));

    }

    private void setTimeout(RequestConfig.Builder configBuilder) {

        // Socket connection timeout
        Long connectTimeout = (Long) configuration.get(HttpConfiguration.CONNECT_TIMEOUT);

        if (connectTimeout != null) {
            log.debug(HttpConfiguration.CONNECT_TIMEOUT + "=" + connectTimeout);
            configBuilder.setConnectTimeout(connectTimeout.intValue());
        }

        // Socket read timeout
        Long readTimeout = (Long) configuration.get(HttpConfiguration.READ_TIMEOUT);

        if (readTimeout != null) {
            log.debug(HttpConfiguration.READ_TIMEOUT + "=" + readTimeout);
            configBuilder.setSocketTimeout(readTimeout.intValue());
        }

        // Connection pool/manager timeout
        Long managerTimeout = (Long) configuration.get(HttpConfiguration.CONNECTION_MANAGER_TIMEOUT);
        if (managerTimeout != null) {
            log.debug(HttpConfiguration.CONNECTION_MANAGER_TIMEOUT + "=" + managerTimeout);
            configBuilder.setConnectionRequestTimeout(managerTimeout.intValue());
        }

    }

    public HttpInvocation build() {
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        setProxy(configBuilder);
        setTimeout(configBuilder);
        
        configuration.property(HttpConfiguration.REQUEST_CONFIG, configBuilder.build());
        
        return new HttpInvocation(configuration, uri, graphqlRequest, headers);
    }

}
