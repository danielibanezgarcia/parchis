package ai.portia.parchis.strategy;

import ai.portia.parchis.game.GameStatus;
import ai.portia.parchis.move.Move;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractPlayStrategy implements PlayStrategy {

    protected abstract double score(final Move move, final GameStatus status);

    @Override
    public Move choose(final List<Move> moves, final GameStatus status) {
        if (moves.isEmpty()) {
            return null;
        }
        final List<MoveScore> moveScores = moves.stream()
                .map(move -> new MoveScore(move, score(move, status)))
                .collect(Collectors.toList());
        moveScores.sort(Comparator.comparing(MoveScore::getScore).reversed());
        return moveScores.get(0).getMove();
    }
}
