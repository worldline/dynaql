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
package com.worldline.dynaql.client;

import com.worldline.dynaql.response.GraphQLError;
import com.worldline.dynaql.response.Response;
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
import javax.json.JsonObjectBuilder;
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
import java.util.Properties;

/**
 *
 * @author jefrajames
 */
public class Invocation {

    private final HttpTimeout connectTimeout;
    private final HttpTimeout readTimeout;
    private final URI uri;
    private final String request;
    private final Properties properties;
    private final Map<String, String> headers;
    private final Map<String, String> variables; // TODO: how to manage non String variables?
    
    private static org.slf4j.Logger log = LoggerFactory.getLogger(Invocation.class);

    protected Invocation(HttpTimeout connectTimeout, HttpTimeout readTimeout, URI uri, String request, Map<String, String> variables, Map<String, String> headers) {
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        this.uri = uri;
        this.request = request;
        this.variables = variables;
        this.properties = new Properties();
        this.headers = headers;
    }

    // Classe HttpResponse qui continet header + responseBody
    private String postHttp(StringEntity stringEntity, Response graphQLResponse) throws IOException {

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpPost httpPost = new HttpPost(uri);
            httpPost.setConfig(RequestConfig.DEFAULT);

            if (headers != null) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    httpPost.addHeader(header.getKey(), header.getValue());
                }
            }

            // TODO: set the HTTP headers here
            httpPost.setEntity(stringEntity);

            try (CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {

                Header[] headers = httpResponse.getAllHeaders();
                for (int i = 0; i < headers.length; i++) {
                    graphQLResponse.addHeader(headers[i].getName(), headers[i].getValue());
                }

                InputStream contentStream = httpResponse.getEntity().getContent();

                String contentString = IOUtils.toString(contentStream, "UTF-8");
                log.info("GraphQL response=" + contentString);

                if (httpResponse.getStatusLine().getStatusCode() != 200) {
                    throw new HttpResponseException(httpResponse.getStatusLine().getStatusCode(), "The server responded with" + contentString);
                }

                return contentString;
            }

        }

    }

    private JsonObject formatJsonVariables() {
        JsonObjectBuilder varBuilder = Json.createObjectBuilder();

        variables.forEach((k, v) -> {
            varBuilder.add(k, v);
        });

        return varBuilder.build();
    }

    private String formatJsonQuery(String request) {
        return Json.createObjectBuilder().add("query", request).add("variables", formatJsonVariables()).build().toString();
    }

    public Response invoke() {

        String jsonRequest = formatJsonQuery(request);
        log.info("GraphQL request=" + jsonRequest);

        StringEntity stringEntity = new StringEntity(jsonRequest, ContentType.APPLICATION_JSON);

        Response graphQLResponse = new Response();

        String responseBody;
        try {
            responseBody = postHttp(stringEntity, graphQLResponse);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        JsonReader jsonReader = Json.createReader(new StringReader(responseBody));

        JsonObject jsonResponse = jsonReader.readObject();

        if (jsonResponse.containsKey("errors")) {
            log.warn("GraphQL errors element detected");
            JsonArray rawErrors = jsonResponse.getJsonArray("errors");
            Jsonb jsonb = JsonbBuilder.create();
            List<GraphQLError> errors = jsonb.fromJson(rawErrors.toString(), new ArrayList<GraphQLError>() {
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
                log.warn("data element is null");
            }
        }

        return graphQLResponse;
    }

}
