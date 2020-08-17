package core;

import com.worldline.graphql.dynaql.api.core.Document;
import com.worldline.graphql.dynaql.api.core.InputObject;
import com.worldline.graphql.dynaql.api.core.OperationType;
import com.worldline.graphql.dynaql.impl.core.exceptions.BuilderException;
import helper.AssertGraphQL;
import helper.Utils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import static com.worldline.graphql.dynaql.impl.core.DynaQLArgument.arg;
import static com.worldline.graphql.dynaql.impl.core.DynaQLArgument.args;
import static com.worldline.graphql.dynaql.impl.core.DynaQLDocument.document;
import static com.worldline.graphql.dynaql.impl.core.DynaQLField.field;
import static com.worldline.graphql.dynaql.impl.core.DynaQLInputObject.inputObject;
import static com.worldline.graphql.dynaql.impl.core.DynaQLInputObjectField.prop;
import static com.worldline.graphql.dynaql.impl.core.DynaQLOperation.operation;


public class NestedObjectsTest {

    @Test
    public void nestedObjects() throws IOException, URISyntaxException, BuilderException {
        String expectedRequest = Utils.getResourceFileContent("core/nestedObjects.graphql");

        InputObject baseObject_0 = inputObject(
                prop("level", 0),
                prop("name", "level 0"),
                prop("levelLineage", new byte[]{}),
                prop("nestedObjectLineage", new InputObject[]{})
        );
        InputObject baseObject_1 = inputObject(
                prop("level", 1),
                prop("name", "level 1"),
                prop("levelLineage", new byte[]{0}),
                prop("nestedObjectLineage", new InputObject[]{baseObject_0})
        );
        InputObject baseObject_2 = inputObject(
                prop("level", 2),
                prop("name", "level 2"),
                prop("levelLineage", new byte[]{0, 1}),
                prop("nestedObjectLineage", new InputObject[]{baseObject_0, baseObject_1})
        );
        InputObject baseObject_3 = inputObject(
                prop("level", 3),
                prop("name", "level 3"),
                prop("levelLineage", new byte[]{0, 1, 2}),
                prop("nestedObjectLineage", new InputObject[]{baseObject_0, baseObject_1, baseObject_2})
        );

        /*
        We use cloning hereafter to avoid circular references.
         */
        InputObject object_3 = inputObject();
        object_3.setInputObjectFields(new ArrayList<>(baseObject_3.getInputObjectFields()));
        object_3.getInputObjectFields().add(prop("nestedObject", null));

        InputObject object_2 = inputObject();
        object_2.setInputObjectFields(new ArrayList<>(baseObject_2.getInputObjectFields()));
        object_2.getInputObjectFields().add(prop("nestedObject", object_3));

        InputObject object_1 = inputObject();
        object_1.setInputObjectFields(new ArrayList<>(baseObject_1.getInputObjectFields()));
        object_1.getInputObjectFields().add(prop("nestedObject", object_2));

        InputObject object_0 = inputObject();
        object_0.setInputObjectFields(new ArrayList<>(baseObject_0.getInputObjectFields()));
        object_0.getInputObjectFields().add(prop("nestedObject", object_1));

        Document document = document(
                operation(OperationType.MUTATION, "nestedObjects",
                        field("nestedObjectHolder", args(
                                arg("nestedObjectHolder", object_0)),
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
                                                                                                        field("levelLineage")))))))))))));

        String generatedRequest = document.build();
        //System.out.println(generatedRequest);
        AssertGraphQL.assertEquivalentGraphQLRequest(expectedRequest, generatedRequest);
    }
}
