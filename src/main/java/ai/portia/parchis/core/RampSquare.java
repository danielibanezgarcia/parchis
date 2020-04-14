package ai.portia.parchis.core;

public class RampSquare extends AbstractSquare implements Square {

    private final int indexInColor;
    private final int distanceToGoalSquare;

    public RampSquare(final Color color, final int indexInColor, final int distanceToGoalSquare) {
        super(color);
        this.indexInColor = indexInColor;
        this.distanceToGoalSquare = distanceToGoalSquare;
    }

    public int getIndexInColor() {
        return indexInColor;
    }

    public int getDistanceToGoalSquare() {
        return distanceToGoalSquare;
    }

    @Override
    public String toString() {
        return "RampSquare{" +
                "color=" + super.getColor() +
                ", indexInColor=" + indexInColor +
                ", distanceToGoalSquare=" + distanceToGoalSquare +
                "} ";
    }
}
