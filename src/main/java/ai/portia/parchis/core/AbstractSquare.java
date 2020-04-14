package ai.portia.parchis.core;

import ai.portia.parchis.core.Color;
import ai.portia.parchis.core.Square;

public abstract class AbstractSquare implements Square {

    private final Color color;

    protected AbstractSquare(final Color color) {
        this.color = color;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Square{" +
                "color=" + color +
                '}';
    }
}
