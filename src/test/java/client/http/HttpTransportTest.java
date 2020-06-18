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
package client.http;

import client.dtos.Person;
import com.worldline.graphql.dynaql.api.GraphQLClientBuilder;
import com.worldline.graphql.dynaql.api.GraphQLRequest;
import com.worldline.graphql.dynaql.api.GraphQLResponse;
import com.worldline.graphql.dynaql.impl.DynaQLClientBuilder;
import com.worldline.graphql.dynaql.impl.http.HttpConfiguration;
import com.worldline.graphql.dynaql.impl.http.HttpInvocation;
import com.worldline.graphql.dynaql.impl.http.HttpInvocationBuilder;
import com.worldline.graphql.dynaql.impl.http.HttpInvocationException;
import com.worldline.graphql.dynaql.impl.http.HttpResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

import static client.WireMockHelper.getWireMock;
import static client.WireMockHelper.stubWireMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author jefrajames
 */
public class HttpTransportTest {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(HttpTransportTest.class);
    private static final Properties CONFIG = new Properties();
    private static String endpoint;
    private static GraphQLClientBuilder graphQLClientBuilder;

    @BeforeAll
    public static void beforeClass() throws IOException {
        CONFIG.load(HttpTransportTest.class.getClassLoader().getResourceAsStream("client/graphql-config.properties"));
        endpoint = CONFIG.getProperty("endpoint");
        graphQLClientBuilder = new DynaQLClientBuilder();

        getWireMock().start();
    }

    @AfterAll
    public static void teardown () {
        getWireMock().stop();
    }


    @Test
    public void testQueryList() {
        GraphQLRequest graphQLRequest = graphQLClientBuilder.newRequest(CONFIG.getProperty("allPeople"));
        stubWireMock("allPeople.json");

        HttpInvocation invocation = HttpInvocationBuilder
                .newBuilder()
                .uri(endpoint)
                .graphQLRequest(graphQLRequest)
                .build();

        HttpResponse httpResponse = invocation.invoke();

        GraphQLResponse graphQLResponse = httpResponse.getGraphQLResponse();

        assertTrue(graphQLResponse.hasData());
        assertFalse(graphQLResponse.hasError());

        List<Person> people = graphQLResponse.getList(Person.class, "people");
        assertTrue(people.size() == 10);
    }

    @Test
    public void testGraphQLErrors() throws IOException {

        GraphQLRequest graphQLRequest = graphQLClientBuilder.newRequest(CONFIG.getProperty("allPeopleWithErrors"));

        HttpInvocation invocation = HttpInvocationBuilder
                .newBuilder()
                .uri(endpoint)
                .graphQLRequest(graphQLRequest)
                .build();

        HttpResponse httpResponse = invocation.invoke();

        GraphQLResponse graphQLResponse = httpResponse.getGraphQLResponse();

        assertFalse(graphQLResponse.hasData());
        assertTrue(graphQLResponse.hasError());
        graphQLResponse.getErrors().forEach(System.out::println);
        assertEquals(graphQLResponse.getErrors().size(), 3);
    }

    @Test
    public void testHeader() throws IOException {

        GraphQLRequest graphQLRequest = graphQLClientBuilder.newRequest(CONFIG.getProperty("personById"));

        HttpInvocation invocation = HttpInvocationBuilder
                .newBuilder()
                .uri(endpoint)
                .header("Authorization", "Bearer: JWT")
                .graphQLRequest(graphQLRequest)
                .build();

        assertEquals(invocation.getHeader("Authorization"), "Bearer: JWT");

        HttpResponse httpResponse = invocation.invoke();

        GraphQLResponse graphQLResponse = httpResponse.getGraphQLResponse();
        assertTrue(graphQLResponse.hasData());
        assertFalse(graphQLResponse.hasError());
    }

    @Test
    public void testStringVariable() {

        GraphQLRequest graphQLRequest = graphQLClientBuilder.newRequest(CONFIG.getProperty("queryWithStringVariable"));
        graphQLRequest.addVariable("surname", "Grant");

        HttpInvocation invocation = HttpInvocationBuilder
                .newBuilder()
                .uri(endpoint)
                .graphQLRequest(graphQLRequest)
                .build();

        HttpResponse httpResponse = invocation.invoke();

        GraphQLResponse graphQLResponse = httpResponse.getGraphQLResponse();

        assertFalse(graphQLResponse.hasError());
        assertTrue(graphQLResponse.hasData());

        assertThrows(HttpInvocationException.class, () -> {
            graphQLRequest.resetVariables();
            // Variable required here!
            invocation.invoke();
        });
    }

    @Test
    public void testIntVariable() {

        GraphQLRequest graphQLRequest = graphQLClientBuilder.newRequest(CONFIG.getProperty("queryWithIntVariable"));
        graphQLRequest.addVariable("personId", 1);

        HttpInvocation invocation = HttpInvocationBuilder
                .newBuilder()
                .uri(endpoint)
                .graphQLRequest(graphQLRequest)
                .build();

        HttpResponse httpResponse = invocation.invoke();

        GraphQLResponse graphQLResponse = httpResponse.getGraphQLResponse();
        assertFalse(graphQLResponse.hasError());
        assertTrue(graphQLResponse.hasData());

        assertThrows(HttpInvocationException.class, () -> {
            graphQLRequest.resetVariables();
            // Variable required here!
            invocation.invoke();
        });

    }

    @Test
    public void testCreatePerson() {
        assertTrue(1 == 1);

        GraphQLRequest graphQLRequest = graphQLClientBuilder
                .newRequest(CONFIG.getProperty("createPersonWithVariables"))
                .addVariable("surname", "James")
                .addVariable("names", "JF")
                .addVariable("birthDate", "27/04/1962");

        HttpInvocation invocation = HttpInvocationBuilder
                .newBuilder()
                .uri(endpoint)
                .graphQLRequest(graphQLRequest)
                .build();

        HttpResponse httpResponse = invocation.invoke();
        GraphQLResponse graphQLResponse = httpResponse.getGraphQLResponse();

        assertFalse(graphQLResponse.hasError());
        assertTrue(graphQLResponse.hasData());

        Person jfj = graphQLResponse.getObject(Person.class, "updatePerson");
        assertEquals(jfj.getSurname(), "James");
        assertEquals(jfj.getNames()[0], "JF");
        assertEquals(jfj.getBirthDate(), LocalDate.of(1962, 4, 27));

    }

    @Test
    public void testProxyKO() {

        GraphQLRequest graphQLRequest = graphQLClientBuilder.newRequest(CONFIG.getProperty("allPeople"));
        String proxyHostname = CONFIG.getProperty(HttpConfiguration.PROXY_HOSTNAME);
        int proxyPort = Integer.valueOf(CONFIG.getProperty(HttpConfiguration.PROXY_PORT)) + 1; // Deliberate error here

        HttpInvocation invocation = HttpInvocationBuilder
                .newBuilder()
                .uri(endpoint)
                .graphQLRequest(graphQLRequest)
                .property(HttpConfiguration.PROXY_HOSTNAME, proxyHostname)
                .property(HttpConfiguration.PROXY_PORT, proxyPort)
                .build();

        assertEquals(invocation.getConfiguration(HttpConfiguration.PROXY_HOSTNAME), proxyHostname);
        assertEquals(invocation.getConfiguration(HttpConfiguration.PROXY_PORT), proxyPort);

        assertThrows(HttpInvocationException.class, () -> {
            graphQLRequest.resetVariables();
            // Variable required here!
            invocation.invoke();
        });
    }

    @Test
    public void testProxyOK() {

        GraphQLRequest graphQLRequest = graphQLClientBuilder.newRequest(CONFIG.getProperty("allPeople"));
        String proxyHostname = CONFIG.getProperty(HttpConfiguration.PROXY_HOSTNAME);
        int proxyPort = Integer.valueOf(CONFIG.getProperty(HttpConfiguration.PROXY_PORT));

        HttpInvocation invocation = HttpInvocationBuilder
                .newBuilder()
                .uri(endpoint)
                .graphQLRequest(graphQLRequest)
                .property(HttpConfiguration.PROXY_HOSTNAME, proxyHostname)
                .property(HttpConfiguration.PROXY_PORT, proxyPort)
                .build();

        assertEquals(invocation.getConfiguration(HttpConfiguration.PROXY_HOSTNAME), proxyHostname);
        assertEquals(invocation.getConfiguration(HttpConfiguration.PROXY_PORT), proxyPort);

        HttpResponse httpResponse = invocation.invoke();

        GraphQLResponse graphQLResponse = httpResponse.getGraphQLResponse();

        assertTrue(graphQLResponse.hasData());
        assertFalse(graphQLResponse.hasError());

        List<Person> people = graphQLResponse.getList(Person.class, "people");
        assertTrue(people.size() >= 100);
    }

    @Test
    public void testTimeoutOK() {

        GraphQLRequest graphQLRequest = graphQLClientBuilder.newRequest(CONFIG.getProperty("allPeople"));

        HttpInvocation invocation = HttpInvocationBuilder
                .newBuilder()
                .uri(endpoint)
                .graphQLRequest(graphQLRequest)
                .property(HttpConfiguration.CONNECT_TIMEOUT, 200L)
                .property(HttpConfiguration.READ_TIMEOUT, 400L)
                .build();

        assertEquals(invocation.getConfiguration(HttpConfiguration.CONNECT_TIMEOUT), 200L);
        assertEquals(invocation.getConfiguration(HttpConfiguration.READ_TIMEOUT), 400L);

        HttpResponse httpResponse = invocation.invoke();

        GraphQLResponse graphQLResponse = httpResponse.getGraphQLResponse();
        assertTrue(graphQLResponse.hasData());
        assertFalse(graphQLResponse.hasError());

    }

    @Test
    public void testTimeoutKO() {

        GraphQLRequest graphQLRequest = graphQLClientBuilder.newRequest(CONFIG.getProperty("allPeople"));

        HttpInvocation invocation = HttpInvocationBuilder
                .newBuilder()
                .uri(endpoint)
                .graphQLRequest(graphQLRequest)
                .property(HttpConfiguration.CONNECT_TIMEOUT, 1L) // Unrealistic values here
                .property(HttpConfiguration.READ_TIMEOUT, 1L)
                .build();

        assertEquals(invocation.getConfiguration(HttpConfiguration.CONNECT_TIMEOUT), 1L);
        assertEquals(invocation.getConfiguration(HttpConfiguration.READ_TIMEOUT), 1L);

        assertThrows(HttpInvocationException.class, () -> {
            graphQLRequest.resetVariables();
            // Variable required here!
            invocation.invoke();
        });

    }

}
