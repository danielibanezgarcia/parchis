package ai.portia.parchis;

import ai.portia.parchis.core.Color;
import ai.portia.parchis.game.Player;
import ai.portia.parchis.dice.BiasedDice;
import ai.portia.parchis.dice.Dice;
import ai.portia.parchis.dice.FairDice;
import ai.portia.parchis.game.Game;
import ai.portia.parchis.strategy.BasicPlayStrategy;
import ai.portia.parchis.strategy.RandomPlayStrategy;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        final Dice biasedDice = BiasedDice.sixSided(0.16, 0.16, 0.16, 0.16, 0.20, 0.16);
        final Dice fairDice = FairDice.sixSided();
        final List<Player> players = Arrays.asList(
                new Player("1", "Daniel", Color.RED, BasicPlayStrategy.getInstance(), fairDice),
                new Player("2", "Pili", Color.BLUE, RandomPlayStrategy.getInstance(), fairDice),
                new Player("3", "Vega", Color.YELLOW, RandomPlayStrategy.getInstance(), fairDice),
                new Player("4", "Azahara", Color.GREEN, RandomPlayStrategy.getInstance(), fairDice)
        );
        final int[] gamesWon = new int[players.size()];
        for (int i = 0; i < 12000; i++) {
            final Player winner = new Game(players).run();
            gamesWon[Integer.parseInt(winner.getId()) - 1]++;
        }
        System.out.println(Arrays.toString(gamesWon));
    }

}
