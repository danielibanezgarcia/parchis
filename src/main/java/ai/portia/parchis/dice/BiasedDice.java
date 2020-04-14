package ai.portia.parchis.dice;

import java.util.Arrays;
import java.util.Random;

public class BiasedDice implements Dice {

    private final int numberOfSides;
    private final double[] accumSideAppearanceProb;
    private final Random random;

    public static BiasedDice sixSided(final double... biasedSideAppearanceProb) {
        return new BiasedDice(DiceSides.SIX, biasedSideAppearanceProb);
    }

    public static BiasedDice withSides(final DiceSides sides, final double... biasedSideAppearanceProb) {
        return new BiasedDice(sides, biasedSideAppearanceProb);
    }

    private BiasedDice(final DiceSides diceSides, final double[] sideProbabilities) {
        final double sumOfProbs = Arrays.stream(sideProbabilities).sum();
        if (sumOfProbs != 1.0) {
            throw new IllegalArgumentException("Probabilities should add up to 1");
        }
        if (sideProbabilities.length != diceSides.getValue()) {
            throw new IllegalArgumentException("Sides probabilities array must match number of sides");
        }
        this.numberOfSides = diceSides.getValue();
        this.accumSideAppearanceProb = createAccumSideAppearanceProb(sideProbabilities);
        this.random = new Random();
    }

    @Override
    public int roll() {
        final double randomValue = this.random.nextDouble();
        for (int side = 1; side <= numberOfSides; side++) {
            if (randomValue <= accumSideAppearanceProb[side - 1]) {
                return side;
            }
        }
        throw new RuntimeException("Something really terrible happened");
    }

    private double[] createAccumSideAppearanceProb(final double[] sideAppearanceProb) {
        final double[] accumSideAppearanceProb = new double[sideAppearanceProb.length];
        accumSideAppearanceProb[0] = sideAppearanceProb[0];
        for (int i = 1; i < sideAppearanceProb.length; i++) {
            accumSideAppearanceProb[i] = accumSideAppearanceProb[i - 1] + sideAppearanceProb[i];
        }
        return accumSideAppearanceProb;
    }
}
