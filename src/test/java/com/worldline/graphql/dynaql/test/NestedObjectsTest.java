package com.worldline.graphql.dynaql.test;

import com.worldline.graphql.dynaql.api.core.Operation;
import com.worldline.graphql.dynaql.impl.core.DynaQLDocument;
import com.worldline.graphql.dynaql.impl.core.DynaQLField;
import com.worldline.graphql.dynaql.impl.core.DynaQLInputObject;
import com.worldline.graphql.dynaql.impl.core.exceptions.BuilderException;
import com.worldline.graphql.dynaql.test.utils.AssertGraphQL;
import com.worldline.graphql.dynaql.test.utils.Utils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.worldline.graphql.dynaql.impl.core.DynaQLArgument.arg;
import static com.worldline.graphql.dynaql.impl.core.DynaQLArgument.args;
import static com.worldline.graphql.dynaql.impl.core.DynaQLDocument.document;
import static com.worldline.graphql.dynaql.impl.core.DynaQLField.field;
import static com.worldline.graphql.dynaql.impl.core.DynaQLInputObject.object;
import static com.worldline.graphql.dynaql.impl.core.DynaQLInputObjectField.prop;
import static com.worldline.graphql.dynaql.impl.core.DynaQLOperation.operation;


public class NestedObjectsTest {

    @Test
    public void nestedObjects() throws IOException, URISyntaxException, BuilderException {
        String expectedRequest = Utils.getResourceFileContent(getClass(), "nestedObjects.graphql");

        DynaQLInputObject baseObject_0 = object(
                prop("level", 0),
                prop("name", "level 0"),
                prop("levelLineage", new byte[]{}),
                prop("nestedObjectLineage", new DynaQLInputObject[]{})
        );
        DynaQLInputObject baseObject_1 = object(
                prop("level", 1),
                prop("name", "level 1"),
                prop("levelLineage", new byte[]{0}),
                prop("nestedObjectLineage", new DynaQLInputObject[]{baseObject_0})
        );
        DynaQLInputObject baseObject_2 = object(
                prop("level", 2),
                prop("name", "level 2"),
                prop("levelLineage", new byte[]{0, 1}),
                prop("nestedObjectLineage", new DynaQLInputObject[]{baseObject_0, baseObject_1})
        );
        DynaQLInputObject baseObject_3 = object(
                prop("level", 3),
                prop("name", "level 3"),
                prop("levelLineage", new byte[]{0, 1, 2}),
                prop("nestedObjectLineage", new DynaQLInputObject[]{baseObject_0, baseObject_1, baseObject_2})
        );

        /*
        We use cloning hereafter to avoid circular references.
         */

        DynaQLInputObject object_3 = baseObject_3.clone();
        object_3.getInputObjectFields().add(prop("nestedObject", null));

        DynaQLInputObject object_2 = (DynaQLInputObject) baseObject_2.clone();
        object_2.getInputObjectFields().add(prop("nestedObject", object_3));

        DynaQLInputObject object_1 = (DynaQLInputObject) baseObject_1.clone();
        object_1.getInputObjectFields().add(prop("nestedObject", object_2));

        DynaQLInputObject object_0 = (DynaQLInputObject) baseObject_0.clone();
        object_0.getInputObjectFields().add(prop("nestedObject", object_1));

        DynaQLDocument document =  document(
                operation(Operation.Type.MUTATION, "nestedObjects",
                        field("nestedObjectHolder", args(
                                arg("nestedObjectHolder", object_0)), DynaQLField.fields(
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

        String generatedRequest = document.build();
        //System.out.println(generatedRequest);
        AssertGraphQL.assertEquivalentGraphQLRequest(expectedRequest, generatedRequest);
    }
}
