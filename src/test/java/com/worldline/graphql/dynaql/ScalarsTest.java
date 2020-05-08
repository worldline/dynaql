package com.worldline.graphql.dynaql;

import com.worldline.graphql.dynaql.request.Document;
import com.worldline.graphql.dynaql.request.Operation;
import com.worldline.graphql.dynaql.request.exceptions.RequestBuilderException;
import com.worldline.graphql.dynaql.request.Field;
import com.worldline.graphql.dynaql.request.InputObjectField;
import com.worldline.graphql.dynaql.util.AssertGraphQL;
import com.worldline.graphql.dynaql.util.Utils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URISyntaxException;

import static com.worldline.graphql.dynaql.request.Argument.arg;
import static com.worldline.graphql.dynaql.request.Argument.args;
import static com.worldline.graphql.dynaql.request.Document.document;
import static com.worldline.graphql.dynaql.request.Field.field;
import static com.worldline.graphql.dynaql.request.InputObject.object;
import static com.worldline.graphql.dynaql.request.Operation.operation;

public class ScalarsTest {

    @Test
    public void scalars() throws IOException, URISyntaxException, RequestBuilderException {
        String expectedRequest = Utils.getResourceFileContent(getClass(), "scalars.graphql");

        Document document = document(
                operation(Operation.Type.MUTATION, "scalarHolderMutation",
                        field("scalarHolder",
                                args(
                                        arg("scalarHolder", object(
                                                InputObjectField.prop("booleanPrimitive", false),
                                                InputObjectField.prop("booleanObject", Boolean.valueOf(true)),

                                                InputObjectField.prop("bytePrimitive", Byte.MIN_VALUE),
                                                InputObjectField.prop("byteObject", Byte.valueOf(Byte.MAX_VALUE)),

                                                InputObjectField.prop("shortPrimitive", Short.MIN_VALUE),
                                                InputObjectField.prop("shortObject", Short.valueOf(Short.MAX_VALUE)),

                                                InputObjectField.prop("intPrimitive", Integer.MIN_VALUE),
                                                InputObjectField.prop("intObject", Integer.valueOf(Integer.MAX_VALUE)),

                                                InputObjectField.prop("longPrimitive", Long.MIN_VALUE),
                                                InputObjectField.prop("longObject", Long.valueOf(Long.MAX_VALUE)),

                                                InputObjectField.prop("floatPrimitive", Float.MIN_VALUE),
                                                InputObjectField.prop("floatObject", Float.valueOf(Float.MAX_VALUE)),

                                                InputObjectField.prop("doublePrimitive", Double.MIN_VALUE),
                                                InputObjectField.prop("doubleObject", Double.valueOf(Double.MAX_VALUE)),

                                                InputObjectField.prop("bigInteger", BigInteger.TEN),
                                                InputObjectField.prop("bigDecimal", BigDecimal.TEN),

                                                InputObjectField.prop("charPrimitive", Character.MIN_VALUE),
                                                InputObjectField.prop("charObject", Character.valueOf(Character.MAX_VALUE)),

                                                InputObjectField.prop("stringObject", "Hello World !")
                                        ))),
                                Field.fields(
                                        Field.field("booleanPrimitive"),
                                        Field.field("booleanObject"),

                                        Field.field("bytePrimitive"),
                                        Field.field("byteObject"),

                                        Field.field("shortPrimitive"),
                                        Field.field("shortObject"),

                                        Field.field("intPrimitive"),
                                        Field.field("intObject"),

                                        Field.field("longPrimitive"),
                                        Field.field("longObject"),

                                        Field.field("floatPrimitive"),
                                        Field.field("floatObject"),

                                        Field.field("doublePrimitive"),
                                        Field.field("doubleObject"),

                                        Field.field("bigInteger"),
                                        Field.field("bigDecimal"),

                                        Field.field("charPrimitive"),
                                        Field.field("charObject"),

                                        Field.field("stringObject")
                                )
                        )
                ));

        String generatedRequest = document.toString();
        //System.out.println(generatedRequest);
        AssertGraphQL.assertEquivalentGraphQLRequest(expectedRequest, generatedRequest);

        Client client = ClientBuilder.newBuilder().build();
        client.target("http://mockTarget").request(generatedRequest);
    }
}
