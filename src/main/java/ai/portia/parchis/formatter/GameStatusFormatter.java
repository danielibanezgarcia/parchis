package ai.portia.parchis.formatter;

import ai.portia.parchis.core.*;
import ai.portia.parchis.game.GameStatus;
import ai.portia.parchis.util.FileUtils;
import org.apache.commons.text.StringSubstitutor;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class GameStatusFormatter {

    private static final String TEMPLATE = FileUtils.readFile("gameTemplate.txt");
    private static final String EMPTY_POSITION = "  ";

    private final GameStatus status;
    private final Board board;
    private final Map<Square, Set<Piece>> piecesAtSquare;

    public static GameStatusFormatter of(final GameStatus status) {
        return new GameStatusFormatter(status);
    }

    private GameStatusFormatter(final GameStatus status) {
        this.status = status;
        this.board = status.getBoard();
        this.piecesAtSquare = getPiecesAtSquareMap(this.status, this.board);
    }

    public String toString() {
        String formattedStatus = TEMPLATE;
        formattedStatus = drawHomePieces(formattedStatus);
        formattedStatus = drawGoalPieces(formattedStatus);
        formattedStatus = drawNormalPieces(formattedStatus);
        formattedStatus = drawRampPieces(formattedStatus);
        return formattedStatus;
    }

    public void output() {
        try {
            Runtime.getRuntime().exec("clear");
            System.out.println(GameStatusFormatter.of(status).toString());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<Square, Set<Piece>> getPiecesAtSquareMap(final GameStatus status, final Board board) {
        final Map<Square, Set<Piece>> squaresByPiece = new HashMap<>();
        for (final Color color : board.getColors()) {
            for (final Piece piece : board.getPieces(color)) {
                final Square square = status.getSquare(piece);
                final Set<Piece> pieces = squaresByPiece.computeIfAbsent(square, k -> new HashSet<>());
                pieces.add(piece);
            }
        }
        return squaresByPiece;
    }

    private String drawHomePieces(final String formattedStatus) {
        final Map<String, String> values = new HashMap<>();
        for (final Color color : board.getColors()) {
            final HomeSquare square = board.getHomeSquare(color);
            final Set<Piece> piecesAtHome = piecesAtSquare.getOrDefault(square, new HashSet<>());
            int i = 0;
            for (final Piece piece : board.getPieces(color)) {
                final String placeholderName = getPlaceholderName(square, i++);
                values.put(placeholderName, piecesAtHome.contains(piece) ? getDisplayName(piece) : EMPTY_POSITION);
            }
        }
        return StringSubstitutor.replace(formattedStatus, values, "{", "}");
    }

    private String drawGoalPieces(final String formattedStatus) {
        final Map<String, String> values = new HashMap<>();
        for (final Color color : board.getColors()) {
            final GoalSquare square = board.getGoalSquare(color);
            final Set<Piece> piecesAtGoal = piecesAtSquare.getOrDefault(square, new HashSet<>());
            int i = 0;
            for (final Piece piece : board.getPieces(color)) {
                final String placeholderName = getPlaceholderName(square, i++);
                values.put(placeholderName, piecesAtGoal.contains(piece) ? getDisplayName(piece) : EMPTY_POSITION);
            }
        }
        return StringSubstitutor.replace(formattedStatus, values, "{", "}");
    }

    private String getColorInitial(final Color color) {
        return color.name().substring(0, 1).toUpperCase();
    }

    private String getDisplayName(final Piece piece) {
        return getColorInitial(piece.getColor()) + piece.getIndexInColor();
    }

    private String drawNormalPieces(final String formattedStatus) {
        final Map<String, String> values = new HashMap<>();
        for (final Color color : board.getColors()) {
            final List<NormalSquare> squares = board.getNormalSquares(color);
            for (final NormalSquare square : squares) {
                final List<Piece> pieces = new ArrayList<>(piecesAtSquare.getOrDefault(square, Collections.emptySet()));
                for (int i = 0; i < 2; i++) {
                    final Piece piece = i < pieces.size() ? pieces.get(i) : null;
                    final String placeholderName = getPlaceholderName(square, i);
                    values.put(placeholderName, piece != null ? getDisplayName(piece) : EMPTY_POSITION);
                }
            }
        }
        return StringSubstitutor.replace(formattedStatus, values, "{", "}");
    }

    private String drawRampPieces(final String formattedStatus) {
        final Map<String, String> values = new HashMap<>();
        for (final Color color : board.getColors()) {
            final List<RampSquare> squares = board.getRampSquares(color);
            for (final RampSquare square : squares) {
                final List<Piece> pieces = piecesAtSquare.getOrDefault(square, Collections.emptySet()).stream().sorted(Comparator.comparing(Piece::getIndexInColor)).collect(Collectors.toList());
                for (int i = 0; i < board.getNumberOfPieces(); i++) {
                    final Piece piece = i < pieces.size() ? pieces.get(i) : null;
                    final String placeholderName = getPlaceholderName(square, i);
                    values.put(placeholderName, piece != null ? getDisplayName(piece) : EMPTY_POSITION);
                }
            }
        }
        return StringSubstitutor.replace(formattedStatus, values, "{", "}");
    }

    private String getPlaceholderName(final HomeSquare square, final int pieceIndex) {
        return "H" + getColorInitial(square.getColor()) + pieceIndex;
    }

    private String getPlaceholderName(final GoalSquare square, final int pieceIndex) {
        return "G" + getColorInitial(square.getColor()) + pieceIndex;
    }

    private String getPlaceholderName(final NormalSquare square, final int pieceIndex) {
        return "N" + getColorInitial(square.getColor()) + square.getIndexInColor() + pieceIndex;
    }

    private String getPlaceholderName(final RampSquare square, final int pieceIndex) {
        return "R" + getColorInitial(square.getColor()) + square.getIndexInColor() + pieceIndex;
    }


}
