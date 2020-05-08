package com.worldline.graphql.dynaql;

import com.worldline.graphql.dynaql.request.Document;
import com.worldline.graphql.dynaql.request.Operation;
import com.worldline.graphql.dynaql.request.exceptions.RequestBuilderException;
import com.worldline.graphql.dynaql.request.Field;
import com.worldline.graphql.dynaql.request.InputObjectField;
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
import static com.worldline.graphql.dynaql.util.AssertGraphQL.assertEquivalentGraphQLRequest;


public class ArraysTest {

    @Test
    public void arrays() throws IOException, URISyntaxException, RequestBuilderException {
        String expectedRequest = Utils.getResourceFileContent(getClass(), "arrays.graphql");

        Document document = document(
                operation(Operation.Type.QUERY, "arrayHolderQuery",
                        field("arrayHolder",
                                args(
                                        arg("arrayHolder", object(
                                                InputObjectField.prop("boolPrimitiveArray", new boolean[]{true, false, true}),
                                                InputObjectField.prop("boolObjectArray", new Boolean[]{true, false, true}),

                                                InputObjectField.prop("bytePrimitiveArray", new byte[]{0, 2, 3}),
                                                InputObjectField.prop("byteObjectArray", new Byte[]{0, 2, 3}),

                                                InputObjectField.prop("shortPrimitiveArray", new short[]{78, 789, 645}),
                                                InputObjectField.prop("shortObjectArray", new Short[]{78, 789, 645}),

                                                InputObjectField.prop("intPrimitiveArray", new int[]{78, 65, 12354}),
                                                InputObjectField.prop("intObjectArray", new Integer[]{78, 65, 12354}),

                                                InputObjectField.prop("longPrimitiveArray", new long[]{789L, 947894L, 1874448L}),
                                                InputObjectField.prop("longObjectArray", new Long[]{789L, 947894L, 1874448L}),

                                                InputObjectField.prop("floatPrimitiveArray", new float[]{1567.654f, 8765f, 123789456.1851f}),
                                                InputObjectField.prop("floatObjectArray", new Float[]{1567.654f, 8765f, 123789456.1851f}),

                                                InputObjectField.prop("doublePrimitiveArray", new double[]{789.3242d, 1815d, 98765421.654897d}),
                                                InputObjectField.prop("doubleObjectArray", new Double[]{789.3242d, 1815d, 98765421.654897d}),

                                                InputObjectField.prop("bigIntegerArray", new BigInteger[]{BigInteger.ZERO, BigInteger.ONE, BigInteger.TEN}),
                                                InputObjectField.prop("bigDecimalArray", new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TEN}),

                                                InputObjectField.prop("charPrimitiveArray", new char[]{'f', 'o', 'o'}),
                                                InputObjectField.prop("charObjectArray", new Character[]{'f', 'o', 'o'}),

                                                InputObjectField.prop("stringArray", new String[]{"foo", "bar", "baz"})
                                        ))),
                                Field.fields(
                                        Field.field("boolPrimitiveArray"),
                                        Field.field("boolObjectArray"),

                                        Field.field("bytePrimitiveArray"),
                                        Field.field("byteObjectArray"),

                                        Field.field("shortPrimitiveArray"),
                                        Field.field("shortObjectArray"),

                                        Field.field("intPrimitiveArray"),
                                        Field.field("intObjectArray"),

                                        Field.field("longPrimitiveArray"),
                                        Field.field("longObjectArray"),

                                        Field.field("floatPrimitiveArray"),
                                        Field.field("floatObjectArray"),

                                        Field.field("doublePrimitiveArray"),
                                        Field.field("doubleObjectArray"),

                                        Field.field("bigIntegerArray"),
                                        Field.field("bigDecimalArray"),

                                        Field.field("charPrimitiveArray"),
                                        Field.field("charObjectArray"),

                                        Field.field("stringArray")
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
