package com.worldline.graphql.dynaql;

import com.worldline.graphql.dynaql.request.Document;
import com.worldline.graphql.dynaql.request.exceptions.RequestBuilderException;
import com.worldline.graphql.dynaql.request.Field;
import com.worldline.graphql.dynaql.util.Utils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.worldline.graphql.dynaql.request.Argument.arg;
import static com.worldline.graphql.dynaql.request.Argument.args;
import static com.worldline.graphql.dynaql.request.Document.document;
import static com.worldline.graphql.dynaql.request.Field.field;
import static com.worldline.graphql.dynaql.request.Operation.operation;
import static com.worldline.graphql.dynaql.util.AssertGraphQL.assertEquivalentGraphQLRequest;

public class FieldTest {

    @Test
    public void fieldTest() throws IOException, URISyntaxException, RequestBuilderException {
        String expectedRequest = Utils.getResourceFileContent(getClass(), "field.graphql");

        Document document = document(
                operation(
                        Field.field("noArgNoSubField"),
                        Field.field("noArgWithSubField",
                                Field.field("bool"),
                                Field.field("string"),
                                Field.field("double")
                        ),
                        field("withArgNoSubField", arg("anInt", 42)),
                        field("withArgWithSubField", args(
                                arg("aString", "world"),
                                arg("aDouble", 78.12d),
                                arg("aBool", false)), Field.fields(
                                Field.field("bool"),
                                Field.field("string"),
                                Field.field("double")
                        ))
                ));

        String generatedRequest = document.toString();
        //System.out.println(generatedRequest);
        assertEquivalentGraphQLRequest(expectedRequest, generatedRequest);

        Client client = ClientBuilder.newBuilder().build();
        client.target("http://mockTarget").request(generatedRequest);
    }
}
