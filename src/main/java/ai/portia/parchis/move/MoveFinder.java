package ai.portia.parchis.move;

import ai.portia.parchis.core.*;
import ai.portia.parchis.game.GameStatus;

import java.util.ArrayList;
import java.util.List;

public class MoveFinder {

    private final GameStatus status;
    private final Board board;

    public MoveFinder(final GameStatus status) {
        this.status = status;
        this.board = status.getBoard();
    }

    public List<Move> findAvailableMoves(final Color color, final int diceNumber) {
        final List<Move> availableMoves = new ArrayList<>();
        for (final Piece piece : board.getPieces(color)) {
            final Move move = findMove(piece, diceNumber);
            if (move != null) {
                availableMoves.add(move);
            }
        }
        return availableMoves;
    }

    private Move findMove(final Piece piece, final int diceNumber) {
        final Square currentSquare = status.getSquare(piece);
        final Square targetSquare = board.findTargetSquare(currentSquare, diceNumber, piece.getColor());
        if (targetSquare == null) {
            return null;
        }
        return new Move(piece, currentSquare, targetSquare);
    }

}
