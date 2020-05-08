package com.worldline.dynaql;

import com.worldline.dynaql.request.Document;
import com.worldline.dynaql.request.Operation;
import com.worldline.dynaql.request.exceptions.RequestBuilderException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
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

public class ScalarsTest {

    @Test
    public void scalars() throws IOException, URISyntaxException, RequestBuilderException {
        String expectedRequest = getResourceFileContent(getClass(), "scalars.graphql");

        Document document = document(
                operation(Operation.Type.MUTATION, "scalarHolderMutation",
                        field("scalarHolder",
                                args(
                                        arg("scalarHolder", object(
                                                prop("booleanPrimitive", false),
                                                prop("booleanObject", Boolean.valueOf(true)),

                                                prop("bytePrimitive", Byte.MIN_VALUE),
                                                prop("byteObject", Byte.valueOf(Byte.MAX_VALUE)),

                                                prop("shortPrimitive", Short.MIN_VALUE),
                                                prop("shortObject", Short.valueOf(Short.MAX_VALUE)),

                                                prop("intPrimitive", Integer.MIN_VALUE),
                                                prop("intObject", Integer.valueOf(Integer.MAX_VALUE)),

                                                prop("longPrimitive", Long.MIN_VALUE),
                                                prop("longObject", Long.valueOf(Long.MAX_VALUE)),

                                                prop("floatPrimitive", Float.MIN_VALUE),
                                                prop("floatObject", Float.valueOf(Float.MAX_VALUE)),

                                                prop("doublePrimitive", Double.MIN_VALUE),
                                                prop("doubleObject", Double.valueOf(Double.MAX_VALUE)),

                                                prop("bigInteger", BigInteger.TEN),
                                                prop("bigDecimal", BigDecimal.TEN),

                                                prop("charPrimitive", Character.MIN_VALUE),
                                                prop("charObject", Character.valueOf(Character.MAX_VALUE)),

                                                prop("stringObject", "Hello World !")
                                        ))),
                                fields(
                                        field("booleanPrimitive"),
                                        field("booleanObject"),

                                        field("bytePrimitive"),
                                        field("byteObject"),

                                        field("shortPrimitive"),
                                        field("shortObject"),

                                        field("intPrimitive"),
                                        field("intObject"),

                                        field("longPrimitive"),
                                        field("longObject"),

                                        field("floatPrimitive"),
                                        field("floatObject"),

                                        field("doublePrimitive"),
                                        field("doubleObject"),

                                        field("bigInteger"),
                                        field("bigDecimal"),

                                        field("charPrimitive"),
                                        field("charObject"),

                                        field("stringObject")
                                )
                        )
                ));

        String generatedRequest = document.toString();
        //System.out.println(generatedRequest);
        assertEquivalentGraphQLRequest(expectedRequest, generatedRequest);

        Client client = ClientBuilder.newBuilder().build();
        client.target("http://mockTarget").request(generatedRequest);
    }
}
