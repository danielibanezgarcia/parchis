package ai.portia.parchis.game;

import ai.portia.parchis.core.*;
import ai.portia.parchis.core.GoalSquare;
import ai.portia.parchis.core.HomeSquare;
import ai.portia.parchis.core.NormalSquare;
import ai.portia.parchis.core.RampSquare;
import ai.portia.parchis.move.Move;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameStatus {

    public static final int MAX_PIECES_AT_START = 2;

    private final Board board;
    private final Map<Piece, Square> squareByPiece;

    public GameStatus(final Board board) {
        this.board = board;
        this.squareByPiece = initializeSquareByPiece();
    }

    private Map<Piece, Square> initializeSquareByPiece() {
        final Map<Piece, Square> squareByPiece = new HashMap<>();
        for (final Color color : board.getColors()) {
            final HomeSquare initialSquare = board.getHomeSquare(color);
            final List<Piece> pieces = board.getPieces(color);
            for (final Piece piece : pieces) squareByPiece.put(piece, initialSquare);
        }
        return squareByPiece;
    }

    public Board getBoard() {
        return board;
    }

    public boolean isAtHome(final Piece piece) {
        return board.isHomeSquare(getSquare(piece));
    }

    public Square getSquare(final Piece piece) {
        return squareByPiece.get(piece);
    }

    public List<Piece> getPiecesAtNormalSquares(final Color color) {
        final List<Piece> piecesAtNormalSquares = new ArrayList<>();
        for (final Piece piece : board.getPieces(color)) {
            if (board.isNormalSquare(getSquare(piece))) {
                piecesAtNormalSquares.add(piece);
            }
        }
        return piecesAtNormalSquares;
    }

    public List<Piece> getPiecesAtRampSquares(final Color color) {
        final List<Piece> piecesAtRampSquares = new ArrayList<>();
        for (final Piece piece : board.getPieces(color)) {
            if (board.isRampSquare(getSquare(piece))) {
                piecesAtRampSquares.add(piece);
            }
        }
        return piecesAtRampSquares;
    }

    public List<Piece> getPiecesAtHomeSquare(final Color color) {
        final List<Piece> piecesAtHomeSquare = new ArrayList<>();
        for (final Piece piece : board.getPieces(color)) {
            if (board.isHomeSquare(getSquare(piece))) {
                piecesAtHomeSquare.add(piece);
            }
        }
        return piecesAtHomeSquare;
    }

    public List<Piece> getPiecesAtGoalSquare(final Color color) {
        final List<Piece> piecesAtGoalSquare = new ArrayList<>();
        for (final Piece piece : board.getPieces(color)) {
            if (board.isGoalSquare(getSquare(piece))) {
                piecesAtGoalSquare.add(piece);
            }
        }
        return piecesAtGoalSquare;
    }

    public List<Piece> getPiecesAt(final Color color, final Square square) {
        final List<Piece> piecesAtTheSquare = new ArrayList<>();
        for (final Piece piece : board.getPieces(color)) {
            if (squareByPiece.get(piece) == square) {
                piecesAtTheSquare.add(piece);
            }
        }
        return piecesAtTheSquare;
    }

    public boolean areAllPiecesAtGoalSquare(final Color color) {
        final List<Piece> pieces = this.board.getPieces(color);
        for (final Piece piece : pieces) {
            final Square square = squareByPiece.get(piece);
            if (!board.isGoalSquare(square)) {
                return false;
            }
        }
        return true;
    }

    public void apply(final Move move) {
        setPieceAtSquare(move.getPiece(), move.getEndSquare());
    }

    public void apply(final List<Move> moves) {
        moves.forEach(this::apply);
    }

    private void setPieceAtSquare(final Piece piece, final Square square) {
        squareByPiece.put(piece, square);
    }

}
