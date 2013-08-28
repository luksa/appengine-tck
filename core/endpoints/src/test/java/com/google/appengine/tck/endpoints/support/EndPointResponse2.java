package com.google.appengine.tck.endpoints.support;

import com.google.api.server.spi.config.ApiSerializer;

/**
 * @author <a href="mailto:mluksa@redhat.com">Marko Luksa</a>
 */
/*@ApiSerializer(EndPointResponse2CustomSerializer.class)*/
public class EndPointResponse2 extends EndPointResponse {

    public EndPointResponse2(String response) {
        super(response);
    }
}
