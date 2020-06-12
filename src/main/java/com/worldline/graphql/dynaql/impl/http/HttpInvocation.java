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

import com.worldline.graphql.dynaql.api.GraphQLRequest;
import com.worldline.graphql.dynaql.api.GraphQLResponse;
import com.worldline.graphql.dynaql.impl.DynaQLResponse;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.LoggerFactory;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.worldline.graphql.dynaql.impl.http.HttpConfiguration.REQUEST_CONFIG;

/**
 *
 * @author jefrajames
 */
public class HttpInvocation {

    private final HttpConfiguration configuration;
    private final URI uri;
    private final GraphQLRequest graphqlRequest;
    private final Map<String, String> headers;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(HttpInvocation.class);

    private static final int MAX_LOG_LENGTH = 128;

    protected HttpInvocation(HttpConfiguration configuration, URI uri, GraphQLRequest request, Map<String, String> headers) {
        this.configuration = configuration;
        this.uri = uri;
        this.graphqlRequest = request;
        this.headers = headers;
    }  

    private String postHttp(StringEntity stringEntity, HttpResponse httpResponse) throws IOException {

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpPost httpPost = new HttpPost(uri);

            httpPost.setConfig((RequestConfig)configuration.get(REQUEST_CONFIG));

            // Set the HTTP headers
            if (headers != null) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    httpPost.addHeader(header.getKey(), header.getValue());
                }
            }

            httpPost.setEntity(stringEntity);

            try (CloseableHttpResponse serverResponse = httpClient.execute(httpPost)) {

                Header[] headers = serverResponse.getAllHeaders();
                for (int i = 0; i < headers.length; i++) {
                    httpResponse.header(headers[i].getName(), headers[i].getValue());
                }

                InputStream contentStream = serverResponse.getEntity().getContent();

                String contentString = IOUtils.toString(contentStream, "UTF-8");
                
                if (serverResponse.getStatusLine().getStatusCode() != 200) {
                    log.warn("HTTP response code NOK " + serverResponse.getStatusLine().getStatusCode());
                    throw new HttpResponseException(serverResponse.getStatusLine().getStatusCode(), "The server responded with" + contentString);
                }
        
                log.info("Received GraphQL response: " + (contentString.length() <= MAX_LOG_LENGTH ? contentString : contentString.substring(0, MAX_LOG_LENGTH) + " etc..."));

                return contentString;
            }

        }

    }

    public HttpResponse invoke() {

        String jsonRequest = graphqlRequest.toJson();
        log.info("Sending GraphQL request: " + jsonRequest);

        StringEntity stringEntity = new StringEntity(jsonRequest, ContentType.APPLICATION_JSON);

        HttpResponse httpResponse = new HttpResponse();

        String responseBody;
        try {
            responseBody = postHttp(stringEntity, httpResponse);
        } catch (IOException ex) {
            throw new HttpInvocationException(ex);
        }

        JsonReader jsonReader = Json.createReader(new StringReader(responseBody));

        JsonObject jsonResponse = jsonReader.readObject();

        DynaQLResponse graphQLResponse = new DynaQLResponse();
        httpResponse.setGraphQLResponse(graphQLResponse);

        if (jsonResponse.containsKey("errors")) {
            log.warn("GraphQL errors element detected");
            JsonArray rawErrors = jsonResponse.getJsonArray("errors");
            Jsonb jsonb = JsonbBuilder.create();
            List<GraphQLResponse.GraphQLError> errors = jsonb.fromJson(rawErrors.toString(), new ArrayList<DynaQLResponse.DynaQLError>() {
            }.getClass().getGenericSuperclass());
            graphQLResponse.setErrors(errors);
            try {
                jsonb.close();
            } catch (Exception ignore) {
            } // Ugly!!!
        }

        if (jsonResponse.containsKey("data")) {
            if (!jsonResponse.isNull("data")) {
                JsonObject data = jsonResponse.getJsonObject("data");
                graphQLResponse.setData(data);
            } else {
                log.warn("GraphQL data element is null");
            }
        }

        return httpResponse;
    }

    public Object getConfiguration(String key) {
        return configuration.get(key);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    @Override
    public String toString() {
        return "HttpInvocation{" + "configuration=" + configuration + ", uri=" + uri + ", graphqlRequest=" + graphqlRequest + ", headers=" + headers + '}';
    }

}
