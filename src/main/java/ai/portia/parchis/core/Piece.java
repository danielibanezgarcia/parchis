package ai.portia.parchis.core;

import java.util.Objects;

public class Piece {

    private final Color color;
    private final int indexInColor;

    public Piece(final Color color, final int indexInColor) {
        this.color = color;
        this.indexInColor = indexInColor;
    }

    public Color getColor() {
        return color;
    }

    public int getIndexInColor() {
        return indexInColor;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Piece piece = (Piece) o;
        return indexInColor == piece.indexInColor &&
                color == piece.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, indexInColor);
    }

    @Override
    public String toString() {
        return "Piece{" +
                "color=" + color +
                ", indexInColor=" + indexInColor +
                '}';
    }
}
