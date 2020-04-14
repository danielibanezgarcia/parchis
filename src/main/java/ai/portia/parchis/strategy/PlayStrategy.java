package ai.portia.parchis.strategy;

import ai.portia.parchis.game.GameStatus;
import ai.portia.parchis.move.Move;

import java.util.List;

public interface PlayStrategy {
    Move choose(List<Move> moves, GameStatus status);
}
