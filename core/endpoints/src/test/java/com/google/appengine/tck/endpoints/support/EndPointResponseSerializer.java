package com.google.appengine.tck.endpoints.support;

import com.google.api.server.spi.config.Serializer;

/**
 * @author <a href="mailto:mluksa@redhat.com">Marko Luksa</a>
 */
public class EndPointResponseSerializer implements Serializer<EndPointResponse, String> {

    public static final String PREFIX = "custom serializer output: ";

    @Override
    public String serialize(EndPointResponse endPointResponse) {
        return PREFIX + endPointResponse.getResponse();
    }

    @Override
    public EndPointResponse deserialize(String s) {
        return new EndPointResponse(s.substring(PREFIX.length()));
    }
}
