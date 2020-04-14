package ai.portia.parchis.strategy;

import ai.portia.parchis.game.GameStatus;
import ai.portia.parchis.move.Move;

import java.util.Random;

public class RandomPlayStrategy extends AbstractPlayStrategy implements PlayStrategy {

    private static final RandomPlayStrategy instance = new RandomPlayStrategy();

    private RandomPlayStrategy() {
    }

    public static final RandomPlayStrategy getInstance() {
        return instance;
    }

    @Override
    protected double score(Move move, final GameStatus status) {
        return new Random().nextDouble();
    }
}
