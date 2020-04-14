package ai.portia.parchis.move;

import ai.portia.parchis.core.Piece;
import ai.portia.parchis.core.Square;
import ai.portia.parchis.game.GameStatus;

public class Move {

    private final Piece piece;
    private final Square startSquare;
    private final Square endSquare;

    public Move(final Piece piece, final Square startSquare, final Square endSquare) {
        this.piece = piece;
        this.startSquare = startSquare;
        this.endSquare = endSquare;
    }

    public Piece getPiece() {
        return piece;
    }

    public Square getStartSquare() {
        return startSquare;
    }

    public Square getEndSquare() {
        return endSquare;
    }

    @Override
    public String toString() {
        return "MovePlay{" +
                "piece=" + piece +
                ", startSquare=" + startSquare +
                ", endSquare=" + endSquare +
                '}';
    }
}
