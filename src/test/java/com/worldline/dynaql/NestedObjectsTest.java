package com.worldline.dynaql;

import com.worldline.dynaql.request.Document;
import com.worldline.dynaql.request.InputObject;
import com.worldline.dynaql.request.Operation;
import com.worldline.dynaql.request.exceptions.RequestBuilderException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.worldline.dynaql.request.Argument.arg;
import static com.worldline.dynaql.request.Argument.args;
import static com.worldline.dynaql.request.Document.document;
import static com.worldline.dynaql.request.Field.field;
import static com.worldline.dynaql.request.Field.fields;
import static com.worldline.dynaql.request.InputObject.object;
import static com.worldline.dynaql.request.InputObjectField.prop;
import static com.worldline.dynaql.request.Operation.operation;
import static com.worldline.dynaql.util.AssertGraphQL.assertEquivalentGraphQLRequest;
import static com.worldline.dynaql.util.Utils.getResourceFileContent;


public class NestedObjectsTest {

    @Test
    public void nestedObjects() throws IOException, URISyntaxException, RequestBuilderException {
        String expectedRequest = getResourceFileContent(getClass(), "nestedObjects.graphql");

        InputObject baseObject_0 = object(
                prop("level", 0),
                prop("name", "level 0"),
                prop("levelLineage", new byte[]{}),
                prop("nestedObjectLineage", new InputObject[]{})
        );
        InputObject baseObject_1 = object(
                prop("level", 1),
                prop("name", "level 1"),
                prop("levelLineage", new byte[]{0}),
                prop("nestedObjectLineage", new InputObject[]{baseObject_0})
        );
        InputObject baseObject_2 = object(
                prop("level", 2),
                prop("name", "level 2"),
                prop("levelLineage", new byte[]{0, 1}),
                prop("nestedObjectLineage", new InputObject[]{baseObject_0, baseObject_1})
        );
        InputObject baseObject_3 = object(
                prop("level", 3),
                prop("name", "level 3"),
                prop("levelLineage", new byte[]{0, 1, 2}),
                prop("nestedObjectLineage", new InputObject[]{baseObject_0, baseObject_1, baseObject_2})
        );

        InputObject object_3 = baseObject_3.clone();
        object_3.getInputObjectFields().add(prop("nestedObject", null));

        InputObject object_2 = (InputObject) baseObject_2.clone();
        object_2.getInputObjectFields().add(prop("nestedObject", object_3));

        InputObject object_1 = (InputObject) baseObject_1.clone();
        object_1.getInputObjectFields().add(prop("nestedObject", object_2));

        InputObject object_0 = (InputObject) baseObject_0.clone();
        object_0.getInputObjectFields().add(prop("nestedObject", object_1));

        Document document =  document(
                operation(Operation.Type.MUTATION, "nestedObjects",
                        field("nestedObjectHolder", args(
                                arg("nestedObjectHolder", object_0)), fields(
                                field("level"),
                                field("name"),
                                field("levelLineage"),
                                field("nestedObjectLineage",
                                        field("level"),
                                        field("name"),
                                        field("levelLineage"),
                                        field("nestedObjectLineage",
                                                field("level"),
                                                field("name"),
                                                field("levelLineage"),
                                                field("nestedObjectLineage",
                                                        field("level"),
                                                        field("name"),
                                                        field("levelLineage"),
                                                        field("nestedObjectLineage",
                                                                field("level"),
                                                                field("name"),
                                                                field("levelLineage"))))),
                                field("nestedObject",
                                        field("level"),
                                        field("name"),
                                        field("levelLineage"),
                                        field("nestedObjectLineage",
                                                field("level"),
                                                field("name"),
                                                field("levelLineage"),
                                                field("nestedObjectLineage",
                                                        field("level"),
                                                        field("name"),
                                                        field("levelLineage"),
                                                        field("nestedObjectLineage",
                                                                field("level"),
                                                                field("name"),
                                                                field("levelLineage"),
                                                                field("nestedObjectLineage",
                                                                        field("level"),
                                                                        field("name"),
                                                                        field("levelLineage"))))),
                                        field("nestedObject",
                                                field("level"),
                                                field("name"),
                                                field("levelLineage"),
                                                field("nestedObjectLineage",
                                                        field("level"),
                                                        field("name"),
                                                        field("levelLineage"),
                                                        field("nestedObjectLineage",
                                                                field("level"),
                                                                field("name"),
                                                                field("levelLineage"),
                                                                field("nestedObjectLineage",
                                                                        field("level"),
                                                                        field("name"),
                                                                        field("levelLineage"),
                                                                        field("nestedObjectLineage",
                                                                                field("level"),
                                                                                field("name"),
                                                                                field("levelLineage"))))),
                                                field("nestedObject",
                                                        field("level"),
                                                        field("name"),
                                                        field("levelLineage"),
                                                        field("nestedObjectLineage",
                                                                field("level"),
                                                                field("name"),
                                                                field("levelLineage"),
                                                                field("nestedObjectLineage",
                                                                        field("level"),
                                                                        field("name"),
                                                                        field("levelLineage"),
                                                                        field("nestedObjectLineage",
                                                                                field("level"),
                                                                                field("name"),
                                                                                field("levelLineage"),
                                                                                field("nestedObjectLineage",
                                                                                        field("level"),
                                                                                        field("name"),
                                                                                        field("levelLineage"))))),
                                                        field("nestedObject",
                                                                field("level"),
                                                                field("name"),
                                                                field("levelLineage"),
                                                                field("nestedObjectLineage",
                                                                        field("level"),
                                                                        field("name"),
                                                                        field("levelLineage"),
                                                                        field("nestedObjectLineage",
                                                                                field("level"),
                                                                                field("name"),
                                                                                field("levelLineage"),
                                                                                field("nestedObjectLineage",
                                                                                        field("level"),
                                                                                        field("name"),
                                                                                        field("levelLineage"),
                                                                                        field("nestedObjectLineage",
                                                                                                field("level"),
                                                                                                field("name"),
                                                                                                field("levelLineage"))))),
                                                                field("nestedObject",
                                                                        field("level"),
                                                                        field("name"),
                                                                        field("levelLineage"),
                                                                        field("nestedObjectLineage",
                                                                                field("level"),
                                                                                field("name"),
                                                                                field("levelLineage"),
                                                                                field("nestedObjectLineage",
                                                                                        field("level"),
                                                                                        field("name"),
                                                                                        field("levelLineage"),
                                                                                        field("nestedObjectLineage",
                                                                                                field("level"),
                                                                                                field("name"),
                                                                                                field("levelLineage"),
                                                                                                field("nestedObjectLineage",
                                                                                                        field("level"),
                                                                                                        field("name"),
                                                                                                        field("levelLineage"))))))))))))));

        String generatedRequest = document.toString();
        //System.out.println(generatedRequest);
        assertEquivalentGraphQLRequest(expectedRequest, generatedRequest);

        Client client = ClientBuilder.newBuilder().build();
        client.target("http://mockTarget").request(generatedRequest);
    }
}
