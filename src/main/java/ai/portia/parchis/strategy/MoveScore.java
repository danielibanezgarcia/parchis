package ai.portia.parchis.strategy;

import ai.portia.parchis.move.Move;

public class MoveScore {

    private final Move move;
    private final double score;

    public MoveScore(final Move move, final double score) {
        this.move = move;
        this.score = score;
    }

    public Move getMove() {
        return move;
    }

    public double getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "MoveScore{" +
                "move=" + move +
                ", score=" + score +
                '}';
    }
}
