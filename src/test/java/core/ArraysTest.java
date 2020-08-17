package core;

import com.worldline.graphql.dynaql.api.core.Document;
import com.worldline.graphql.dynaql.api.core.OperationType;
import helper.AssertGraphQL;
import helper.Utils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URISyntaxException;


import static com.worldline.graphql.dynaql.impl.core.DynaQLArgument.arg;
import static com.worldline.graphql.dynaql.impl.core.DynaQLArgument.args;
import static com.worldline.graphql.dynaql.impl.core.DynaQLDocument.document;
import static com.worldline.graphql.dynaql.impl.core.DynaQLField.field;
import static com.worldline.graphql.dynaql.impl.core.DynaQLInputObject.inputObject;
import static com.worldline.graphql.dynaql.impl.core.DynaQLInputObjectField.prop;
import static com.worldline.graphql.dynaql.impl.core.DynaQLOperation.operation;


public class ArraysTest {

    @Test
    public void arrays() throws IOException, URISyntaxException {
        String expectedRequest = Utils.getResourceFileContent("core/arrays.graphql");

        Document document = document(
                operation(OperationType.QUERY, "arrayHolderQuery",
                        field("arrayHolder",
                                args(
                                        arg("arrayHolder", inputObject(
                                                prop("boolPrimitiveArray", new boolean[]{true, false, true}),
                                                prop("boolObjectArray", new Boolean[]{true, false, true}),

                                                prop("bytePrimitiveArray", new byte[]{0, 2, 3}),
                                                prop("byteObjectArray", new Byte[]{0, 2, 3}),

                                                prop("shortPrimitiveArray", new short[]{78, 789, 645}),
                                                prop("shortObjectArray", new Short[]{78, 789, 645}),

                                                prop("intPrimitiveArray", new int[]{78, 65, 12354}),
                                                prop("intObjectArray", new Integer[]{78, 65, 12354}),

                                                prop("longPrimitiveArray", new long[]{789L, 947894L, 1874448L}),
                                                prop("longObjectArray", new Long[]{789L, 947894L, 1874448L}),

                                                prop("floatPrimitiveArray", new float[]{1567.654f, 8765f, 123789456.1851f}),
                                                prop("floatObjectArray", new Float[]{1567.654f, 8765f, 123789456.1851f}),

                                                prop("doublePrimitiveArray", new double[]{789.3242d, 1815d, 98765421.654897d}),
                                                prop("doubleObjectArray", new Double[]{789.3242d, 1815d, 98765421.654897d}),

                                                prop("bigIntegerArray", new BigInteger[]{BigInteger.ZERO, BigInteger.ONE, BigInteger.TEN}),
                                                prop("bigDecimalArray", new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TEN}),

                                                prop("charPrimitiveArray", new char[]{'f', 'o', 'o'}),
                                                prop("charObjectArray", new Character[]{'f', 'o', 'o'}),

                                                prop("stringArray", new String[]{"foo", "bar", "baz"})
                                        ))),
                                field("boolPrimitiveArray"),
                                field("boolObjectArray"),

                                field("bytePrimitiveArray"),
                                field("byteObjectArray"),

                                field("shortPrimitiveArray"),
                                field("shortObjectArray"),

                                field("intPrimitiveArray"),
                                field("intObjectArray"),

                                field("longPrimitiveArray"),
                                field("longObjectArray"),

                                field("floatPrimitiveArray"),
                                field("floatObjectArray"),

                                field("doublePrimitiveArray"),
                                field("doubleObjectArray"),

                                field("bigIntegerArray"),
                                field("bigDecimalArray"),

                                field("charPrimitiveArray"),
                                field("charObjectArray"),

                                field("stringArray")
                        )
                )
        );

        String generatedRequest = document.build();
        //System.out.println(generatedRequest);
        AssertGraphQL.assertEquivalentGraphQLRequest(expectedRequest, generatedRequest);
    }
}
