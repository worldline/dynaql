package core;

import com.worldline.graphql.dynaql.api.core.Document;
import com.worldline.graphql.dynaql.api.core.OperationType;
import com.worldline.graphql.dynaql.api.core.Variable;
import com.worldline.graphql.dynaql.impl.core.exceptions.BuilderException;
import helper.AssertGraphQL;
import helper.Utils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.worldline.graphql.dynaql.api.core.ScalarType.GQL_BOOL;
import static com.worldline.graphql.dynaql.api.core.ScalarType.GQL_FLOAT;
import static com.worldline.graphql.dynaql.api.core.ScalarType.GQL_ID;
import static com.worldline.graphql.dynaql.api.core.ScalarType.GQL_INT;
import static com.worldline.graphql.dynaql.api.core.ScalarType.GQL_STRING;
import static com.worldline.graphql.dynaql.impl.core.DynaQLArgument.arg;
import static com.worldline.graphql.dynaql.impl.core.DynaQLArgument.args;
import static com.worldline.graphql.dynaql.impl.core.DynaQLVariable.var;
import static com.worldline.graphql.dynaql.impl.core.DynaQLDocument.document;
import static com.worldline.graphql.dynaql.impl.core.DynaQLField.field;
import static com.worldline.graphql.dynaql.impl.core.DynaQLInputObject.inputObject;
import static com.worldline.graphql.dynaql.impl.core.DynaQLInputObjectField.prop;
import static com.worldline.graphql.dynaql.impl.core.DynaQLOperation.operation;
import static com.worldline.graphql.dynaql.impl.core.DynaQLVariable.vars;
import static com.worldline.graphql.dynaql.impl.core.DynaQLVariableType.list;
import static com.worldline.graphql.dynaql.impl.core.DynaQLVariableType.nonNull;

public class VariablesTest {

    @Test
    public void variablesDefaultValueTest() throws IOException, URISyntaxException, BuilderException {
        String expectedRequest = Utils.getResourceFileContent("core/variablesDefaultValue.graphql");

        Variable varName = var("name", GQL_STRING, "Lee Byron");

        Document document = document(
                operation(OperationType.QUERY,
                        vars(varName),
                        field("helloYou", arg("name", varName))
                )
        );

        String generatedRequest = document.build();
        //System.out.println(generatedRequest);
        AssertGraphQL.assertEquivalentGraphQLRequest(expectedRequest, generatedRequest);
    }

    @Test
    public void variablesFlatTest() throws IOException, URISyntaxException, BuilderException {
        String expectedRequest = Utils.getResourceFileContent("core/variablesFlat.graphql");

        Variable varBool = var("varBool", nonNull(GQL_BOOL));
        Variable varDouble = var("varDouble", nonNull(GQL_FLOAT));
        Variable varString = var("varString", nonNull(GQL_STRING));

        Document document = document(
                operation(OperationType.QUERY,
                        vars(
                                varBool,
                                varDouble,
                                varString
                        ),
                        field("withArgWithSubField", args(
                                arg("aString", varString),
                                arg("aDouble", varDouble),
                                arg("aBool", varBool)
                                ),
                                field("bool"),
                                field("double"),
                                field("string")
                        )
                )
        );

        String generatedRequest = document.build();
        //System.out.println(generatedRequest);
        AssertGraphQL.assertEquivalentGraphQLRequest(expectedRequest, generatedRequest);
    }

    @Test
    public void variablesInInputObjectTest() throws IOException, URISyntaxException, BuilderException {
        String expectedRequest = Utils.getResourceFileContent("core/variablesInInputObject.graphql");

        Variable varBool = var("varBool", nonNull(GQL_BOOL));
        Variable varInt = var("varInt", nonNull(GQL_INT));
        Variable varFloat = var("varFloat", nonNull(GQL_FLOAT));
        Variable varString = var("varString", nonNull(GQL_STRING));
        Variable varID = var("varID", GQL_ID);

        Document document = document(
                operation(OperationType.QUERY,
                        vars(
                                varBool,
                                varInt,
                                varFloat,
                                varString,
                                varID
                        ),
                        field("basicScalarHolder", args(
                                arg("basicScalarHolder", inputObject(
                                        prop("bool", varBool),
                                        prop("int", varInt),
                                        prop("float", varFloat),
                                        prop("string", varString),
                                        prop("iD", varID)
                                ))),
                                field("bool"),
                                field("int"),
                                field("float"),
                                field("string"),
                                field("iD")
                        )
                )
        );

        String generatedRequest = document.build();
        AssertGraphQL.assertEquivalentGraphQLRequest(expectedRequest, generatedRequest);
    }

    @Test
    public void variablesArraysTest() throws IOException, URISyntaxException, BuilderException {
        String expectedRequest = Utils.getResourceFileContent("core/variablesArrays.graphql");

        Variable varInt_1 = var("varInt_1", list(GQL_INT));
        Variable varInt_1_bang = var("varInt_1_bang", nonNull(list(GQL_INT)));
        Variable varInt_bang_1 = var("varInt_bang_1", list(nonNull(GQL_INT)));
        Variable varInt_1_2 = var("varInt_1_2", list(list(GQL_INT)));
        Variable varInt_1_2_3 = var("varInt_1_2_3", list(list(list(GQL_INT))));
        Variable varInt_1_bang_2_3_bang = var("varInt_1_bang_2_3_bang",
                nonNull(list(
                        list(
                                nonNull(list(GQL_INT))))));
        Variable varInt_bang_1_bang_2_bang_3_bang = var("varInt_bang_1_bang_2_bang_3_bang",
                nonNull(list(
                        nonNull(list(
                                nonNull(list(
                                        nonNull(GQL_INT))))))));

        /* To use for e2e tests
            {
              "varInt_1": [12, 34, 567, 89],
              "varInt_1_2": null,
              "varInt_1_2_3": [[[1, null], [3, 4]], null, [[7], null]],
              "varInt_1_bang": [null],
              "varInt_bang_1": null,
              "varInt_1_bang_2_3_bang": [[[null, 2], [null, 4]], [[5, 6]], null],
              "varInt_bang_1_bang_2_bang_3_bang": [[[1, 2], [3, 4]], [[5, 6], [7], [8, 9]]]
            }
         */

        Document document = document(
                operation(OperationType.MUTATION,
                        vars(
                                varInt_1,
                                varInt_1_2,
                                varInt_1_2_3,
                                varInt_1_bang,
                                varInt_bang_1,
                                varInt_1_bang_2_3_bang,
                                varInt_bang_1_bang_2_bang_3_bang
                        ),
                        field("nestedArraysHolder", args(
                                arg("nestedArraysHolder", inputObject(
                                        prop("int_1", varInt_1),
                                        prop("int_1_2", varInt_1_2),
                                        prop("int_1_2_3", varInt_1_2_3),
                                        prop("int_1_bang", varInt_1_bang),
                                        prop("int_bang_1", varInt_bang_1),
                                        prop("int_1_bang_2_3_bang", varInt_1_bang_2_3_bang),
                                        prop("int_bang_1_bang_2_bang_3_bang", varInt_bang_1_bang_2_bang_3_bang)
                                ))),
                                field("int_1"),
                                field("int_1_2"),
                                field("int_1_2_3"),
                                field("int_1_bang"),
                                field("int_1_bang_2_3_bang"),
                                field("int_bang_1"),
                                field("int_bang_1_bang_2_bang_3_bang")
                        )
                )
        );

        String generatedRequest = document.build();
        //System.out.println(generatedRequest);
        AssertGraphQL.assertEquivalentGraphQLRequest(expectedRequest, generatedRequest);
    }
}
