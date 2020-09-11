package com.worldline.graphql.dynaql.test;

import com.worldline.graphql.dynaql.impl.DynaQLRequest;
import com.worldline.graphql.dynaql.impl.jaxrs.GraphQLRequestWriter;
import com.worldline.graphql.dynaql.impl.jaxrs.GraphQLResponseReader;
import com.worldline.graphql.dynaql.test.dtos.Hero;
import org.eclipse.microprofile.graphql.client.Request;
import org.eclipse.microprofile.graphql.client.Response;
import org.eclipse.microprofile.graphql.client.core.Document;
import org.eclipse.microprofile.graphql.client.core.Variable;
import org.eclipse.microprofile.graphql.client.tck.helper.Utils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static helper.WireMockHelper.getWireMock;
import static helper.WireMockHelper.stubWireMock;
import static javax.ws.rs.client.Entity.json;
import static org.eclipse.microprofile.graphql.client.core.Argument.arg;
import static org.eclipse.microprofile.graphql.client.core.Argument.args;
import static org.eclipse.microprofile.graphql.client.core.Document.document;
import static org.eclipse.microprofile.graphql.client.core.Field.field;
import static org.eclipse.microprofile.graphql.client.core.Operation.operation;
import static org.eclipse.microprofile.graphql.client.core.ScalarType.GQL_STRING;
import static org.eclipse.microprofile.graphql.client.core.Variable.var;
import static org.eclipse.microprofile.graphql.client.core.Variable.vars;
import static org.eclipse.microprofile.graphql.client.core.VariableType.nonNull;
import static org.eclipse.microprofile.graphql.client.tck.helper.AssertGraphQL.assertEquivalentGraphQLRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class E2ETest {

    private static final Properties CONFIG = new Properties();
    private static String endpoint;
    private static javax.ws.rs.client.ClientBuilder jaxrsClientBuilder;

    @BeforeAll
    public static void beforeClass() throws IOException {
        CONFIG.load(JaxrsTransportTest.class.getClassLoader().getResourceAsStream("graphql-config.properties"));
        endpoint = CONFIG.getProperty("endpoint");
        jaxrsClientBuilder = javax.ws.rs.client.ClientBuilder
                .newBuilder()
                .register(GraphQLResponseReader.class)
                .register(GraphQLRequestWriter.class)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS);

        getWireMock().start();
    }

    @AfterAll
    public static void teardown() {
        getWireMock().stop();
    }

    @Test
    // Correctly identifies R2-D2 as the hero of the Star Wars Saga
    public void basicQueries_1_Test() throws IOException, URISyntaxException {
        Document gqlDoc = document(
                operation("HeroNameQuery",
                        field("hero",
                                field("name"))));
        String document = gqlDoc.build();
        assertEquivalentGraphQLRequest(Utils.getResourceFileContent("queries/e2e/basicQuery_1.graphql"), document);

        stubWireMock("e2e/basicQuery_1.json");
        Client client = jaxrsClientBuilder.build();
        WebTarget target = client.target(endpoint);
        Request request = new DynaQLRequest(document);
        javax.ws.rs.core.Response response = target.request(MediaType.APPLICATION_JSON).post(json(request));
        assertEquals(200, response.getStatus());

        Response graphQLResponse = response.readEntity(Response.class);
        assertTrue(graphQLResponse.hasData());
        assertFalse(graphQLResponse.hasError());

        Hero hero = graphQLResponse.getObject(Hero.class, "hero");
        assertEquals("R2-D2", hero.getName());

        client.close();
    }

    @Test
    // Allows us to query for the ID and friends of R2-D2
    public void basicQueries_2_Test() throws IOException, URISyntaxException {
        Document gqlDoc = document(
                operation("HeroNameAndFriendsQuery",
                        field("hero",
                                field("id"),
                                field("name"),
                                field("friends",
                                        field("name"))))
        );
        String document = gqlDoc.build();
        assertEquivalentGraphQLRequest(Utils.getResourceFileContent("queries/e2e/basicQuery_2.graphql"), document);

        stubWireMock("e2e/basicQuery_2.json");
        Client client = jaxrsClientBuilder.build();
        WebTarget target = client.target(endpoint);
        Request request = new DynaQLRequest(document);
        javax.ws.rs.core.Response response = target.request(MediaType.APPLICATION_JSON).post(json(request));
        assertEquals(200, response.getStatus());

        Response graphQLResponse = response.readEntity(Response.class);
        assertTrue(graphQLResponse.hasData());
        assertFalse(graphQLResponse.hasError());

        Hero hero = graphQLResponse.getObject(Hero.class, "hero");
        assertEquals("2001", hero.getId());
        assertEquals("R2-D2", hero.getName());
        assertEquals("Luke Skywalker", hero.getFriends().get(0).getName());
        assertEquals("Han Solo", hero.getFriends().get(1).getName());
        assertEquals("Leia Organa", hero.getFriends().get(2).getName());

        client.close();
    }

    @Test
    // Allows us to query for the friends of friends of R2-D2
    public void nestedQueries_1_Test() throws IOException, URISyntaxException {
        Document gqlDoc = document(
                operation("NestedQuery",
                        field("hero",
                                field("name"),
                                field("friends",
                                        field("name"),
                                        field("appearsIn"),
                                        field("friends",
                                                field("name")))))
        );
        String document = gqlDoc.build();
        assertEquivalentGraphQLRequest(Utils.getResourceFileContent("queries/e2e/nestedQuery_1.graphql"), document);

        stubWireMock("e2e/nestedQuery_1.json");
        Client client = jaxrsClientBuilder.build();
        WebTarget target = client.target(endpoint);
        Request request = new DynaQLRequest(document);
        javax.ws.rs.core.Response response = target.request(MediaType.APPLICATION_JSON).post(json(request));
        assertEquals(200, response.getStatus());

        Response graphQLResponse = response.readEntity(Response.class);
        assertTrue(graphQLResponse.hasData());
        assertFalse(graphQLResponse.hasError());

        Hero hero = graphQLResponse.getObject(Hero.class, "hero");
        assertEquals("R2-D2", hero.getName());

        assertEquals("Luke Skywalker", hero.getFriends().get(0).getName());
        assertEquals(Arrays.asList("NEW_HOPE", "EMPIRE", "JEDI"), hero.getFriends().get(0).getMovies());
        assertEquals("Han Solo", hero.getFriends().get(0).getFriends().get(0).getName());
        assertEquals("Leia Organa", hero.getFriends().get(0).getFriends().get(1).getName());
        assertEquals("C-3PO", hero.getFriends().get(0).getFriends().get(2).getName());
        assertEquals("R2-D2", hero.getFriends().get(0).getFriends().get(3).getName());

        assertEquals("Han Solo", hero.getFriends().get(1).getName());
        assertEquals(Arrays.asList("NEW_HOPE", "EMPIRE", "JEDI"), hero.getFriends().get(1).getMovies());
        assertEquals("Luke Skywalker", hero.getFriends().get(1).getFriends().get(0).getName());
        assertEquals("Leia Organa", hero.getFriends().get(1).getFriends().get(1).getName());
        assertEquals("R2-D2", hero.getFriends().get(1).getFriends().get(2).getName());

        assertEquals("Leia Organa", hero.getFriends().get(2).getName());
        assertEquals(Arrays.asList("NEW_HOPE", "EMPIRE", "JEDI"), hero.getFriends().get(2).getMovies());
        assertEquals("Luke Skywalker", hero.getFriends().get(2).getFriends().get(0).getName());
        assertEquals("Han Solo", hero.getFriends().get(2).getFriends().get(1).getName());
        assertEquals("C-3PO", hero.getFriends().get(2).getFriends().get(2).getName());
        assertEquals("R2-D2", hero.getFriends().get(2).getFriends().get(3).getName());

        client.close();
    }

    @Test
    // Allows us to query characters directly, using their IDs
    public void queryWithArgs_1_Test() throws IOException, URISyntaxException {
        Document gqlDoc = document(
                operation("FetchLukeAndC3POQuery",
                        field("human", args(arg("id", "1000")),
                                field("name")),
                        field("droid", args(arg("id", "2000")),
                                field("name"))
        ));
        String document = gqlDoc.build();
        assertEquivalentGraphQLRequest(Utils.getResourceFileContent("queries/e2e/queryWithArgs_1.graphql"), document);

        stubWireMock("e2e/queryWithArgs_1.json");
        Client client = jaxrsClientBuilder.build();
        WebTarget target = client.target(endpoint);
        Request request = new DynaQLRequest(document);
        javax.ws.rs.core.Response response = target.request(MediaType.APPLICATION_JSON).post(json(request));
        assertEquals(200, response.getStatus());

        Response graphQLResponse = response.readEntity(Response.class);
        assertTrue(graphQLResponse.hasData());
        assertFalse(graphQLResponse.hasError());

        Hero human = graphQLResponse.getObject(Hero.class, "human");
        assertEquals("Luke Skywalker", human.getName());
        Hero droid = graphQLResponse.getObject(Hero.class, "droid");
        assertEquals("C-3PO", droid.getName());

        client.close();
    }

    @Test
    // Allows us to create a generic query, then use it to fetch Luke Skywalker using his ID
    public void queryWithVariables_1_Test() throws IOException, URISyntaxException {
        Variable someId = var("someId", nonNull(GQL_STRING));
        Document gqlDoc = document(
                operation("FetchSomeIDQuery", vars(someId),
                        field("human", args(arg("id", someId)),
                                field("name"))
                ));
        String document = gqlDoc.build();
        assertEquivalentGraphQLRequest(Utils.getResourceFileContent("queries/e2e/queryWithVariables_1.graphql"), document);

        stubWireMock("e2e/queryWithVariables_1.json");
        Client client = jaxrsClientBuilder.build();
        WebTarget target = client.target(endpoint);
        Request request = new DynaQLRequest(document).setVariable("someId", "1000");
        javax.ws.rs.core.Response response = target.request(MediaType.APPLICATION_JSON).post(json(request));
        assertEquals(200, response.getStatus());

        Response graphQLResponse = response.readEntity(Response.class);
        assertTrue(graphQLResponse.hasData());
        assertFalse(graphQLResponse.hasError());

        Hero human = graphQLResponse.getObject(Hero.class, "human");
        assertEquals("Luke Skywalker", human.getName());

        client.close();
    }
}
