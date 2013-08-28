package com.google.appengine.tck.endpoints.support;

import com.google.api.server.spi.config.Serializer;

/**
 * @author <a href="mailto:mluksa@redhat.com">Marko Luksa</a>
 */
public class EndPointResponse2CustomSerializer implements Serializer<EndPointResponse2, String> {

    public static final String PREFIX = "other custom serializer output: ";

    @Override
    public String serialize(EndPointResponse2 endPointResponse2) {
        return PREFIX + endPointResponse2.getResponse();
    }

    @Override
    public EndPointResponse2 deserialize(String s) {
        return new EndPointResponse2(s.substring(PREFIX.length()));
    }
}
