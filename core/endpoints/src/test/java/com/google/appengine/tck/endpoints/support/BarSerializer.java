package com.google.appengine.tck.endpoints.support;

import com.google.api.server.spi.config.Serializer;

/**
 *
 */
public class BarSerializer implements Serializer<Bar, String> {
    public String serialize(Bar in) {
        return in.getX() + "," + in.getY();
    }

    public Bar deserialize(String in) {
        String[] xy = in.split(",");
        return new Bar(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
    }
}