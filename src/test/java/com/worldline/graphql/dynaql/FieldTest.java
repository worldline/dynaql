package com.worldline.graphql.dynaql;

import com.worldline.graphql.dynaql.impl.core.DynaQLDocument;
import com.worldline.graphql.dynaql.impl.core.exceptions.BuilderException;
import com.worldline.graphql.dynaql.utils.Utils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.worldline.graphql.dynaql.impl.core.DynaQLArgument.arg;
import static com.worldline.graphql.dynaql.impl.core.DynaQLArgument.args;
import static com.worldline.graphql.dynaql.impl.core.DynaQLDocument.document;
import static com.worldline.graphql.dynaql.impl.core.DynaQLField.field;
import static com.worldline.graphql.dynaql.impl.core.DynaQLField.fields;
import static com.worldline.graphql.dynaql.impl.core.DynaQLOperation.operation;
import static com.worldline.graphql.dynaql.utils.AssertGraphQL.assertEquivalentGraphQLRequest;

public class FieldTest {

    @Test
    public void fieldTest() throws IOException, URISyntaxException, BuilderException {
        String expectedRequest = Utils.getResourceFileContent(getClass(), "field.graphql");

        DynaQLDocument document = document(
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

        String generatedRequest = document.build();
        //System.out.println(generatedRequest);
        assertEquivalentGraphQLRequest(expectedRequest, generatedRequest);
    }
}
