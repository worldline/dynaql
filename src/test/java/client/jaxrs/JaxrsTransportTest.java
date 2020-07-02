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
package client.jaxrs;

import client.dtos.Person;
import client.dtos.Profile;
import com.worldline.graphql.dynaql.api.GraphQLClientBuilder;
import com.worldline.graphql.dynaql.api.GraphQLRequest;
import com.worldline.graphql.dynaql.api.GraphQLResponse;
import com.worldline.graphql.dynaql.impl.DynaQLClientBuilder;
import com.worldline.graphql.dynaql.impl.jaxrs.GraphQLRequestWriter;
import com.worldline.graphql.dynaql.impl.jaxrs.GraphQLResponseReader;
import helper.Utils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.json.JsonObject;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import static client.WireMockHelper.getWireMock;
import static client.WireMockHelper.stubWireMock;
import static javax.ws.rs.client.Entity.json;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class JaxrsTransportTest {

    private static final Properties CONFIG = new Properties();
    private static String endpoint;
    private static ClientBuilder clientBuilder;
    private static GraphQLClientBuilder graphQLClientBuilder;

    @BeforeAll
    public static void beforeClass() throws IOException {
        CONFIG.load(JaxrsTransportTest.class.getClassLoader().getResourceAsStream("client/graphql-config.properties"));
        endpoint = CONFIG.getProperty("endpoint");
        clientBuilder = ClientBuilder
                .newBuilder()
                .register(GraphQLResponseReader.class)
                .register(GraphQLRequestWriter.class)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS);
        graphQLClientBuilder = new DynaQLClientBuilder();

        getWireMock().start();
    }

    @AfterAll
    public static void teardown() {
        getWireMock().stop();
    }

    @Test
    public void testQueryList() throws IOException, URISyntaxException {
        GraphQLRequest graphQLRequest = graphQLClientBuilder.newRequest(Utils.getResourceFileContent("client/allPeople.graphql"));
        stubWireMock("allPeople.json");

        Client client = clientBuilder.build();

        WebTarget target = client.target(endpoint);

        Response response = target.request(MediaType.APPLICATION_JSON).post(json(graphQLRequest));

        assertEquals(response.getStatus(), 200);

        GraphQLResponse graphQLResponse = response.readEntity(GraphQLResponse.class);
        assertTrue(graphQLResponse.hasData());
        assertFalse(graphQLResponse.hasError());

        List<Person> people = graphQLResponse.getList(Person.class, "people");
        assertEquals(10, people.size());

        client.close();
    }

    @Test
    public void testGraphQLErrors() throws IOException, URISyntaxException {
        GraphQLRequest graphQLRequest = graphQLClientBuilder.newRequest(Utils.getResourceFileContent("client/allPeopleWithErrors.graphql"));
        stubWireMock("allPeopleWithErrors.json");

        Client client = clientBuilder.build();

        Response response = client
                .target(endpoint)
                .request(MediaType.APPLICATION_JSON)
                // Same as .post(json(graphQLRequest))
                .post(Entity.entity(graphQLRequest, MediaType.APPLICATION_JSON));

        assertEquals(response.getStatus(), 200);

        GraphQLResponse graphQLResponse = response.readEntity(GraphQLResponse.class);

        assertFalse(graphQLResponse.hasData());
        assertTrue(graphQLResponse.hasError());
        assertEquals(graphQLResponse.getErrors().size(), 3);

        client.close();
    }

    @Test
    public void testHeader() throws IOException, URISyntaxException {
        GraphQLRequest graphQLRequest = graphQLClientBuilder.newRequest(Utils.getResourceFileContent("client/personById.graphql"));
        stubWireMock("personById.json");

        Client client = clientBuilder.build();

        Response response = client
                .target(endpoint)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer: JWT")
                .post(json(graphQLRequest));

        assertEquals(response.getStatus(), 200);

        GraphQLResponse graphQLResponse = response.readEntity(GraphQLResponse.class);

        assertTrue(graphQLResponse.hasData());
        assertFalse(graphQLResponse.hasError());

        client.close();
    }

    @Test
    public void testStringVariable() throws IOException, URISyntaxException {
        GraphQLRequest graphQLRequest = graphQLClientBuilder.newRequest(Utils.getResourceFileContent("client/queryWithStringVariable.graphql"));
        stubWireMock("queryWithStringVariable.json");

        graphQLRequest.addVariable("surname", "Zemlak");

        Client client = clientBuilder.build();

        // Here, we directly get a GraphQLResponse typed entity
        GraphQLResponse graphQLResponse = client
                .target(endpoint)
                .request(MediaType.APPLICATION_JSON)
                .post(json(graphQLRequest), GraphQLResponse.class);

        assertFalse(graphQLResponse.hasError());
        assertTrue(graphQLResponse.hasData());

        List<Person> people = graphQLResponse.getList(Person.class, "personsWithSurname");

        client.close();
    }

    @Test
    public void testIntVariable() throws IOException, URISyntaxException {
        GraphQLRequest graphQLRequest = graphQLClientBuilder.newRequest(Utils.getResourceFileContent("client/queryWithIntVariable.graphql"));
        stubWireMock("queryWithIntVariable.json");

        graphQLRequest.addVariable("personId", "2");

        Client client = clientBuilder.build();

        Response response = client
                .target(endpoint)
                .request() // Here we don't specify that we expect application/json content
                .post(json(graphQLRequest));

        assertEquals(response.getStatus(), 200);

        GraphQLResponse graphQLResponse = response
                .readEntity(GraphQLResponse.class);

        assertFalse(graphQLResponse.hasError());
        assertTrue(graphQLResponse.hasData());

        JsonObject myData = graphQLResponse.getData();

        Profile profile = graphQLResponse.getObject(Profile.class, "profile");
        assertFalse(graphQLResponse.hasError());
        assertTrue(graphQLResponse.hasData());

        assertEquals(2, profile.getPerson().getId());

        List<Profile> resultAsList = graphQLResponse.getList(Profile.class, "profile");
        assertEquals(resultAsList.size(), 1);
        assertEquals(2, resultAsList.get(0).getPerson().getId());

        client.close();
    }

    @Test
    public void testCreatePerson() throws IOException, URISyntaxException {
        GraphQLRequest graphQLRequest = graphQLClientBuilder
                .newRequest(Utils.getResourceFileContent("client/createPersonWithVariables.graphql"))
                .addVariable("surname", "James")
                .addVariable("names", "JF")
                .addVariable("birthDate", "27/04/1962");
        stubWireMock("createPersonWithVariables.json");

        Client client = clientBuilder.build();

        Response response = client
                .target(endpoint)
                .request(MediaType.APPLICATION_JSON) // Here we don't specify that we expect application/json content
                .post(json(graphQLRequest));

        assertEquals(response.getStatus(), 200);

        GraphQLResponse graphQLResponse = response
                .readEntity(GraphQLResponse.class);
        assertFalse(graphQLResponse.hasError());
        assertTrue(graphQLResponse.hasData());

        Person jfj = graphQLResponse.getObject(Person.class, "updatePerson");
        assertEquals(jfj.getSurname(), "James");
        assertEquals(jfj.getNames()[0], "JF");
        assertEquals(jfj.getBirthDate(), LocalDate.of(1962, 4, 27));

        client.close();
    }

    // No Proxy test here: not JAX-RS standard!
    @Test
    public void testTimeoutOK() throws IOException, URISyntaxException {
        GraphQLRequest graphQLRequest = graphQLClientBuilder.newRequest(Utils.getResourceFileContent("client/allPeople.graphql"));
        stubWireMock("allPeople.json");

        Client client = clientBuilder
                .connectTimeout(200, TimeUnit.MILLISECONDS)
                .readTimeout(400, TimeUnit.MILLISECONDS)
                .build();

        Response response = client
                .target(endpoint)
                .request()
                .post(json(graphQLRequest));

        GraphQLResponse graphQLResponse = response.readEntity(GraphQLResponse.class);

        assertTrue(graphQLResponse.hasData());
        assertFalse(graphQLResponse.hasError());

        client.close();
    }

    @Test
    public void testTimeoutKO() throws IOException, URISyntaxException {
        GraphQLRequest graphQLRequest = graphQLClientBuilder.newRequest(Utils.getResourceFileContent("client/allPeople.graphql"));
        stubWireMock("allPeople.json");

        Client client = clientBuilder
                .connectTimeout(1, TimeUnit.MILLISECONDS) // Unrealistic values here!
                .readTimeout(1, TimeUnit.MILLISECONDS)
                .build();

        assertThrows(ProcessingException.class, () -> {
            client
                    .target(endpoint)
                    .request()
                    .post(json(graphQLRequest));
        });

        client.close();
    }

    @Test
    public void testMissingVariable() throws IOException, URISyntaxException {
        GraphQLRequest graphQLRequest = graphQLClientBuilder.newRequest(Utils.getResourceFileContent("client/queryWithStringVariable.graphql"));
        stubWireMock("queryWithMissingVariable.json");

        Client client = clientBuilder.build();

        Response response = client
                .target(endpoint)
                .request(MediaType.APPLICATION_JSON)
                .post(json(graphQLRequest.toJson()));

        GraphQLResponse graphQLResponse = response
                .readEntity(GraphQLResponse.class);

        assertFalse(graphQLResponse.hasData());
        assertTrue(graphQLResponse.hasError());
        assertEquals(200, response.getStatus());

        client.close();
    }

    @Test
    public void testReactiveCall() throws InterruptedException, IOException, URISyntaxException {
        GraphQLRequest graphQLRequest = graphQLClientBuilder.newRequest(Utils.getResourceFileContent("client/allPeople.graphql"));
        stubWireMock("allPeople.json");

        Client client = clientBuilder.build();

        CompletionStage<GraphQLResponse> csr = client
                .target(endpoint)
                .request()
                .rx()
                .post(json(graphQLRequest.toJson()), GraphQLResponse.class);

        Thread.sleep(2000);

        csr.thenAccept(r -> {
            assertTrue(r.hasData());
            assertFalse(r.hasError());
        });

    }

}
