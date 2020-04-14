package ai.portia.parchis.game;

import ai.portia.parchis.core.Color;
import ai.portia.parchis.dice.Dice;
import ai.portia.parchis.strategy.PlayStrategy;

public class Player {

    private final String id;
    private final String name;
    private final Color color;
    private final Dice dice;
    private final PlayStrategy strategy;

    public Player(final String id, final String name, final Color color, final PlayStrategy strategy, final Dice dice) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.dice = dice;
        this.strategy = strategy;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public int rollDice() {
        return dice.roll();
    }

    public PlayStrategy getStrategy() {
        return strategy;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", color=" + color +
                ", dice=" + dice +
                ", strategy=" + strategy +
                '}';
    }
}
