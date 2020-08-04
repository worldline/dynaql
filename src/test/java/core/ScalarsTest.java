package core;

import com.worldline.graphql.dynaql.api.core.Document;
import com.worldline.graphql.dynaql.api.core.OperationType;
import com.worldline.graphql.dynaql.impl.core.DynaQLInputObject;
import com.worldline.graphql.dynaql.impl.core.exceptions.BuilderException;
import helper.AssertGraphQL;
import helper.Utils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URISyntaxException;

import static com.worldline.graphql.dynaql.api.core.Argument.args;
import static com.worldline.graphql.dynaql.impl.core.DynaQLArgument.arg;
import static com.worldline.graphql.dynaql.impl.core.DynaQLDocument.document;
import static com.worldline.graphql.dynaql.impl.core.DynaQLField.field;
import static com.worldline.graphql.dynaql.impl.core.DynaQLInputObjectField.prop;
import static com.worldline.graphql.dynaql.impl.core.DynaQLOperation.operation;

public class ScalarsTest {

    @Test
    public void scalars() throws IOException, URISyntaxException, BuilderException {
        String expectedRequest = Utils.getResourceFileContent("core/scalars.graphql");

        Document document = document(
                operation(OperationType.MUTATION, "scalarHolderMutation",
                        field("scalarHolder",
                                args(
                                        arg("scalarHolder", DynaQLInputObject.inputObject(
                                                prop("booleanPrimitive", false),
                                                prop("booleanObject", Boolean.valueOf(true)),

                                                prop("bytePrimitive", Byte.MIN_VALUE),
                                                prop("byteObject", Byte.valueOf(Byte.MAX_VALUE)),

                                                prop("shortPrimitive", Short.MIN_VALUE),
                                                prop("shortObject", Short.valueOf(Short.MAX_VALUE)),

                                                prop("intPrimitive", Integer.MIN_VALUE + 1),
                                                prop("intObject", Integer.valueOf(Integer.MAX_VALUE)),

                                                prop("longPrimitive", Long.MIN_VALUE),
                                                prop("longObject", Long.valueOf(Long.MAX_VALUE)),

                                                prop("floatPrimitive", Float.MIN_VALUE),
                                                prop("floatObject", Float.valueOf(Float.MAX_VALUE)),

                                                prop("doublePrimitive", Double.MIN_VALUE),
                                                prop("doubleObject", Double.valueOf(Double.MAX_VALUE)),

                                                prop("bigInteger", BigInteger.TEN),
                                                prop("bigDecimal", BigDecimal.TEN),

                                                prop("charPrimitive", 'a'),
                                                prop("charObject", Character.valueOf('Z')),

                                                prop("stringObject", "Hello World !")
                                        ))),
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
        );

        String generatedRequest = document.build();
        //System.out.println(generatedRequest);
        AssertGraphQL.assertEquivalentGraphQLRequest(expectedRequest, generatedRequest);
    }
}
