package com.worldline.graphql.dynaql.api.core;

import com.worldline.graphql.dynaql.impl.core.DynaQLOperation;
import java.util.List;

public interface Document extends Buildable {
    List<DynaQLOperation> getOperations();
    void setOperations(List<DynaQLOperation> operations);
}
