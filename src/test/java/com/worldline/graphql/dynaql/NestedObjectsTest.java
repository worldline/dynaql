package com.worldline.graphql.dynaql;

import com.worldline.graphql.dynaql.request.Document;
import com.worldline.graphql.dynaql.request.InputObject;
import com.worldline.graphql.dynaql.request.Operation;
import com.worldline.graphql.dynaql.request.exceptions.RequestBuilderException;
import com.worldline.graphql.dynaql.request.Field;
import com.worldline.graphql.dynaql.request.InputObjectField;
import com.worldline.graphql.dynaql.util.Utils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.worldline.graphql.dynaql.request.Argument.arg;
import static com.worldline.graphql.dynaql.request.Argument.args;
import static com.worldline.graphql.dynaql.request.Document.document;
import static com.worldline.graphql.dynaql.request.Field.field;
import static com.worldline.graphql.dynaql.request.InputObject.object;
import static com.worldline.graphql.dynaql.request.Operation.operation;
import static com.worldline.graphql.dynaql.util.AssertGraphQL.assertEquivalentGraphQLRequest;


public class NestedObjectsTest {

    @Test
    public void nestedObjects() throws IOException, URISyntaxException, RequestBuilderException {
        String expectedRequest = Utils.getResourceFileContent(getClass(), "nestedObjects.graphql");

        InputObject baseObject_0 = object(
                InputObjectField.prop("level", 0),
                InputObjectField.prop("name", "level 0"),
                InputObjectField.prop("levelLineage", new byte[]{}),
                InputObjectField.prop("nestedObjectLineage", new InputObject[]{})
        );
        InputObject baseObject_1 = object(
                InputObjectField.prop("level", 1),
                InputObjectField.prop("name", "level 1"),
                InputObjectField.prop("levelLineage", new byte[]{0}),
                InputObjectField.prop("nestedObjectLineage", new InputObject[]{baseObject_0})
        );
        InputObject baseObject_2 = object(
                InputObjectField.prop("level", 2),
                InputObjectField.prop("name", "level 2"),
                InputObjectField.prop("levelLineage", new byte[]{0, 1}),
                InputObjectField.prop("nestedObjectLineage", new InputObject[]{baseObject_0, baseObject_1})
        );
        InputObject baseObject_3 = object(
                InputObjectField.prop("level", 3),
                InputObjectField.prop("name", "level 3"),
                InputObjectField.prop("levelLineage", new byte[]{0, 1, 2}),
                InputObjectField.prop("nestedObjectLineage", new InputObject[]{baseObject_0, baseObject_1, baseObject_2})
        );

        InputObject object_3 = baseObject_3.clone();
        object_3.getInputObjectFields().add(InputObjectField.prop("nestedObject", null));

        InputObject object_2 = (InputObject) baseObject_2.clone();
        object_2.getInputObjectFields().add(InputObjectField.prop("nestedObject", object_3));

        InputObject object_1 = (InputObject) baseObject_1.clone();
        object_1.getInputObjectFields().add(InputObjectField.prop("nestedObject", object_2));

        InputObject object_0 = (InputObject) baseObject_0.clone();
        object_0.getInputObjectFields().add(InputObjectField.prop("nestedObject", object_1));

        Document document =  document(
                operation(Operation.Type.MUTATION, "nestedObjects",
                        field("nestedObjectHolder", args(
                                arg("nestedObjectHolder", object_0)), Field.fields(
                                Field.field("level"),
                                Field.field("name"),
                                Field.field("levelLineage"),
                                Field.field("nestedObjectLineage",
                                        Field.field("level"),
                                        Field.field("name"),
                                        Field.field("levelLineage"),
                                        Field.field("nestedObjectLineage",
                                                Field.field("level"),
                                                Field.field("name"),
                                                Field.field("levelLineage"),
                                                Field.field("nestedObjectLineage",
                                                        Field.field("level"),
                                                        Field.field("name"),
                                                        Field.field("levelLineage"),
                                                        Field.field("nestedObjectLineage",
                                                                Field.field("level"),
                                                                Field.field("name"),
                                                                Field.field("levelLineage"))))),
                                Field.field("nestedObject",
                                        Field.field("level"),
                                        Field.field("name"),
                                        Field.field("levelLineage"),
                                        Field.field("nestedObjectLineage",
                                                Field.field("level"),
                                                Field.field("name"),
                                                Field.field("levelLineage"),
                                                Field.field("nestedObjectLineage",
                                                        Field.field("level"),
                                                        Field.field("name"),
                                                        Field.field("levelLineage"),
                                                        Field.field("nestedObjectLineage",
                                                                Field.field("level"),
                                                                Field.field("name"),
                                                                Field.field("levelLineage"),
                                                                Field.field("nestedObjectLineage",
                                                                        Field.field("level"),
                                                                        Field.field("name"),
                                                                        Field.field("levelLineage"))))),
                                        Field.field("nestedObject",
                                                Field.field("level"),
                                                Field.field("name"),
                                                Field.field("levelLineage"),
                                                Field.field("nestedObjectLineage",
                                                        Field.field("level"),
                                                        Field.field("name"),
                                                        Field.field("levelLineage"),
                                                        Field.field("nestedObjectLineage",
                                                                Field.field("level"),
                                                                Field.field("name"),
                                                                Field.field("levelLineage"),
                                                                Field.field("nestedObjectLineage",
                                                                        Field.field("level"),
                                                                        Field.field("name"),
                                                                        Field.field("levelLineage"),
                                                                        Field.field("nestedObjectLineage",
                                                                                Field.field("level"),
                                                                                Field.field("name"),
                                                                                Field.field("levelLineage"))))),
                                                Field.field("nestedObject",
                                                        Field.field("level"),
                                                        Field.field("name"),
                                                        Field.field("levelLineage"),
                                                        Field.field("nestedObjectLineage",
                                                                Field.field("level"),
                                                                Field.field("name"),
                                                                Field.field("levelLineage"),
                                                                Field.field("nestedObjectLineage",
                                                                        Field.field("level"),
                                                                        Field.field("name"),
                                                                        Field.field("levelLineage"),
                                                                        Field.field("nestedObjectLineage",
                                                                                Field.field("level"),
                                                                                Field.field("name"),
                                                                                Field.field("levelLineage"),
                                                                                Field.field("nestedObjectLineage",
                                                                                        Field.field("level"),
                                                                                        Field.field("name"),
                                                                                        Field.field("levelLineage"))))),
                                                        Field.field("nestedObject",
                                                                Field.field("level"),
                                                                Field.field("name"),
                                                                Field.field("levelLineage"),
                                                                Field.field("nestedObjectLineage",
                                                                        Field.field("level"),
                                                                        Field.field("name"),
                                                                        Field.field("levelLineage"),
                                                                        Field.field("nestedObjectLineage",
                                                                                Field.field("level"),
                                                                                Field.field("name"),
                                                                                Field.field("levelLineage"),
                                                                                Field.field("nestedObjectLineage",
                                                                                        Field.field("level"),
                                                                                        Field.field("name"),
                                                                                        Field.field("levelLineage"),
                                                                                        Field.field("nestedObjectLineage",
                                                                                                Field.field("level"),
                                                                                                Field.field("name"),
                                                                                                Field.field("levelLineage"))))),
                                                                Field.field("nestedObject",
                                                                        Field.field("level"),
                                                                        Field.field("name"),
                                                                        Field.field("levelLineage"),
                                                                        Field.field("nestedObjectLineage",
                                                                                Field.field("level"),
                                                                                Field.field("name"),
                                                                                Field.field("levelLineage"),
                                                                                Field.field("nestedObjectLineage",
                                                                                        Field.field("level"),
                                                                                        Field.field("name"),
                                                                                        Field.field("levelLineage"),
                                                                                        Field.field("nestedObjectLineage",
                                                                                                Field.field("level"),
                                                                                                Field.field("name"),
                                                                                                Field.field("levelLineage"),
                                                                                                Field.field("nestedObjectLineage",
                                                                                                        Field.field("level"),
                                                                                                        Field.field("name"),
                                                                                                        Field.field("levelLineage"))))))))))))));

        String generatedRequest = document.toString();
        //System.out.println(generatedRequest);
        assertEquivalentGraphQLRequest(expectedRequest, generatedRequest);
    }
}
