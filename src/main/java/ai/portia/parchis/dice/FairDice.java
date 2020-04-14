package ai.portia.parchis.dice;

import java.util.Random;

public class FairDice implements Dice {

    public static final FairDice sixSided() {
        return withSides(DiceSides.SIX);
    }

    public static FairDice withSides(final DiceSides sides) {
        return new FairDice(sides.getValue());
    }

    private final int sides;

    private FairDice(final int sides) {
        this.sides = sides;
    }

    @Override
    public int roll() {
        return new Random().nextInt(sides) + 1;
    }

}
