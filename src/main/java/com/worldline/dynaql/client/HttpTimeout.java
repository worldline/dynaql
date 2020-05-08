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
package com.worldline.dynaql.client;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author jefrajames
 */
public class HttpTimeout {
    
    private final long value;
    private final TimeUnit timeUnit;
    
    public HttpTimeout(long value, TimeUnit timeUnit) {
        
         if ( value<=0 )
            throw new IllegalArgumentException("Timeout value must be positive: " + value);
        
        if ( timeUnit!= TimeUnit.SECONDS && timeUnit!= TimeUnit.MILLISECONDS )
            throw new IllegalArgumentException("TimeUnit must be SECONDS or MILLISECONDS: " + timeUnit);
        
        this.value=value;
        this.timeUnit= timeUnit;
    }

    public long getValue() {
        return value;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    
}
