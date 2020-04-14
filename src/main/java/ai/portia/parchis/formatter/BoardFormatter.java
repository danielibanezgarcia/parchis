package ai.portia.parchis.formatter;

import ai.portia.parchis.core.Board;
import ai.portia.parchis.core.Color;

public class BoardFormatter {

    public static final BoardFormatter getInstance() {
        return instance;
    }

    private static final BoardFormatter instance = new BoardFormatter();

    private BoardFormatter() { }

    public String format(final Board board) {
        StringBuilder homeSquaresText = new StringBuilder();
        StringBuilder normalSquaresText = new StringBuilder();
        StringBuilder rampSquaresText = new StringBuilder();
        StringBuilder goalSquaresText = new StringBuilder();
        for (final Color color : board.getColors()) {
            homeSquaresText.append(color).append(": ").append(board.getHomeSquare(color)).append("\n");
            normalSquaresText.append(color).append(": ").append(board.getNormalSquares(color)).append("\n");
            rampSquaresText.append(color).append(": ").append(board.getRampSquares(color)).append("\n");
            goalSquaresText.append(color).append(": ").append(board.getGoalSquare(color)).append("\n");
        }
        return "Board{" +
                "numberOfPieces=" + board.getNumberOfPieces() + "\n" +
                ", numberOfColors=" + board.getNumberOfColors() + "\n" +
                ", numberOfNormalSquares=" + board.getNumberOfNormalSquares() + "\n" +
                ", colors=" + board.getColors() + "\n" +
                ", homeSquares=\n" + homeSquaresText.toString() + '\n' +
                ", normalSquares=\n" + normalSquaresText.toString() + "\n" +
                ", rampSquaresByColor=\n" + rampSquaresText.toString() + "\n" +
                ", goalSquares=\n" + goalSquaresText.toString() + '\n' +
                '}';
    }

}
