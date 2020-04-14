package ai.portia.parchis.strategy;

import ai.portia.parchis.core.NormalSquare;
import ai.portia.parchis.game.GameStatus;
import ai.portia.parchis.move.Move;

import java.util.Random;

public class BasicPlayStrategy extends AbstractPlayStrategy implements PlayStrategy {

    private static final BasicPlayStrategy instance = new BasicPlayStrategy();

    private BasicPlayStrategy() {
    }

    public static final BasicPlayStrategy getInstance() {
        return instance;
    }

    @Override
    protected double score(final Move move, final GameStatus status) {
        if (isMoveToGetOutOfHome(move)) {
            return 1.0;
        }
        return new Random().nextDouble();
    }

    private boolean isMoveToGetOutOfHome(final Move move) {
        if (!(move.getEndSquare() instanceof  NormalSquare)) {
            return false;
        }
        return ((NormalSquare) move.getEndSquare()).isStart();
    }
}
