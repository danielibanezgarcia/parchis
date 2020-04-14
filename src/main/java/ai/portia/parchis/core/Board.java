package ai.portia.parchis.core;

import java.util.*;
import java.util.stream.Collectors;

public class Board {

    public static final int REPEAT_ROLL_DICE_NUMBER = 6;
    public static final int START_FROM_HOME_DICE_NUMBER = 5;
    public static final int MAX_PIECES_PER_SQUARE = 2;

    private static final int NUMBER_OF_NORMAL_SQUARES_BY_COLOR = 17;
    private static final int NUMBER_OF_RAMP_SQUARES_BY_COLOR = 8;

    private final int numberOfPieces;
    private final int numberOfColors;
    private final int numberOfNormalSquares;
    private final List<Color> colors;
    private final Map<Color, List<Piece>> piecesByColor;
    private final Map<Color, HomeSquare> homeSquareByColor;
    private final Map<Color, List<NormalSquare>> normalSquaresByColor;
    private final Map<Color, List<RampSquare>> rampSquaresByColor;
    private final Map<Color, GoalSquare> goalSquareByColor;

    public static Board forNumberOfColors(final int numberOfColors) {
        if (numberOfColors < 1) {
            throw new IllegalArgumentException("Not enough number of colors");
        }
        final Board classic = Board.classic();
        if (numberOfColors > classic.numberOfColors) {
            throw new IllegalArgumentException("Too many colors");
        }
        return classic;
    }

    public static Board classic() {
        return new Board(4, 4);
    }

    private Board(final int numberOfPieces, final int numberOfColors) {
        this.numberOfPieces = numberOfPieces;
        this.numberOfColors = numberOfColors;
        this.numberOfNormalSquares = numberOfColors * NUMBER_OF_NORMAL_SQUARES_BY_COLOR;
        this.colors = createColors(this.numberOfColors);
        this.piecesByColor = createPiecesByColor(this.colors);
        this.normalSquaresByColor = createBoardNormalSquares(colors);
        this.homeSquareByColor = createHomeSquareByColor(this.colors);
        this.goalSquareByColor = createGoalSquares(this.colors);
        this.rampSquaresByColor = createRampSquares(this.colors);
    }

    private List<Color> createColors(final int numberOfColors) {
        if (numberOfColors < 1) {
            throw new IllegalArgumentException("Number of colors must be >= 1");
        }
        if (numberOfColors > Color.values().length) {
            throw new IllegalArgumentException(String.format("Number of colors must be <= %s", Color.values().length));
        }
        return Arrays.stream(Color.values()).filter(color -> color.ordinal() < numberOfColors).collect(Collectors.toList());
    }

    private Map<Color, List<Piece>> createPiecesByColor(final Collection<Color> colors) {
        final Map<Color, List<Piece>> piecesByColor = new HashMap<>();
        for (final Color color : colors) {
            final List<Piece> pieces = new ArrayList<>();
            for (int i = 0; i < this.numberOfPieces; i++) {
                pieces.add(new Piece(color, i));
            }
            piecesByColor.put(color, pieces);
        }
        return piecesByColor;
    }

    private Map<Color, List<NormalSquare>> createBoardNormalSquares(final Collection<Color> colors) {
        final Map<Color, List<NormalSquare>> boardSquares = new HashMap<>();
        int currentId = 0;
        for (final Color color : colors) {
            final List<NormalSquare> colorSquares = createColorNormalSquares(currentId, color);
            boardSquares.put(color, colorSquares);
            currentId += NUMBER_OF_NORMAL_SQUARES_BY_COLOR;
        }
        return boardSquares;
    }

    private Map<Color, HomeSquare> createHomeSquareByColor(final Collection<Color> colors) {
        final Map<Color, HomeSquare> homeSquareByColor = new HashMap<>();
        for (final Color color : colors) {
            homeSquareByColor.put(color, new HomeSquare(color));
        }
        return homeSquareByColor;
    }

    private Map<Color, GoalSquare> createGoalSquares(final Collection<Color> colors) {
        final Map<Color, GoalSquare> goalSquareByColor = new HashMap<>();
        for (final Color color : colors) {
            goalSquareByColor.put(color, new GoalSquare(color));
        }
        return goalSquareByColor;
    }

    private Map<Color, List<RampSquare>> createRampSquares(final Collection<Color> colors) {
        final Map<Color, List<RampSquare>> rampSquaresByColor = new HashMap<>();
        for (final Color color : colors) {
            final List<RampSquare> rampSquares = createRampSquaresForColor(color);
            rampSquaresByColor.put(color, rampSquares);
        }
        return rampSquaresByColor;
    }

    private List<NormalSquare> createColorNormalSquares(final int startId, final Color color) {
        final List<NormalSquare> squares = new ArrayList<>();
        for (int relativeIndex = 0; relativeIndex < NUMBER_OF_NORMAL_SQUARES_BY_COLOR; relativeIndex++) {
            squares.add(createNormalSquare(startId, relativeIndex, color));
        }
        return squares;
    }

    private List<RampSquare> createRampSquaresForColor(final Color color) {
        final List<RampSquare> squares = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_RAMP_SQUARES_BY_COLOR - 1; i++) {
            squares.add(new RampSquare(color, i, NUMBER_OF_RAMP_SQUARES_BY_COLOR - (i + 1)));
        }
        return squares;
    }

    private NormalSquare createNormalSquare(final int startId, final int relativeIndex, final Color color) {
        final String displayId = getDisplayId(startId + relativeIndex);
        return new NormalSquare(relativeIndex, displayId, color);
    }

    private String getDisplayId(final int id) {
        return String.valueOf(id == 0 ? this.numberOfNormalSquares : id);
    }

    public int getNumberOfNormalSquaresByColor() {
        return NUMBER_OF_NORMAL_SQUARES_BY_COLOR;
    }

    public List<Color> getColors() {
        return new ArrayList<>(colors);
    }

    public List<Piece> getPieces(final Color color) {
        return new ArrayList<>(piecesByColor.get(color));
    }

    public HomeSquare getHomeSquare(final Piece piece) {
        return getHomeSquare(piece.getColor());
    }

    public HomeSquare getHomeSquare(final Color color) {
        return this.homeSquareByColor.get(color);
    }

    public List<NormalSquare> getNormalSquares(final Color color) {
        return new ArrayList<>(normalSquaresByColor.get(color));
    }

    public List<RampSquare> getRampSquares(final Color color) {
        return new ArrayList<>(rampSquaresByColor.get(color));
    }

    public GoalSquare getGoalSquare(final Color color) {
        return this.goalSquareByColor.get(color);
    }

    public NormalSquare getStartSquare(final Piece piece) {
        return getStartSquare(piece.getColor());
    }

    public NormalSquare getStartSquare(final Color color) {
        final List<NormalSquare> squares = normalSquaresByColor.get(color);
        for (final NormalSquare square : squares) {
            if (square.isStart()) {
                return square;
            }
        }
        return null;
    }

    public Square findTargetSquare(final Square currentSquare, final int movements, final Color color) {
        if (isHomeSquare(currentSquare)) {
            return findTargetFromHomeSquare((HomeSquare) currentSquare, movements);
        }
        if (isNormalSquare(currentSquare)) {
            return findTargetFromNormalSquare((NormalSquare) currentSquare, movements, color);
        }
        if (isRampSquare(currentSquare)) {
            return findTargetFromRampSquare((RampSquare) currentSquare, movements);
        }
        return null;
    }

    public boolean isHomeSquare(final Square square) {
        return (square instanceof HomeSquare);
    }

    public boolean isNormalSquare(final Square square) {
        return (square instanceof NormalSquare);
    }

    public boolean isRampSquare(final Square square) {
        return (square instanceof RampSquare);
    }

    public boolean isGoalSquare(final Square square) {
        return (square instanceof GoalSquare);
    }

    public int getNumberOfNormalSquares() {
        return numberOfNormalSquares;
    }

    public int getNumberOfPieces() {
        return numberOfPieces;
    }

    public int getNumberOfColors() {
        return numberOfColors;
    }

    private Square findTargetFromHomeSquare(final HomeSquare currentSquare, final int movements) {
        if (movements != Board.START_FROM_HOME_DICE_NUMBER) {
            return null;
        }
        return getStartSquare(currentSquare.getColor());
    }

    private Square findTargetFromNormalSquare(final NormalSquare startSquare, final int movements, final Color playerColor) {
        NormalSquare currentSquare = startSquare;
        int takenMovements = 0;
        do {
            if (squareIsInRampEntrance(currentSquare, playerColor)) {
                return findRampTargetFromNormalSquare(currentSquare, movements - takenMovements);
            }
            currentSquare = getNextNormalSquare(currentSquare);
            takenMovements++;
        } while (takenMovements < movements);
        return currentSquare;
    }

    private NormalSquare getNextNormalSquare(final NormalSquare square) {
        final int currentAbsolutePosition = getAbsolutePosition(square);
        final int targetAbsolutePosition = moveAbsolutePositions(currentAbsolutePosition, 1);
        return getNormalSquareFromAbsolutePosition(targetAbsolutePosition);
    }

    private int getAbsolutePosition(final NormalSquare square) {
        return square.getColor().ordinal() * getNumberOfNormalSquaresByColor() + square.getIndexInColor();
    }

    private int moveAbsolutePositions(final int startAbsolutePosition, final int movements) {
        return (startAbsolutePosition + movements) % getNumberOfNormalSquares();
    }

    private NormalSquare getNormalSquareFromAbsolutePosition(final int absolutePosition) {
        final int targetColorIndex = absolutePosition / getNumberOfNormalSquaresByColor();
        final int targetRelativeIndex = absolutePosition % getNumberOfNormalSquaresByColor();
        final Color color = colors.get(targetColorIndex);
        return getNormalSquares(color).get(targetRelativeIndex);
    }

    private boolean squareIsInRampEntrance(final NormalSquare square, final Color pieceColor) {
        return (square.isRampEntrance() && square.getColor().equals(pieceColor));
    }

    private GoalSquare getGoalSquare(final RampSquare currentSquare) {
        return getGoalSquare(currentSquare.getColor());
    }

    private RampSquare moveInsideTheRamp(final RampSquare currentSquare, final int movements) {
        final int targetSquareIndex = currentSquare.getIndexInColor() + movements;
        return getRampSquares(currentSquare.getColor()).get(targetSquareIndex);
    }

    private Square findRampTargetFromNormalSquare(final NormalSquare normalSquare, final int movements) {
        final RampSquare firstRampSquare = getRampSquares(normalSquare.getColor()).get(0);
        final int movementsLeftInRamp = movements - 1;
        return findTargetFromRampSquare(firstRampSquare, movementsLeftInRamp);
    }

    private Square findTargetFromRampSquare(final RampSquare currentSquare, final int movements) {
        if (movements == 0) {
            return currentSquare;
        }
        if (targetIsOverGoal(currentSquare, movements)) {
            return null;
        }
        if (targetIsTheGoal(currentSquare, movements)) {
            return getGoalSquare(currentSquare);
        }
        return moveInsideTheRamp(currentSquare, movements);
    }

    private boolean targetIsOverGoal(final RampSquare currentSquare, final int movements) {
        return (movements > currentSquare.getDistanceToGoalSquare());
    }

    private boolean targetIsTheGoal(final RampSquare currentSquare, final int movements) {
        return (movements == currentSquare.getDistanceToGoalSquare());
    }

}
