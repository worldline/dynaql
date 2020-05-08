/*
 * Copyright 2020 jefrajames.
 *
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
package com.worldline.dynaql;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 * @author jefrajames
 */
public class ClientBuilderTest {

    @Test
    public void testEmptyBuilder() {
        ClientBuilder builder = ClientBuilder.newBuilder();
        assertNotNull(builder);
    }

    @Test
    public void testTimeout() {
        ClientBuilder builder = ClientBuilder.newBuilder();
        builder.connectTimeout(5, TimeUnit.SECONDS);
        builder.readTimeout(500, TimeUnit.MILLISECONDS);

        assertEquals(builder.getConnectTimeout().getValue(), 5);
        assertEquals(builder.getConnectTimeout().getTimeUnit(), TimeUnit.SECONDS);

        assertEquals(builder.getReadTimeout().getValue(), 500);
        assertEquals(builder.getReadTimeout().getTimeUnit(), TimeUnit.MILLISECONDS);

    }

    @Test
    public void testIllegalTimeout() {
        assertThrows(IllegalArgumentException.class, () -> {
            ClientBuilder.newBuilder().connectTimeout(-1, TimeUnit.MINUTES);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            ClientBuilder.newBuilder().connectTimeout(10, TimeUnit.DAYS);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            ClientBuilder.newBuilder().readTimeout(0, TimeUnit.MINUTES);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            ClientBuilder.newBuilder().readTimeout(1, TimeUnit.HOURS);
        });

    }

}
