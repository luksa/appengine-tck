package com.google.appengine.tck.endpoints.support;

import com.google.api.server.spi.config.ApiSerializer;

/**
 *
 */
@ApiSerializer(BarSerializer.class)
public class Bar {
    private final int x;
    private final int y;

    public Bar(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
