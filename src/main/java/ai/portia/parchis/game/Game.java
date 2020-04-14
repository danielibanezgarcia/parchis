package ai.portia.parchis.game;

import ai.portia.parchis.core.*;
import ai.portia.parchis.formatter.GameStatusFormatter;
import ai.portia.parchis.move.Move;
import ai.portia.parchis.move.MoveFinder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class Game {

    private static final int MAX_NUMBER_OF_TURNS = 1000000; // 1 Million

    private final List<Player> players;
    private final Board board;
    private final GameStatus gameStatus;
    private final MoveFinder moveFinder;
    private int turn;

    public Game(final Collection<Player> players) {
        this.players = new ArrayList<>(players);
        this.board = Board.forNumberOfColors(players.size());
        this.gameStatus = new GameStatus(board);
        this.moveFinder = new MoveFinder(gameStatus);
        this.turn = 0;
    }

    public Player run() {
        this.turn = new Random().nextInt(players.size());
        Player winner;
        for (winner = null; winner == null; winner = runNextTurn()) { }
        return winner;
    }

    public Player runNextTurn() {
        if (this.turn > MAX_NUMBER_OF_TURNS) {
            throw new RuntimeException(String.format("Max number of turns (%s) reached", MAX_NUMBER_OF_TURNS));
        }
        final Player player = getPlayerForTurn(turn++);
        final List<Move> play = getNextPlay(player);
        gameStatus.apply(play);
//        GameStatusFormatter.of(gameStatus).output();
        return isWinner(player) ? player : null;
    }

    private Player getPlayerForTurn(final int turn) {
        return players.get(turn % players.size());
    }

    private List<Move> getNextPlay(final Player player) {
        final List<Move> play = new ArrayList<>();
        int diceNumber;
        do {
            diceNumber = player.rollDice();
            if (diceNumber == Board.REPEAT_ROLL_DICE_NUMBER && play.size() == 2) {
                play.add(getGoHomeMove(getLastPiece(play)));
                break;
            }
            final Move nextMove = getNextMove(player, diceNumber);
            if (nextMove != null) {
                play.add(nextMove);
            }
        } while (diceNumber == Board.REPEAT_ROLL_DICE_NUMBER);
        return play;
    }

    private Move getNextMove(final Player player, final int diceNumber) {
        final List<Move> availableMoves = moveFinder.findAvailableMoves(player.getColor(), diceNumber);
        return player.getStrategy().choose(availableMoves, gameStatus);
    }

    private boolean isWinner(final Player player) {
        return gameStatus.areAllPiecesAtGoalSquare(player.getColor());
    }

    private Piece getLastPiece(final List<Move> play) {
        return play.get(play.size()-1).getPiece();
    }

    private Move getGoHomeMove(final Piece lastPiece) {
        final Square startSquare = gameStatus.getSquare(lastPiece);
        final HomeSquare endSquare = board.getHomeSquare(lastPiece.getColor());
        return new Move(lastPiece, startSquare, endSquare);
    }

}
