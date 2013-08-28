/*
 * Copyright 2013 Google Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.appengine.tck.endpoints.support;

import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;

import static com.google.api.server.spi.config.ApiMethod.HttpMethod;

/**
 * @author <a href="mailto:mluksa@redhat.com">Marko Luksa</a>
 */
@Api(name = EndPointWithCustomSerializer.NAME/*, serializers = EndPointResponseSerializer.class*/)
public class EndPointWithCustomSerializer {

    public static final String NAME = "endPointWithCustomSerializer";

//    @ApiMethod(httpMethod = HttpMethod.POST)
//    public EndPointResponse test(EndPointResponse request) {
//        return new EndPointResponse("method test was invoked");
//    }
//
//    @ApiMethod(httpMethod = HttpMethod.POST)
//    public EndPointResponse2 test2(EndPointResponse2 request) {
//        return new EndPointResponse2("method test2 was invoked");
//    }

    @ApiMethod(name = "bar.doSomething", httpMethod = HttpMethod.POST)
    public Bar doSomething(Bar bar) {
        return new Bar(1, 2);
    }
}
