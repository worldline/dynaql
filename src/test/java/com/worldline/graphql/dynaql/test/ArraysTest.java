package com.worldline.graphql.dynaql.test;

import com.worldline.graphql.dynaql.impl.core.DynaQLDocument;
import com.worldline.graphql.dynaql.impl.core.DynaQLField;
import com.worldline.graphql.dynaql.impl.core.DynaQLInputObjectField;
import com.worldline.graphql.dynaql.impl.core.DynaQLOperation;
import com.worldline.graphql.dynaql.impl.core.exceptions.BuilderException;
import com.worldline.graphql.dynaql.test.utils.AssertGraphQL;
import com.worldline.graphql.dynaql.test.utils.Utils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URISyntaxException;

import static com.worldline.graphql.dynaql.impl.core.DynaQLArgument.arg;
import static com.worldline.graphql.dynaql.impl.core.DynaQLArgument.args;
import static com.worldline.graphql.dynaql.impl.core.DynaQLDocument.document;
import static com.worldline.graphql.dynaql.impl.core.DynaQLField.field;
import static com.worldline.graphql.dynaql.impl.core.DynaQLInputObject.object;
import static com.worldline.graphql.dynaql.impl.core.DynaQLOperation.operation;


public class ArraysTest {

    @Test
    public void arrays() throws IOException, URISyntaxException, BuilderException {
        String expectedRequest = Utils.getResourceFileContent(getClass(), "arrays.graphql");

        DynaQLDocument document = document(
                operation(DynaQLOperation.Type.QUERY, "arrayHolderQuery",
                        field("arrayHolder",
                                args(
                                        arg("arrayHolder", object(
                                                DynaQLInputObjectField.prop("boolPrimitiveArray", new boolean[]{true, false, true}),
                                                DynaQLInputObjectField.prop("boolObjectArray", new Boolean[]{true, false, true}),

                                                DynaQLInputObjectField.prop("bytePrimitiveArray", new byte[]{0, 2, 3}),
                                                DynaQLInputObjectField.prop("byteObjectArray", new Byte[]{0, 2, 3}),

                                                DynaQLInputObjectField.prop("shortPrimitiveArray", new short[]{78, 789, 645}),
                                                DynaQLInputObjectField.prop("shortObjectArray", new Short[]{78, 789, 645}),

                                                DynaQLInputObjectField.prop("intPrimitiveArray", new int[]{78, 65, 12354}),
                                                DynaQLInputObjectField.prop("intObjectArray", new Integer[]{78, 65, 12354}),

                                                DynaQLInputObjectField.prop("longPrimitiveArray", new long[]{789L, 947894L, 1874448L}),
                                                DynaQLInputObjectField.prop("longObjectArray", new Long[]{789L, 947894L, 1874448L}),

                                                DynaQLInputObjectField.prop("floatPrimitiveArray", new float[]{1567.654f, 8765f, 123789456.1851f}),
                                                DynaQLInputObjectField.prop("floatObjectArray", new Float[]{1567.654f, 8765f, 123789456.1851f}),

                                                DynaQLInputObjectField.prop("doublePrimitiveArray", new double[]{789.3242d, 1815d, 98765421.654897d}),
                                                DynaQLInputObjectField.prop("doubleObjectArray", new Double[]{789.3242d, 1815d, 98765421.654897d}),

                                                DynaQLInputObjectField.prop("bigIntegerArray", new BigInteger[]{BigInteger.ZERO, BigInteger.ONE, BigInteger.TEN}),
                                                DynaQLInputObjectField.prop("bigDecimalArray", new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TEN}),

                                                DynaQLInputObjectField.prop("charPrimitiveArray", new char[]{'f', 'o', 'o'}),
                                                DynaQLInputObjectField.prop("charObjectArray", new Character[]{'f', 'o', 'o'}),

                                                DynaQLInputObjectField.prop("stringArray", new String[]{"foo", "bar", "baz"})
                                        ))),
                                DynaQLField.fields(
                                        DynaQLField.field("boolPrimitiveArray"),
                                        DynaQLField.field("boolObjectArray"),

                                        DynaQLField.field("bytePrimitiveArray"),
                                        DynaQLField.field("byteObjectArray"),

                                        DynaQLField.field("shortPrimitiveArray"),
                                        DynaQLField.field("shortObjectArray"),

                                        DynaQLField.field("intPrimitiveArray"),
                                        DynaQLField.field("intObjectArray"),

                                        DynaQLField.field("longPrimitiveArray"),
                                        DynaQLField.field("longObjectArray"),

                                        DynaQLField.field("floatPrimitiveArray"),
                                        DynaQLField.field("floatObjectArray"),

                                        DynaQLField.field("doublePrimitiveArray"),
                                        DynaQLField.field("doubleObjectArray"),

                                        DynaQLField.field("bigIntegerArray"),
                                        DynaQLField.field("bigDecimalArray"),

                                        DynaQLField.field("charPrimitiveArray"),
                                        DynaQLField.field("charObjectArray"),

                                        DynaQLField.field("stringArray")
                                )
                        )
                ));

        String generatedRequest = document.build();
        //System.out.println(generatedRequest);
        AssertGraphQL.assertEquivalentGraphQLRequest(expectedRequest, generatedRequest);
    }
}
