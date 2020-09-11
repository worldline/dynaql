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
package com.worldline.graphql.dynaql.test;

import com.worldline.graphql.dynaql.impl.DynaQLRequest;
import com.worldline.graphql.dynaql.impl.http.HttpConfiguration;
import com.worldline.graphql.dynaql.impl.http.HttpInvocation;
import com.worldline.graphql.dynaql.impl.http.HttpInvocationBuilder;
import com.worldline.graphql.dynaql.impl.http.HttpInvocationException;
import com.worldline.graphql.dynaql.impl.http.HttpResponse;
import com.worldline.graphql.dynaql.test.dtos.Person;
import helper.Utils;
import org.eclipse.microprofile.graphql.client.Request;
import org.eclipse.microprofile.graphql.client.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

import static helper.WireMockHelper.getWireMock;
import static helper.WireMockHelper.stubWireMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class HttpTransportTest {

    private static final Properties CONFIG = new Properties();
    private static String endpoint;

    @BeforeAll
    public static void beforeClass() throws IOException {
        CONFIG.load(HttpTransportTest.class.getClassLoader().getResourceAsStream("graphql-config.properties"));
        endpoint = CONFIG.getProperty("endpoint");

        getWireMock().start();
    }

    @AfterAll
    public static void teardown() {
        getWireMock().stop();
    }


    @Test
    public void testQueryList() throws IOException, URISyntaxException {
        Request request = new DynaQLRequest(Utils.getResourceFileContent("queries/allPeople.graphql"));
        stubWireMock("allPeople.json");

        HttpInvocation invocation = HttpInvocationBuilder
                .newBuilder()
                .uri(endpoint)
                .graphQLRequest(request)
                .build();

        HttpResponse httpResponse = invocation.invoke();

        Response response = httpResponse.getGraphQLResponse();

        assertTrue(response.hasData());
        assertFalse(response.hasError());

        List<Person> people = response.getList(Person.class, "people");
        assertEquals(10, people.size());
    }

    @Test
    public void testGraphQLErrors() throws IOException, URISyntaxException {
        Request request = new DynaQLRequest(Utils.getResourceFileContent("queries/allPeopleWithErrors.graphql"));
        stubWireMock("allPeopleWithErrors.json");

        HttpInvocation invocation = HttpInvocationBuilder
                .newBuilder()
                .uri(endpoint)
                .graphQLRequest(request)
                .build();

        HttpResponse httpResponse = invocation.invoke();

        Response response = httpResponse.getGraphQLResponse();

        assertFalse(response.hasData());
        assertTrue(response.hasError());
        assertEquals(3, response.getErrors().size());
    }

    @Test
    public void testHeader() throws IOException, URISyntaxException {
        Request request = new DynaQLRequest(Utils.getResourceFileContent("queries/personById.graphql"));
        stubWireMock("personById.json");

        HttpInvocation invocation = HttpInvocationBuilder
                .newBuilder()
                .uri(endpoint)
                .header("Authorization", "Bearer: JWT")
                .graphQLRequest(request)
                .build();

        assertEquals(invocation.getHeader("Authorization"), "Bearer: JWT");

        HttpResponse httpResponse = invocation.invoke();

        Response response = httpResponse.getGraphQLResponse();
        assertTrue(response.hasData());
        assertFalse(response.hasError());
    }

    @Test
    public void testStringVariable() throws IOException, URISyntaxException {
        Request request = new DynaQLRequest(Utils.getResourceFileContent("queries/queryWithStringVariable.graphql"));
        stubWireMock("queryWithStringVariable.json");

        request.setVariable("surname", "Zemlak");

        HttpInvocation invocation = HttpInvocationBuilder
                .newBuilder()
                .uri(endpoint)
                .graphQLRequest(request)
                .build();

        HttpResponse httpResponse = invocation.invoke();

        Response response = httpResponse.getGraphQLResponse();
        assertFalse(response.hasError());
        assertTrue(response.hasData());
    }

    @Test
    public void testIntVariable() throws IOException, URISyntaxException {
        Request request = new DynaQLRequest(Utils.getResourceFileContent("queries/queryWithIntVariable.graphql"));
        stubWireMock("queryWithIntVariable.json");

        request.setVariable("personId", "2");

        HttpInvocation invocation = HttpInvocationBuilder
                .newBuilder()
                .uri(endpoint)
                .graphQLRequest(request)
                .build();

        HttpResponse httpResponse = invocation.invoke();

        Response response = httpResponse.getGraphQLResponse();
        assertFalse(response.hasError());
        assertTrue(response.hasData());
    }

    @Test
    public void testCreatePerson() throws IOException, URISyntaxException {
        Request request = new DynaQLRequest(Utils.getResourceFileContent("queries/createPersonWithVariables.graphql"))
                .setVariable("surname", "James")
                .setVariable("names", "JF")
                .setVariable("birthDate", "27/04/1962");
        stubWireMock("createPersonWithVariables.json");

        HttpInvocation invocation = HttpInvocationBuilder
                .newBuilder()
                .uri(endpoint)
                .graphQLRequest(request)
                .build();

        HttpResponse httpResponse = invocation.invoke();
        Response response = httpResponse.getGraphQLResponse();

        assertFalse(response.hasError());
        assertTrue(response.hasData());

        Person jfj = response.getObject(Person.class, "updatePerson");
        assertEquals(jfj.getSurname(), "James");
        assertEquals(jfj.getNames()[0], "JF");
        assertEquals(jfj.getBirthDate(), LocalDate.of(1962, 4, 27));
    }

    @Test
    public void testProxyKO() throws IOException, URISyntaxException {
        Request request = new DynaQLRequest(Utils.getResourceFileContent("queries/allPeople.graphql"));
        stubWireMock("allPeople.json");

        String proxyHostname = CONFIG.getProperty(HttpConfiguration.PROXY_HOSTNAME);
        int proxyPort = Integer.valueOf(CONFIG.getProperty(HttpConfiguration.PROXY_PORT)) + 1; // Deliberate error here

        HttpInvocation invocation = HttpInvocationBuilder
                .newBuilder()
                .uri(endpoint)
                .graphQLRequest(request)
                .property(HttpConfiguration.PROXY_HOSTNAME, proxyHostname)
                .property(HttpConfiguration.PROXY_PORT, proxyPort)
                .build();

        assertEquals(invocation.getConfiguration(HttpConfiguration.PROXY_HOSTNAME), proxyHostname);
        assertEquals(invocation.getConfiguration(HttpConfiguration.PROXY_PORT), proxyPort);

        assertThrows(HttpInvocationException.class, () -> {
            request.resetVariables();
            // Variable required here!
            invocation.invoke();
        });
    }

    @Test
    public void testProxyOK() throws IOException, URISyntaxException {
        Request request = new DynaQLRequest(Utils.getResourceFileContent("queries/allPeople.graphql"));
        stubWireMock("allPeople.json");

        String proxyHostname = CONFIG.getProperty(HttpConfiguration.PROXY_HOSTNAME);
        int proxyPort = Integer.valueOf(CONFIG.getProperty(HttpConfiguration.PROXY_PORT));

        HttpInvocation invocation = HttpInvocationBuilder
                .newBuilder()
                .uri(endpoint)
                .graphQLRequest(request)
                .property(HttpConfiguration.PROXY_HOSTNAME, proxyHostname)
                .property(HttpConfiguration.PROXY_PORT, proxyPort)
                .build();

        assertEquals(invocation.getConfiguration(HttpConfiguration.PROXY_HOSTNAME), proxyHostname);
        assertEquals(invocation.getConfiguration(HttpConfiguration.PROXY_PORT), proxyPort);

        HttpResponse httpResponse = invocation.invoke();

        Response response = httpResponse.getGraphQLResponse();

        assertTrue(response.hasData());
        assertFalse(response.hasError());

        List<Person> people = response.getList(Person.class, "people");
        assertEquals(10, people.size());
    }

    @Test
    public void testTimeoutOK() throws IOException, URISyntaxException {
        Request request = new DynaQLRequest(Utils.getResourceFileContent("queries/allPeople.graphql"));
        stubWireMock("allPeople.json");

        HttpInvocation invocation = HttpInvocationBuilder
                .newBuilder()
                .uri(endpoint)
                .graphQLRequest(request)
                .property(HttpConfiguration.CONNECT_TIMEOUT, 200L)
                .property(HttpConfiguration.READ_TIMEOUT, 400L)
                .build();

        assertEquals(invocation.getConfiguration(HttpConfiguration.CONNECT_TIMEOUT), 200L);
        assertEquals(invocation.getConfiguration(HttpConfiguration.READ_TIMEOUT), 400L);

        HttpResponse httpResponse = invocation.invoke();

        Response response = httpResponse.getGraphQLResponse();
        assertTrue(response.hasData());
        assertFalse(response.hasError());
    }

    @Test
    public void testTimeoutKO() throws IOException, URISyntaxException {
        Request request = new DynaQLRequest(Utils.getResourceFileContent("queries/allPeople.graphql"));
        stubWireMock("allPeople.json");

        HttpInvocation invocation = HttpInvocationBuilder
                .newBuilder()
                .uri(endpoint)
                .graphQLRequest(request)
                .property(HttpConfiguration.CONNECT_TIMEOUT, 1L) // Unrealistic values here
                .property(HttpConfiguration.READ_TIMEOUT, 1L)
                .build();

        assertEquals(invocation.getConfiguration(HttpConfiguration.CONNECT_TIMEOUT), 1L);
        assertEquals(invocation.getConfiguration(HttpConfiguration.READ_TIMEOUT), 1L);

        assertThrows(HttpInvocationException.class, () -> {
            request.resetVariables();
            // Variable required here!
            invocation.invoke();
        });

    }
}
