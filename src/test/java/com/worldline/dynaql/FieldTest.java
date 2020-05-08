package com.worldline.dynaql;

import com.worldline.dynaql.request.Document;
import com.worldline.dynaql.request.exceptions.RequestBuilderException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.worldline.dynaql.request.Argument.arg;
import static com.worldline.dynaql.request.Argument.args;
import static com.worldline.dynaql.request.Document.document;
import static com.worldline.dynaql.request.Field.field;
import static com.worldline.dynaql.request.Field.fields;
import static com.worldline.dynaql.request.Operation.operation;
import static com.worldline.dynaql.util.AssertGraphQL.assertEquivalentGraphQLRequest;
import static com.worldline.dynaql.util.Utils.getResourceFileContent;

public class FieldTest {

    @Test
    public void fieldTest() throws IOException, URISyntaxException, RequestBuilderException {
        String expectedRequest = getResourceFileContent(getClass(), "field.graphql");

        Document document = document(
                operation(
                        field("noArgNoSubField"),
                        field("noArgWithSubField",
                                field("bool"),
                                field("string"),
                                field("double")
                        ),
                        field("withArgNoSubField", arg("anInt", 42)),
                        field("withArgWithSubField", args(
                                arg("aString", "world"),
                                arg("aDouble", 78.12d),
                                arg("aBool", false)), fields(
                                field("bool"),
                                field("string"),
                                field("double")
                        ))
                ));

        String generatedRequest = document.toString();
        //System.out.println(generatedRequest);
        assertEquivalentGraphQLRequest(expectedRequest, generatedRequest);

        Client client = ClientBuilder.newBuilder().build();
        client.target("http://mockTarget").request(generatedRequest);
    }
}
