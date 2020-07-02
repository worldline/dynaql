/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.worldline.graphql.dynaql.impl.jaxrs;

import com.worldline.graphql.dynaql.impl.DynaQLRequest;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * This is an implementation specific class and should not be in the
 * specification API.
 *
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class GraphQLRequestWriter implements MessageBodyWriter<DynaQLRequest> {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GraphQLRequestWriter.class);

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return type == DynaQLRequest.class;
    }

    @Override
    public void writeTo(DynaQLRequest request, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        String jsonRequest = request.toJson();
        log.warn("Sending GraphQL request: " + jsonRequest);
        Writer writer = new PrintWriter(entityStream);
        writer.write(jsonRequest);
        writer.flush();
        writer.close();
    }

}
